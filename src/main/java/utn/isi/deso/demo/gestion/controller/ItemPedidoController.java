
package utn.isi.deso.demo.gestion.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.isi.deso.demo.gestion.modelo.ItemPedido;
import utn.isi.deso.demo.gestion.service.ItemPedidoService;

@RestController
@RequestMapping("/item-pedido")
public class ItemPedidoController {
    private final ItemPedidoService itemPedidoService;
//    private final ItemMenuService itemMenuService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }


    @GetMapping
    public ResponseEntity<List<ItemPedido>> obtenerTodosLosItemsDelPedido() {
        return ResponseEntity.ok(itemPedidoService.obtenerTodosLosItemsPedido());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> obtenerItemsPorId(@PathVariable Long id) {
        return itemPedidoService.obtenerItemPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemPedido> crearItemPedido(@Validated @RequestBody ItemPedido items) {
        ItemPedido itemsPedido = itemPedidoService.crearItemsPedido(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemsPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedido> actualizarItemPedido(@PathVariable Long id, @Validated @RequestBody ItemPedido items) {
        ItemPedido actualizado = itemPedidoService.actualizarItemPedido(id, items);
        return ResponseEntity.ok(actualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItemPedido(@PathVariable Long id) {
        try {
            itemPedidoService.eliminarItemPedido(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
