/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utn.isi.deso.demo.gestion.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.isi.deso.demo.gestion.dao.ItemPedidoRepository;
import utn.isi.deso.demo.gestion.modelo.ItemPedido;
import utn.isi.deso.demo.tp.ResourceNotFoundException;

@Service
public class ItemPedidoService{

    @Autowired
    private final ItemPedidoRepository itemPedidoRepository;
//    @Autowired
//    private final ItemMenuServiceImpl itemMenuService;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
   }
    
    public List<ItemPedido> obtenerTodosLosItemsPedido() {
        return (List<ItemPedido>) itemPedidoRepository.findAll();
    }

    public Optional<ItemPedido> obtenerItemPedidoPorId(Long id) {
        return itemPedidoRepository.findById(id);
    }

    public ItemPedido crearItemsPedido(ItemPedido items) {
//        if (items.getItemMenu() == null) {
//            throw new IllegalArgumentException("El ItemMenu no puede ser nulo.");
//        }
//        itemMenuRepository.obtenerItemMenuPorId(items.getItemMenu().getId());
        return itemPedidoRepository.save(items);
    }

    public void eliminarItemPedido(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("ItemPedido no encontrado con ID: " + id);
        }
        itemPedidoRepository.deleteById(id);    
    }

    public ItemPedido actualizarItemPedido(long id, ItemPedido items) {
        ItemPedido itemPedidoExistente = obtenerItemPedidoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemsPedido no encontrado con id: " + id));

        itemPedidoExistente.setCantidad(items.getCantidad());
//        itemPedidoExistente.setItemMenu(
//            itemMenuService.obtenerItemMenuPorId(itemPedidoDTO.getItemMenuId())
//                    .orElseThrow(() -> new ResourceNotFoundException("ItemMenu no encontrado"))
//        );

        return itemPedidoRepository.save(itemPedidoExistente);    
    }

//    private ItemsPedido toEntity(ItemsPedidoDTO dto) {
//        ItemMenu itemMenu = itemMenuService.obtenerItemMenuPorId(dto.getItemMenuId())
//                .orElseThrow(() -> new ResourceNotFoundException("ItemMenu no encontrado"));
//        return new ItemsPedido(itemMenu, dto.getCantidad());
//    }
    
}

