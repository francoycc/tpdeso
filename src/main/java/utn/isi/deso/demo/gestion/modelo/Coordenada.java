package utn.isi.deso.demo.gestion.modelo;

import lombok.*;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coordenada")
public class Coordenada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Coordenada")
    private Integer id;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
}