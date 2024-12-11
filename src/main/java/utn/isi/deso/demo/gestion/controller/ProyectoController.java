package utn.isi.deso.demo.gestion.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import utn.isi.deso.demo.gestion.modelo.Proyecto;
import utn.isi.deso.demo.gestion.modelo.Tarea;
import utn.isi.deso.demo.gestion.service.ProyectoService;

import java.time.Instant;
import java.util.List;

@Controller
public class ProyectoController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(ProyectoController.class);

    @Autowired
    private ProyectoService proyectoService;
    
    @GetMapping("/proyectos/nuevo")
    public String mostrarFormularioAlta(Model model) {
        Proyecto nuevoProyecto = new Proyecto();
        nuevoProyecto.setPresupuestoMaximo(9990.0);
        model.addAttribute("proyecto", nuevoProyecto);
        model.addAttribute("fecha", Instant.now());
        return "nuevo-proyecto";
    }

    @PostMapping("/proyectos/guardar")
    public String guardarProyecto(Proyecto proyecto) {
        logger.info("Guardando proyecto: " + proyecto);
        proyectoService.create(proyecto);
        return "redirect:/proyectos";
    }

    @GetMapping("/proyectos")
    public String listarProyectos(Model model) {
        List<Proyecto> proyectos = proyectoService.findAll();
        model.addAttribute("proyectos", proyectos);
        return "proyectos";
    }

    @GetMapping("/proyectos/{id}")
    public String mostrarProyectoPorId(@PathVariable Integer id, Model model) {
        Proyecto proyecto = proyectoService.findById(id).orElseThrow(()-> new RuntimeException("Proyecto no encontrado"));
        logger.info("Proyecto recuperado {}", proyecto);
        if (proyecto != null) {
            model.addAttribute("proyecto", proyecto);
            return "detalle-proyecto";
        } else {
            return "proyecto-no-encontrado";
        }
    }
}