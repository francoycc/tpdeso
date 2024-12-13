
package utn.isi.deso.demo.gestion.modelo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PedidoDTO {
    private Integer clienteId;
    private List<ItemPedido> items;
    private PagoDTO pago;

    
}