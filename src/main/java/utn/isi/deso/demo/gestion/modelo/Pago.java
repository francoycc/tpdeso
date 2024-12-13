
package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "La fecha de pago no puede ser nula")
    private LocalDate fechaPago;

    @NotNull(message = "El monto base no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto base debe ser mayor a 0")
    private double monto;

    public Pago(LocalDate fechaPago, double monto) {
        this.fechaPago = fechaPago;
        this.monto = monto;
    }

    
    public abstract double calcularMontoFinal();

    public abstract String getMetodoPago();
}


