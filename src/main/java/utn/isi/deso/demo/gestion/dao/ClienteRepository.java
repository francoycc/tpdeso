
package utn.isi.deso.demo.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.isi.deso.demo.gestion.modelo.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Métodos de consulta personalizados si es necesario

    List<Cliente> findByNombreContaining(String nombre);
    
    @Query("SELECT c FROM Cliente c "
            + "WHERE (:nombre IS NULL OR c.nombre LIKE %:nombre%) "
            + "AND (:cuit IS NULL OR CAST(c.cuit AS String) LIKE %:cuit%) "
            + "AND (:email IS NULL OR c.email LIKE %:email%) "
            + "AND (:direccion IS NULL OR c.direccion LIKE %:direccion%) "
            + "AND (:latitud IS NULL OR CAST(c.coordenada.lat AS String) LIKE %:latitud%) "
            + "AND (:longitud IS NULL OR CAST(c.coordenada.lng AS String) LIKE %:longitud%)")
    List<Cliente> buscarPorCriterios(
            @Param("nombre") String nombre, 
            @Param("cuit") Integer cuit, 
            @Param("email") String email, 
            @Param("direccion") String direccion, 
            @Param("latitud") Double latitud, 
            @Param("longitud") Double longitud);

}