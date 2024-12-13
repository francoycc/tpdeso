package utn.isi.deso.demo.gestion.modelo;

import lombok.*;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cliente")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "CUIT")
    private Integer cuit;
    @Column(name = "Email")
    private String email;
    @Column(name = "Direccion")
    private String direccion;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CoordenadaID", referencedColumnName = "ID_Coordenada", nullable = false)

    private Coordenada coordenada;
}