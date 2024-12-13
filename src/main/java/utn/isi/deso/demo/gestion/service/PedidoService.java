
package utn.isi.deso.demo.gestion.service;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.isi.deso.demo.gestion.dao.ClienteRepository;
import utn.isi.deso.demo.gestion.dao.PedidoRepository;
import utn.isi.deso.demo.gestion.modelo.Cliente;
import utn.isi.deso.demo.gestion.modelo.EstadoPedido;
import utn.isi.deso.demo.gestion.modelo.ItemPedido;
import utn.isi.deso.demo.gestion.modelo.Pago;
import utn.isi.deso.demo.gestion.modelo.PagoFactory;
import utn.isi.deso.demo.gestion.modelo.Pedido;
import utn.isi.deso.demo.tp.ResourceNotFoundException;


@Service
public class PedidoService {
    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final PedidoRepository pedidoRepository;

//    @Autowired
//    private final ItemMenuRepository itemMenuRepository;

    @Autowired
    private final PagoFactory pagoFactory;

    public PedidoService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, PagoFactory pagoFactory) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagoFactory = pagoFactory;
    }

    @Transactional
    public Pedido agregarItemAPedido(Long pedidoId, Long itemMenuId, int cantidad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + pedidoId));
//
//        ItemMenu itemMenu = itemMenuRepository.findById(itemMenuId)
//                .orElseThrow(() -> new ResourceNotFoundException("ItemMenu no encontrado con ID: " + itemMenuId));
//
//        ItemPedido nuevoItem = new ItemPedido(itemMenu, cantidad);
//        pedido.agregarItemPedido(nuevoItem);

        return pedidoRepository.save(pedido); // Total recalculado internamente
    }

    @Transactional
    public Pedido eliminarItemDePedido(long pedidoId, long itemPedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + pedidoId));

        ItemPedido itemAEliminar = pedido.getItemsPedido().stream()
                .filter(item -> item.getId() == itemPedidoId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("ItemPedido no encontrado con ID: " + itemPedidoId));

        pedido.eliminarItemPedido(itemAEliminar);

        if (pedido.getItemsPedido().isEmpty()) {
            throw new IllegalArgumentException("El pedido no puede quedar sin ítems.");
        }

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido crearPedido(Pedido pedido) {
//        Cliente cliente = clienteRepository.findById(pedido.getCliente())
//                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + pedido.getCliente()));

//        List<ItemPedido> itemsPedido = pedido.getItemsPedido().stream()
//                .map(itemDTO -> {
//                    ItemMenu itemMenu = itemMenuRepository.findById(itemDTO.getItemMenu())
//                            .orElseThrow(() -> new ResourceNotFoundException("ItemMenu no encontrado con ID: " + itemDTO.getItemMenuId()));
//                    return new ItemsPedido(itemMenu, itemDTO.getCantidad());
//                })
//                .collect(Collectors.toList());

       
//        Pago metodoPago = pagoFactory.crearPago(pedido.getMetodoPago(),pedido.getMontoBase(), );
//        Pedido pedido = new Pedido(cliente, metodoPago);
//        pedido.setItemsPedido(itemsPedido);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> obtenerPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> obtenerPedidosPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<Pedido> obtenerPedidosPorRangoDeFechas(Date fechaInicio, Date fechaFin) {
        return pedidoRepository.findByFechaPagoBetween(fechaInicio, fechaFin);
    }

    public Pedido actualizarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPedidoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));

        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
    
    public Pedido actualizarMetodoPago(Long id, Pago nuevoPago) {
        Pedido pedido = obtenerPedidoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));

        pedido.setMetodoPago(nuevoPago);
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido no encontrado con ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }
}
