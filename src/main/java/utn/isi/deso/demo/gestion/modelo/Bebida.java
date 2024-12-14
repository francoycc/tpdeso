/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
@Table(name = "bebida")
public class Bebida extends ItemMenu {

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo.")
    private double tamanio;

    @Column(nullable = false)
    @NotNull(message = "El atributo graduación alcohólica no puede ser nulo.")
    private boolean graduacionAlcoholica;

    @Override
    public double peso() {
        double pesoBase = graduacionAlcoholica ? tamanio * 0.99 : tamanio * 1.04; // 0.99 para alcohol, 1.04 para no alcohol
        return pesoBase * 1.20; // Aumentar 20% por el envase
    }

    @Override
    public boolean esComida() {
        return false;
    }

    @Override
    public boolean esBebida() {
        return true;
    }

}
