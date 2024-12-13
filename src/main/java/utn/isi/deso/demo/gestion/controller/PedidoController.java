
package utn.isi.deso.demo.gestion.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.isi.deso.demo.gestion.modelo.Cliente;
import utn.isi.deso.demo.gestion.modelo.EstadoPedido;
import utn.isi.deso.demo.gestion.modelo.Pago;
import utn.isi.deso.demo.gestion.modelo.Pedido;
import utn.isi.deso.demo.gestion.modelo.PedidoDTO;
import utn.isi.deso.demo.gestion.service.ClienteService;
import utn.isi.deso.demo.gestion.service.PedidoService;


@RestController
@RequestMapping("/pedido")
public class PedidoController {
    
    Logger logger = org.slf4j.LoggerFactory.getLogger(PedidoController.class);
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final ItemMenuService itemMenuService;
    
    private List<ItemPedidoDTO> itemsAgregados = new ArrayList<>();
    private double totalPedido = 0;

    @Autowired
    public PedidoController(PedidoService pedidoService, ClienteService clienteService, ItemMenuService itemMenuService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.itemMenuService = itemMenuService;
    }

    @GetMapping
    public String mostrarListaPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedido";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Integer id) {
        return pedidoService.obtenerPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoDTO pedido) {
        Pedido nuevoPedido = pedidoService.crearPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Integer id, @RequestParam EstadoPedido nuevoEstado) {
        Pedido pedidoActualizado = pedidoService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @PutMapping("/{id}/metodoPago")
    public ResponseEntity<Pedido> actualizarMetodoPago(@PathVariable Integer id, @RequestBody Pago nuevoPago) {
        Pedido pedidoActualizado = pedidoService.actualizarMetodoPago(id, nuevoPago);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarunPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model) {
        List<Cliente> clientes = clienteService.findAll();
        List<ItemMenu> itemsMenu = itemMenuService.findAll();

        PedidoDTO pedidoNuevo = new PedidoDTO();

        model.addAttribute("pedido", pedidoNuevo);
        model.addAttribute("clientes", clientes);
        model.addAttribute("itemsMenu", itemsMenu);

        return "crearEditarPedido";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedido") PedidoDTO pedidoDTO, RedirectAttributes redirectAttributes) {
        logger.info("Guardando Pedido: " + pedidoDTO);
        if (itemsAgregados.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe agregar al menos un ítem al pedido.");
            return "redirect:/pedido/nuevo";
        }
        pedidoDTO.setItems(itemsAgregados);
        pedidoDTO.setTotal(totalPedido);

        pedidoService.crearPedido(pedidoDTO);

        itemsAgregados.clear();
        totalPedido = 0;

        redirectAttributes.addFlashAttribute("success", "Pedido guardado correctamente.");
        return "redirect:/pedido";
    }

    @PostMapping("/buscar")
    public String buscarPedido(@ModelAttribute Pedido pedido, Model model) {
        List<Pedido> pedidos = pedidoService.buscarPorCriterios(
                pedido.getCliente(),
                pedido.getEstado(),
                pedido.getFechaPago(),
                pedido.getMetodoPago()
        );
        model.addAttribute("pedidos", pedidos);
        logger.info("Pedidos encontrados: " + pedidos);
        return "pedidos";
    }

    @PostMapping("/agregar-item")
    public String agregarItem(@RequestParam("id") int id,
                              @RequestParam("nombre") String nombre,
                              @RequestParam("precio") double precio,
                              @RequestParam("cantidad") int cantidad,
                              RedirectAttributes redirectAttributes) {
        if (cantidad <= 0) {
            redirectAttributes.addFlashAttribute("error", "La cantidad debe ser mayor a cero.");
            return "redirect:/pedido/nuevo";
        }

        ItemPedidoDTO itemExistente = itemsAgregados.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setCantidad(cantidad);
            itemExistente.setSubtotal(precio * cantidad);
        } else {
            ItemPedidoDTO nuevoItem = new ItemPedidoDTO(id, nombre, precio, cantidad, precio * cantidad);
            itemsAgregados.add(nuevoItem);
        }

        totalPedido = itemsAgregados.stream().mapToDouble(ItemPedidoDTO::getSubtotal).sum();

        redirectAttributes.addFlashAttribute("success", "Ítem agregado correctamente.");
        return "redirect:/pedido/nuevo";
    }

    @PostMapping("/eliminar-item/{id}")
    public String eliminarItem(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        itemsAgregados.removeIf(item -> item.getId() == id);
        totalPedido = itemsAgregados.stream().mapToDouble(ItemPedidoDTO::getSubtotal).sum();
        redirectAttributes.addFlashAttribute("success", "Ítem eliminado correctamente.");
        return "redirect:/pedido/nuevo";
    }

    @DeleteMapping("/eliminarPedido/{id}")
    public String eliminarPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return "redirect:/pedido";
    }

    @PutMapping("/editarPedido/{id}")
    public String editarPedido(@PathVariable Integer id, Model model) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        logger.info("Pedido recuperado {}", pedido);
        model.addAttribute("pedido", pedido);
        return "crearEditarPedido";
    }
}
