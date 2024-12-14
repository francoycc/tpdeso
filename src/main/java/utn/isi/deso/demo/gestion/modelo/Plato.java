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
@Table(name = "plato")
public class Plato extends ItemMenu {
    @Column(nullable = false)
    @NotNull(message = "El peso no puede ser nulo.")
    private double peso;

    @Column(nullable = false)
    @NotNull(message = "Las calorías no pueden ser nulas.")
    private double calorias;

    @Column(nullable = false)
    private boolean aptoVegano;

    @Override
    public double peso() {
        return peso * 1.10; // Incremento del 10%
    }

    @Override
    public boolean esComida() {
        return true;
    }

    @Override
    public boolean esBebida() {
        return false;
    }

    @Override
    public boolean isAptoVegano() {
        return aptoVegano;
    }

}