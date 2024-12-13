
package utn.isi.deso.demo.gestion.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import utn.isi.deso.demo.gestion.modelo.EstadoPedido;
import utn.isi.deso.demo.gestion.modelo.Pago;
import utn.isi.deso.demo.gestion.modelo.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
    List<Pedido> findByClienteId(Integer clienteId);

    // Buscar pedidos por estado
    List<Pedido> findByEstado(EstadoPedido estado);

    // Buscar pedidos por rango de fechas de pago
    List<Pedido> findByFechaPagoBetween(Date fechaInicio, Date fechaFin);

    // Buscar pedidos por método de pago
    List<Pedido> findByMetodoPago(Pago metodoPago);
}
