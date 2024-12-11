
package utn.isi.deso.demo.gestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.isi.deso.demo.gestion.dao.TareaRepository;
import utn.isi.deso.demo.gestion.modelo.Tarea;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> findAll() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> findById(Integer id) {
        return tareaRepository.findById(id);
    }

    public Tarea create(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Tarea update(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void delete(Integer id) {
        tareaRepository.deleteById(id);
    }
}