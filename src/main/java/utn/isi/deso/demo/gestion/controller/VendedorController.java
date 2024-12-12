package utn.isi.deso.demo.gestion.controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import utn.isi.deso.demo.gestion.modelo.Vendedor;
import utn.isi.deso.demo.gestion.service.VendedorService;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import utn.isi.deso.demo.gestion.modelo.Coordenada;

@Controller
public class VendedorController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(VendedorController.class);

    @Autowired
    private VendedorService vendedorService;
    
    @GetMapping("/vendedor/nuevo")
    public String mostrarFormularioAlta(Model model) {
        Vendedor vendedor = new Vendedor();
        vendedor.setCoordenada(new Coordenada());
        model.addAttribute("vendedor", vendedor);
        return "crearEditarVendedor";
    }

    @PostMapping("/vendedor/guardar")
    public String guardarVendedor(Vendedor vendedor) {
        logger.info("Guardando Vendedor: " + vendedor);
        vendedor.getCoordenada().setLat(Double.valueOf(String.valueOf(vendedor.getCoordenada().getLat())));
        vendedor.getCoordenada().setLng(Double.valueOf(String.valueOf(vendedor.getCoordenada().getLng())));
        if(vendedor.getId() != null && vendedor.getId() != 0) {
            Vendedor existente = vendedorService.findById(vendedor.getId()).orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
            existente.setNombre(vendedor.getNombre());
            existente.setDireccion(vendedor.getDireccion());
            existente.getCoordenada().setLat(vendedor.getCoordenada().getLat());
            existente.getCoordenada().setLng(vendedor.getCoordenada().getLng());
            vendedorService.update(existente);
        } else {
            vendedorService.create(vendedor);
        }
        return "redirect:/vendedor";
    }

    @GetMapping("/vendedor")
    public String listarVendedores(Model model) {
        Vendedor vendedor = new Vendedor();
        vendedor.setCoordenada(new Coordenada());
        model.addAttribute("vendedor", vendedor);
        List<Vendedor> vendedores = vendedorService.findAll();
        model.addAttribute("vendedores", vendedores);
        return "vendedor";
    }
    
    @GetMapping("/vendedor/eliminarVendedor/{id}")
    public String eliminarVendedor(@PathVariable Integer id) {
        vendedorService.delete(id);
        return "redirect:/vendedor";
    }
    
    @GetMapping("/vendedor/editarVendedor/{id}")
    public String editarVendedor(@PathVariable Integer id, Model model) {
        Vendedor vendedor = vendedorService.findById(id).orElseThrow(()-> new RuntimeException("Vendedor no encontrado"));
        logger.info("Vendedor recuperado {}", vendedor);
        model.addAttribute("vendedor", vendedor);
        return "crearEditarVendedor";
    }
    
    @PostMapping("/vendedor")
    public String buscarVendedor(Vendedor vendedor, Model model) {
        model.addAttribute("vendedor", vendedor);
        List<Vendedor> vendedores = vendedorService.buscarPorCriterios(
                vendedor.getNombre(),
                vendedor.getDireccion(),
                vendedor.getCoordenada() != null ? vendedor.getCoordenada().getLat(): null,
                vendedor.getCoordenada() != null ? vendedor.getCoordenada().getLng(): null
        );
        model.addAttribute("vendedores", vendedores);
        logger.info("Vendedoresssssssssss: " + vendedores);
        return "vendedor";
    }
}