
package utn.isi.deso.demo.gestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.isi.deso.demo.gestion.dao.ProyectoRepository;
import utn.isi.deso.demo.gestion.modelo.Proyecto;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> findById(Integer id) {
        return proyectoRepository.findById(id);
    }

    public List<Proyecto> findByDescripcion(String description) {
        // Implementar método de búsqueda por descripción en el repositorio si es necesario
        return proyectoRepository.findByNombreContaining(description);
    }

    public Proyecto create(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Proyecto update(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void delete(Integer id) {
        proyectoRepository.deleteById(id);
    }
}