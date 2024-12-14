
package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "itemPedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "item_menu_id", nullable = false)
    private ItemMenu itemMenu;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @NotNull(message = "El pedido no puede ser nulo.")
    private Pedido pedido;

    @Column(nullable = false)
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private int cantidad;

  public double calcularSubtotal() {
       return itemMenu.getPrecio() * cantidad;
    }

    public ItemPedido(ItemMenu itemMenu, int cantidad) {
        this.itemMenu = itemMenu;
        this.cantidad = cantidad;
    }
  
}

