/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utn.isi.deso.demo.gestion.modelo;

import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Component;


@Component
public class PagoFactory {
    public Pago crearPago(String tipoPago, LocalDate fechaPago, double monto, Map<String, String> detalles) {
        switch (tipoPago) {
            case "Efectivo":
                return new Efectivo();

            case "MercadoPago":
                String alias = detalles.get("alias");
                if (alias == null || alias.isBlank()) {
                    throw new IllegalArgumentException("El alias es obligatorio para MercadoPago");
                }
                return new MercadoPago(fechaPago, monto, alias);

            case "Transferencia":
                String cbu = detalles.get("cbu");
                String cuit = detalles.get("cuit");
                if (cbu == null || cuit == null || cbu.isBlank() || cuit.isBlank()) {
                    throw new IllegalArgumentException("CBU y CUIT son obligatorios para Transferencia");
                }
                return new Transferencia(fechaPago, monto, cbu, cuit);

            default:
                throw new IllegalArgumentException("Tipo de pago no soportado: " + tipoPago);
        }
    }
}
