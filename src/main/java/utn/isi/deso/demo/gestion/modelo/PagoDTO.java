/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utn.isi.deso.demo.gestion.modelo;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PagoDTO {
    private String tipoPago; // "EFECTIVO", "MERCADOPAGO", "TRANSFERENCIA"
    private Double monto;
    private String alias; // Solo para MercadoPago
    private String cbu;   // Solo para Transferencia
    private String cuit;  // Solo para Transferencia
}
