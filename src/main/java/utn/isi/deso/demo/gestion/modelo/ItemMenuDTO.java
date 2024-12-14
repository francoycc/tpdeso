/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utn.isi.deso.demo.gestion.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemMenuDTO {
    private Integer id;
    private String tipo;
    private String nombre;
    private String descripcion;
    private String precio;
     private String categoriaTipoItem; 
    private String vendedorNombre;    
    private Double peso;
    private Double calorias;
    private Boolean aptoVegano;

    // Atributos específicos para Bebida
    private Double tamanio;
    private Boolean graduacionAlcoholica;
}