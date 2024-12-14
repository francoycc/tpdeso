
package utn.isi.deso.demo.gestion.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.ResourceNotFoundException;
import utn.isi.deso.demo.gestion.dao.ItemPedidoRepository;
import utn.isi.deso.demo.gestion.modelo.ItemMenu;
import utn.isi.deso.demo.gestion.modelo.ItemPedido;
import utn.isi.deso.demo.gestion.modelo.ItemPedidoDTO;

@Service
public class ItemPedidoService{

    @Autowired
    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemMenuService itemMenuService;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, ItemMenuService itemMenuService) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.itemMenuService = itemMenuService; 
   }
    
    public List<ItemPedido> obtenerTodosLosItemsPedido() {
        return (List<ItemPedido>) itemPedidoRepository.findAll();
    }

    public Optional<ItemPedido> obtenerItemPedidoPorId(Integer id) {
        return itemPedidoRepository.findById(id);
    }

    public ItemPedido crearItemsPedido(ItemPedido items) {
        if (items.getItemMenu() == null) {
            throw new IllegalArgumentException("El ItemMenu no puede ser nulo.");
        }
        itemMenuService.buscarPorId(items.getItemMenu().getId());
        return itemPedidoRepository.save(items);
    }

    public void eliminarItemPedido(Integer id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("ItemPedido no encontrado con ID: " + id);
        }
        itemPedidoRepository.deleteById(id);    
    }

    public ItemPedido actualizarItemPedido(Integer id, ItemPedido items) {
        ItemPedido itemPedidoExistente = obtenerItemPedidoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemsPedido no encontrado con id: " + id));

        itemPedidoExistente.setCantidad(items.getCantidad());
        itemPedidoExistente.setItemMenu(
            itemMenuService.buscarPorId(items.getItemMenu().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("ItemMenu no encontrado"))
        );

        return itemPedidoRepository.save(itemPedidoExistente);    
    }

    private ItemPedido toEntity(ItemPedidoDTO dto) {
        ItemMenu itemMenu = itemMenuService.buscarPorId(dto.getIdItemMenu())
                .orElseThrow(() -> new ResourceNotFoundException("ItemMenu no encontrado"));
        return new ItemPedido(itemMenu, dto.getCantidad());
    }
    
}

