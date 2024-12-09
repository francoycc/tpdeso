package utn.isi.deso.demo.gestion.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "coordenada")
public class Coordenada {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double lat;
    private double lng;

    public Coordenada() {}

    public Coordenada(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordenada(int id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

