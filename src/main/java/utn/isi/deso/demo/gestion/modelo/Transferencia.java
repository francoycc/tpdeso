/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utn.isi.deso.demo.gestion.modelo;

import jakarta.validation.constraints.*;
import java.time.LocalDate;


public class Transferencia extends Pago {
    @NotBlank(message = "El CBU no puede estar vacío")
    private String cbu;

    @NotBlank(message = "El CUIT no puede estar vacío")
    private String cuit;

    public Transferencia(LocalDate fechaPago, double monto, String cbu, String cuit) {
        super(null, fechaPago, monto);
        this.cbu = cbu;
        this.cuit = cuit;
    }

    @Override
    public double calcularMontoFinal() {
        return getMonto(); // Sin modificaciones en transferencias
    }

    @Override
    public String getMetodoPago() {
        return "Transferencia";
    }
}
