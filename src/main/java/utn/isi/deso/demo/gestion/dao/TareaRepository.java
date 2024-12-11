
package utn.isi.deso.demo.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.isi.deso.demo.gestion.modelo.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    // Métodos de consulta personalizados si es necesario

    List<Tarea> findByDescripcion(String description);  
}