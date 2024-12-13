/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Efectivo extends Pago {

    public Efectivo(LocalDate fechaPago, double monto) {
        super(fechaPago, monto);
    }

    @Override
    public double calcularMontoFinal() {
        return getMonto() * 0.95; // Descuento del 5%
    }

    @Override
    public String getMetodoPago() {
        return "Efectivo";
    }
}