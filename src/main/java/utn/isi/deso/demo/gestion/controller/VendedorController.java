package utn.isi.deso.demo.gestion.controller;

import utn.isi.deso.demo.gestion.modelo.Vendedor;
import utn.isi.deso.demo.gestion.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import utn.isi.deso.demo.gestion.modelo.Coordenada;
import utn.isi.deso.demo.gestion.service.ProyectoService;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {
    
    Logger logger = org.slf4j.LoggerFactory.getLogger(VendedorController.class);

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/crearEditarVendedor")
    public String formularioCrearVendedor(Model model) {
        model.addAttribute("vendedor", new Vendedor());
        model.addAttribute("coordenada", new Coordenada());
        return "crearEditarVendedor";
    }

    @PostMapping("/guardarVendedor")
    public String guardarVendedor(Vendedor vendedor) {
        logger.info("Guardando Vendedor: " + vendedor);
        vendedorService.guardar(vendedor);
        return "redirect:/vendedor";
    }
    
    @GetMapping
    public String listarVendedores(Model model) {
        List<Vendedor> vendedores = vendedorService.obtenerTodos();
        model.addAttribute("vendedores", vendedores);
        return "vendedor";
    }
    
    @GetMapping("/editar/{id}")
    public String formularioEditarVendedor(@PathVariable int id, Model model) {
        Vendedor vendedor = vendedorService.obtenerPorId(id);
        model.addAttribute("vendedor", vendedor);
        return "crearEditarVendedor";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarVendedor(@PathVariable int id, @ModelAttribute Vendedor vendedor) {
        //vendedor.setId(id);
        vendedorService.guardar(vendedor);
        return "redirect:/vendedor";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVendedor(@PathVariable int id) {
        vendedorService.eliminar(id);
        return "redirect:/vendedor";
    }
}
