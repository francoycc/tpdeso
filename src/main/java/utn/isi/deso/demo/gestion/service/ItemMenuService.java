
package utn.isi.deso.demo.gestion.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.isi.deso.demo.gestion.dao.CategoriaRepository;
import utn.isi.deso.demo.gestion.dao.ItemMenuRepository;
import utn.isi.deso.demo.gestion.modelo.Bebida;
import utn.isi.deso.demo.gestion.modelo.Categoria;
import utn.isi.deso.demo.gestion.modelo.ItemMenu;
import utn.isi.deso.demo.gestion.modelo.ItemMenuDTO;
import utn.isi.deso.demo.gestion.modelo.Plato;
import utn.isi.deso.demo.gestion.modelo.Vendedor;


@Service
public class ItemMenuService {
    
    private final ItemMenuRepository itemMenuRepository;
    private final CategoriaRepository categoriaRepository;
    private final VendedorService vendedorService;

    @Autowired
    public ItemMenuService(ItemMenuRepository itemMenuRepository, CategoriaRepository categoriaRepository, VendedorService vendedorService) {
        this.itemMenuRepository = itemMenuRepository;
        this.categoriaRepository = categoriaRepository;
        this.vendedorService = vendedorService;
    }

    public List<ItemMenu> listarTodosLosItems() {
        return (List<ItemMenu>) itemMenuRepository.findAll();
    }

    public Optional<ItemMenu> buscarPorId(Integer id) {
        return itemMenuRepository.findById(id);
    }

    public ItemMenu crearItemMenu(ItemMenu itemMenu) {
        return itemMenuRepository.save(itemMenu);
    }

    public void actualizarItemMenu(Integer id, ItemMenuDTO itemMenuDTO) {
        ItemMenu itemExistente = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado con ID: " + id));

        itemExistente.setNombre(itemMenuDTO.getNombre());
        itemExistente.setDescripcion(itemMenuDTO.getDescripcion());
        itemExistente.setPrecio(Double.parseDouble(itemMenuDTO.getPrecio()));

        Categoria categoria = categoriaRepository.findByTipoItem(itemMenuDTO.getCategoriaTipoItem())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Vendedor vendedor = vendedorService.findByNombre(itemMenuDTO.getVendedorNombre())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        itemExistente.setCategoria(categoria);
        itemExistente.setVendedor(vendedor);

        if (itemExistente instanceof Plato) {
            Plato plato = (Plato) itemExistente;
            plato.setPeso(itemMenuDTO.getPeso());
            plato.setCalorias(itemMenuDTO.getCalorias());
            plato.setAptoVegano(Boolean.TRUE.equals(itemMenuDTO.getAptoVegano()));
        } else if (itemExistente instanceof Bebida) { 
            Bebida bebida = (Bebida) itemExistente;
            bebida.setTamanio(itemMenuDTO.getTamanio());
            bebida.setGraduacionAlcoholica(Boolean.TRUE.equals(itemMenuDTO.getGraduacionAlcoholica()));
        }

        itemMenuRepository.save(itemExistente);
    }
    
    public void borrarItemMenu(Integer id) {
        if (!itemMenuRepository.existsById(id)) {
            throw new RuntimeException("ItemMenu no encontrado con ID: " + id);
        }
        itemMenuRepository.deleteById(id);
    }

    public List<ItemMenu> buscarPorCriterios(Integer id, String nombre, Integer categoriaId, Integer vendedorId, Double precio) {
        List<ItemMenu> items = (List<ItemMenu>) itemMenuRepository.findAll();
        return items.stream()
                .filter(item -> (id == null || item.getId().equals(id)) &&
                        (nombre == null || item.getNombre().equalsIgnoreCase(nombre)) &&
                        (categoriaId == null || (item.getCategoria() != null && item.getCategoria().getId().equals(categoriaId))) &&
                        (vendedorId == null || (item.getVendedor() != null && item.getVendedor().getId().equals(vendedorId))) &&
                        (precio == null || Double.compare(item.getPrecio(), precio) == 0))
                .collect(Collectors.toList());
    }
}
