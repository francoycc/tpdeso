
package utn.isi.deso.demo.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.isi.deso.demo.gestion.modelo.Proyecto;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    // Métodos de consulta personalizados si es necesario

    List<Proyecto> findByNombreContaining(String nombre);
}