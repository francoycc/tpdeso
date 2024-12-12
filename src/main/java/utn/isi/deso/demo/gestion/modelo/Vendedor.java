package utn.isi.deso.demo.gestion.modelo;

import lombok.*;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendedor")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Vendedor")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Direccion")
    private String direccion;
    
    /*@OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas;*/
    
    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_Coordenada", nullable = false)*/
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CoordenadaID", referencedColumnName = "ID_Coordenada", nullable = false)

    private Coordenada coordenada;
}