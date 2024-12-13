
package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente no puede ser nulo.")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itemsPedido = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "El estado del pedido no puede ser nulo.")
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    @NotNull(message = "El método de pago no puede ser nulo.")
    private Pago metodoPago;

    @Column(nullable = false)
    private double montoBase;

    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent(message = "La fecha de pago no puede ser futura.")
    private LocalDate fechaPago;

    @Column(name = "monto_total", nullable = false)
    private double montoTotal;
    
    
    public void agregarItemPedido(ItemPedido item) {
        item.setPedido(this); // Establece la relación bidireccional
        this.itemsPedido.add(item);
        recalcularMontoBase();
    }
    
    public void eliminarItemPedido(ItemPedido item) {
        this.itemsPedido.remove(item);
        item.setPedido(null); // Limpia la relación bidireccional
        recalcularMontoBase();
    }
    
    public void recalcularMontoBase() {
//        this.montoBase = itemsPedido.stream()
//                                    .mapToDouble(ItemPedido::calcularSubtotal)
//                                    .sum();
    }
    
    public void calcularMontoTotal() {
        if (this.metodoPago != null) {
            this.montoTotal = this.metodoPago.calcularMontoFinal();
        } else {
            this.montoTotal = this.montoBase;
        }
    }
}