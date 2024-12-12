package utn.isi.deso.demo.gestion.modelo;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descripcion;
    @Column(name = "costo_por_hora")
    private double costoPorHora;
    @Column(name = "horas_estimadas")
    private int horasEstimadas;
    @Column(name = "horas_ejecutadas")
    private int horasEjecutadas;
    @Column(name = "fecha_inicio")
    private Instant fechaInicio;
    @Column(name = "fecha_fin")
    private Instant fechaFin;

    /*@ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;*/
}
