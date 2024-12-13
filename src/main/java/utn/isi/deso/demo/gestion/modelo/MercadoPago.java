package utn.isi.deso.demo.gestion.modelo;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class MercadoPago extends Pago {
    @NotBlank(message = "El alias no puede estar vacío")
    private String alias;

    public MercadoPago(LocalDate fechaPago, double monto, String alias) {
        super(null, fechaPago, monto);
        this.alias = alias;
    }

    @Override
    public double calcularMontoFinal() {
        return getMonto() * 1.05; // Incremento por comisiones
    }

    @Override
    public String getMetodoPago() {
        return "MercadoPago";
    }
}
