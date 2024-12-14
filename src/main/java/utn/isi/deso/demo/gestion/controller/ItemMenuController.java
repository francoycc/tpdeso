
package utn.isi.deso.demo.gestion.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utn.isi.deso.demo.gestion.dao.CategoriaRepository;
import utn.isi.deso.demo.gestion.modelo.Bebida;
import utn.isi.deso.demo.gestion.modelo.Categoria;
import utn.isi.deso.demo.gestion.modelo.ItemMenu;
import utn.isi.deso.demo.gestion.modelo.ItemMenuDTO;
import utn.isi.deso.demo.gestion.modelo.Plato;
import utn.isi.deso.demo.gestion.modelo.Vendedor;
import utn.isi.deso.demo.gestion.service.ItemMenuService;
import utn.isi.deso.demo.gestion.service.VendedorService;
import tp.ResourceNotFoundException;


@RestController
@RequestMapping("/item-menu")
public class ItemMenuController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(ItemMenuController.class);
     @Autowired
    private final ItemMenuService itemMenuService;
     @Autowired
    private final VendedorService vendedorService;
     
     private final CategoriaRepository categoriaRepository;

    public ItemMenuController(ItemMenuService itemMenuService, VendedorService vendedorService, CategoriaRepository categoriaRepository) {
        this.itemMenuService = itemMenuService;
        this.vendedorService = vendedorService;
        this.categoriaRepository = categoriaRepository;
    }
     @GetMapping("/item-menu/nuevo")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("itemMenu", new ItemMenuDTO());
        return "crearEditarItemMenu";
    }
    
   @PostMapping("/itemMenu/guardar")
    public String guardarItemMenu(@ModelAttribute ItemMenuDTO itemMenuDTO) {
    logger.info("Guardando ItemMenu: " + itemMenuDTO);

    // Buscar y validar las relaciones (Categoria y Vendedor)
    Categoria categoria = categoriaRepository.findByTipoItem(itemMenuDTO.getCategoriaTipoItem())
            .orElseThrow(() -> new RuntimeException("Categoría con tipo '" + itemMenuDTO.getCategoriaTipoItem() + "' no encontrada"));

    Vendedor vendedor = vendedorService.findByNombre(itemMenuDTO.getVendedorNombre())
            .orElseThrow(() -> new RuntimeException("Vendedor con nombre '" + itemMenuDTO.getVendedorNombre() + "' no encontrado"));

    // Actualizar si existe el ID
    if (itemMenuDTO.getId() != null && itemMenuDTO.getId() != 0) {
        ItemMenu existente = itemMenuService.buscarPorId(itemMenuDTO.getId())
                .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado"));

        existente.setNombre(itemMenuDTO.getNombre());
        existente.setDescripcion(itemMenuDTO.getDescripcion());
        try {
            if (itemMenuDTO.getPrecio() != null && !itemMenuDTO.getPrecio().isEmpty()) {
                existente.setPrecio(Double.parseDouble(itemMenuDTO.getPrecio()));
            } else {
                throw new RuntimeException("El precio no puede estar vacío.");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Formato inválido para el precio: " + itemMenuDTO.getPrecio());
        }
        existente.setCategoria(categoria);
        existente.setVendedor(vendedor);

        if (existente instanceof Plato && itemMenuDTO.getPeso() != null) {
            Plato plato = (Plato) existente;
            plato.setPeso(itemMenuDTO.getPeso());
            plato.setCalorias(itemMenuDTO.getCalorias());
            plato.setAptoVegano(Boolean.TRUE.equals(itemMenuDTO.getAptoVegano()));
        } else if (existente instanceof Bebida && itemMenuDTO.getTamanio() != null) {
            Bebida bebida = (Bebida) existente;
            bebida.setTamanio(itemMenuDTO.getTamanio());
            bebida.setGraduacionAlcoholica(Boolean.TRUE.equals(itemMenuDTO.getGraduacionAlcoholica()));
        }

        itemMenuService.actualizarItemMenu(existente.getId(), itemMenuDTO);

    } else {
        // Crear nuevo ItemMenu
        ItemMenu nuevoItemMenu;

        
        if (itemMenuDTO.getTipo().equalsIgnoreCase("Plato")) {
            Plato plato = new Plato();
            plato.setNombre(itemMenuDTO.getNombre());
            plato.setDescripcion(itemMenuDTO.getDescripcion());
             try {
                if (itemMenuDTO.getPrecio() != null && !itemMenuDTO.getPrecio().isEmpty()) {
                    plato.setPrecio(Double.parseDouble(itemMenuDTO.getPrecio()));
                } else {
                    throw new RuntimeException("El precio no puede estar vacío.");
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Formato inválido para el precio: " + itemMenuDTO.getPrecio());
            }
            plato.setCategoria(categoria);
            plato.setVendedor(vendedor);
            plato.setPeso(itemMenuDTO.getPeso());
            plato.setCalorias(itemMenuDTO.getCalorias());
            plato.setAptoVegano(Boolean.TRUE.equals(itemMenuDTO.getAptoVegano()));
            nuevoItemMenu = plato;

        } else if (itemMenuDTO.getTipo().equalsIgnoreCase("Bebida")) {
            Bebida bebida = new Bebida();
            bebida.setNombre(itemMenuDTO.getNombre());
            bebida.setDescripcion(itemMenuDTO.getDescripcion());
             try {
                if (itemMenuDTO.getPrecio() != null && !itemMenuDTO.getPrecio().isEmpty()) {
                    bebida.setPrecio(Double.parseDouble(itemMenuDTO.getPrecio()));
                } else {
                    throw new RuntimeException("El precio no puede estar vacío.");
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Formato inválido para el precio: " + itemMenuDTO.getPrecio());
            }
            bebida.setCategoria(categoria);
            bebida.setVendedor(vendedor);
            bebida.setTamanio(itemMenuDTO.getTamanio());
            bebida.setGraduacionAlcoholica(Boolean.TRUE.equals(itemMenuDTO.getGraduacionAlcoholica()));
            nuevoItemMenu = bebida;

        } else {
            throw new RuntimeException("No se pudo determinar el tipo de ItemMenu (Plato o Bebida).");
        }

        itemMenuService.crearItemMenu(nuevoItemMenu);
    }

    return "redirect:/itemMenu";
}

    @GetMapping("/item-menu")
    public String listarItems(Model model) {
        List<ItemMenu> items = itemMenuService.listarTodosLosItems();
        model.addAttribute("items-menu", items);
        return "item-menu";
    }

    @GetMapping("/item-menu/eliminarItem/{id}")
    public String eliminarVendedor(@PathVariable Integer id) {
        itemMenuService.borrarItemMenu(id);
        return "redirect:/item-menu";
    }
    @GetMapping("/item-menu/editarItem/{id}")
    public String editarItemMenu(@PathVariable Integer id, Model model) {
        ItemMenu item = itemMenuService.buscarPorId(id).orElseThrow(()-> new RuntimeException("ItemMenu no encontrado"));
        logger.info("Item recuperado {}", item);
        model.addAttribute("itemMenu", item);
        return "crearEditarItemMenu";
    }
    @GetMapping("/itemMenu/buscar")
    public String buscarItemMenu(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) Integer vendedorId,
            @RequestParam(required = false) Double precio,
            Model model) {

        // Obtener objetos relacionados (solo si se proporciona el ID)
        Categoria categoria = (categoriaId != null) ? categoriaRepository.findById(categoriaId).orElse(null) : null;
        Vendedor vendedor = (vendedorId != null) ? vendedorService.findById(vendedorId).orElse(null) : null;

        logger.info("Buscando ItemMenu con filtros - id: " + id + ", nombre: " + nombre +
                    ", categoria: " + (categoria != null ? categoria.getTipoItem() : "N/A") +
                    ", vendedor: " + (vendedor != null ? vendedor.getNombre() : "N/A") + ", precio: " + precio);

        // Servicio para buscar por criterios
        List<ItemMenu> resultados = itemMenuService.buscarPorCriterios(id, nombre, categoriaId, vendedorId, precio);

        // Convertir resultados a DTO
        List<ItemMenuDTO> itemMenuDTOs = resultados.stream()
                .map(item -> {
                    ItemMenuDTO dto = new ItemMenuDTO();
                    dto.setId(item.getId());
                    dto.setNombre(item.getNombre());
                    dto.setDescripcion(item.getDescripcion());
                    dto.setPrecio(String.valueOf(item.getPrecio())); // Convertir double a String

                    dto.setCategoriaTipoItem(item.getCategoria() != null ? item.getCategoria().getTipoItem() : "Sin Categoría");
                    dto.setVendedorNombre(item.getVendedor() != null ? item.getVendedor().getNombre() : "Sin Vendedor");

                    // Verificar tipo específico (Plato o Bebida)
                    if (item instanceof Plato) {
                        Plato plato = (Plato) item;
                        dto.setPeso(plato.getPeso());
                        dto.setCalorias(plato.getCalorias());
                        dto.setAptoVegano(plato.isAptoVegano());
                    } else if (item instanceof Bebida) {
                        Bebida bebida = (Bebida) item;
                        dto.setTamanio(bebida.getTamanio());
                        dto.setGraduacionAlcoholica(bebida.isGraduacionAlcoholica());
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // Agregar resultados al modelo para la vista
        model.addAttribute("itemMenus", itemMenuDTOs);
        return "itemMenu/lista"; // Nombre de la vista donde se mostrarán los resultados
    }
    

    // Obtener todos los ítems del menú
    @GetMapping
    public ResponseEntity<List<ItemMenu>> obtenerTodosLosItems() {
        List<ItemMenu> items = itemMenuService.listarTodosLosItems();
        return ResponseEntity.ok(items);
    }


    // Obtener un ítem por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ItemMenu> obtenerItemMenuPorId(@PathVariable Integer id) {
        return itemMenuService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemMenu> crearItemMenu(@Validated @RequestBody ItemMenu itemMenu) {
        ItemMenu createdItem = itemMenuService.crearItemMenu(itemMenu);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemMenu> actualizarItemMenu(@PathVariable Integer id, @Validated @RequestBody ItemMenuDTO itemMenuDTO) {
        try {
            ItemMenu itemExistente = itemMenuService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemMenu no encontrado con ID: " + id));

            itemMenuService.actualizarItemMenu(id, itemMenuDTO);

            return ResponseEntity.ok(itemExistente);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
