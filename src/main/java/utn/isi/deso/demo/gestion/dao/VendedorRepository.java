
package utn.isi.deso.demo.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.isi.deso.demo.gestion.modelo.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
    // Métodos de consulta personalizados si es necesario

    List<Vendedor> findByNombreContaining(String nombre);
    
    @Query("SELECT v FROM Vendedor v "
            + "WHERE (:nombre IS NULL OR v.nombre LIKE %:nombre%) "
            + "AND (:direccion IS NULL OR v.direccion LIKE %:direccion%) "
            + "AND (:latitud IS NULL OR CAST(v.coordenada.lat AS String) LIKE %:latitud%) "
            + "AND (:longitud IS NULL OR CAST(v.coordenada.lng AS String) LIKE %:longitud%)")
    List<Vendedor> buscarPorCriterios(
            @Param("nombre") String nombre, 
            @Param("direccion") String direccion, 
            @Param("latitud") Double latitud, 
            @Param("longitud") Double longitud);

}