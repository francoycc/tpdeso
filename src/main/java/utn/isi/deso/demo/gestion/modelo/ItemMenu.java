
package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ItemMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String nombre;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres.")
    private String descripcion;

    @Column(nullable = false)
    @NotNull(message = "El precio no puede ser nulo.")
    private double precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "La categoría no puede ser nula.")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    @NotNull(message = "El vendedor no puede ser nulo.")
    private Vendedor vendedor;

    
    // Métodos abstractos
    public abstract double peso();

    public abstract boolean esComida();

    public abstract boolean esBebida();

    public boolean isAptoVegano() {
        return false; // Por defecto, no es apto vegano
    }
}