package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "vendedor")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nombre;
    private String direccion;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordenada_id", referencedColumnName = "id")
    private Coordenada coordenada;

    public Vendedor() {}

    public Vendedor(String nombre, String direccion, Coordenada coordenada) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenada = coordenada;
    }
    
    public Vendedor(int id, String nombre, String direccion, Coordenada coordenada) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenada = coordenada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public Coordenada getCoordenadas() {
        return coordenada;
    }

    public void setCoordenadas(Coordenada coordenadas) {
        this.coordenada = coordenadas;
    }
}
