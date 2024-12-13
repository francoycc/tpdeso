
package utn.isi.deso.demo.gestion.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
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
import utn.isi.deso.demo.gestion.modelo.PedidoDTO;
import utn.isi.deso.demo.tp.ResourceNotFoundException;


@Service
public class PedidoService {
    @Autowired
    private final ClienteService clienteService;

    @Autowired
    private final PedidoRepository pedidoRepository;

//    @Autowired
//    private final ItemMenuRepository itemMenuRepository;

    @Autowired
    private final PagoFactory pagoFactory;

    public PedidoService(ClienteService clienteService, PedidoRepository pedidoRepository, PagoFactory pagoFactory) {
        this.clienteService = clienteService;
        this.pedidoRepository = pedidoRepository;
        this.pagoFactory = pagoFactory;
    }

    @Transactional
    public Pedido agregarItemAPedido(Integer pedidoId, Integer itemMenuId, Integer cantidad) {
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
    public Pedido eliminarItemDePedido(Integer pedidoId, Integer itemPedidoId) {
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

     public Pedido crearPedido(PedidoDTO pedidoDTO) {
        Cliente cliente = clienteService.findById(pedidoDTO.getClienteId())
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPago(LocalDate.now());

        // Agregar items al pedido
        for (ItemPedido item : pedidoDTO.getItems()) {
            pedido.getItemsPedido().add(item);
        }

        // Crear el pago utilizando el factory
        Pago pago = pagoFactory.crearPago(pedidoDTO.getPago());
        pedido.setMetodoPago(pago);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> obtenerPedidosPorCliente(Integer clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> obtenerPedidosPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<Pedido> obtenerPedidosPorRangoDeFechas(Date fechaInicio, Date fechaFin) {
        return pedidoRepository.findByFechaPagoBetween(fechaInicio, fechaFin);
    }

    public Pedido actualizarEstado(Integer id, EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPedidoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));

        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
    
    public Pedido actualizarMetodoPago(Integer id, Pago nuevoPago) {
        Pedido pedido = obtenerPedidoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));

        pedido.setMetodoPago(nuevoPago);
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(Integer id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido no encontrado con ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }
}
