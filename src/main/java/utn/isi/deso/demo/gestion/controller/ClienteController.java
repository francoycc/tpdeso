package utn.isi.deso.demo.gestion.controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import utn.isi.deso.demo.gestion.modelo.Cliente;
import utn.isi.deso.demo.gestion.modelo.Coordenada;
import utn.isi.deso.demo.gestion.service.ClienteService;

@Controller
public class ClienteController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;
    
    @GetMapping("/cliente/nuevo")
    public String mostrarFormularioAlta(Model model) {
        Cliente cliente = new Cliente();
        cliente.setCoordenada(new Coordenada());
        model.addAttribute("cliente", cliente);
        return "crearEditarCliente";
    }

    @PostMapping("/cliente/guardar")
    public String guardarCliente(Cliente cliente) {
        logger.info("Guardando Cliente: " + cliente);
        cliente.getCoordenada().setLat(Double.valueOf(String.valueOf(cliente.getCoordenada().getLat())));
        cliente.getCoordenada().setLng(Double.valueOf(String.valueOf(cliente.getCoordenada().getLng())));
        if(cliente.getId() != null && cliente.getId() != 0) {
            Cliente existente = clienteService.findById(cliente.getId()).orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
            existente.setNombre(cliente.getNombre());
            existente.setCuit(cliente.getCuit());
            existente.setEmail(cliente.getEmail());
            existente.setDireccion(cliente.getDireccion());
            existente.getCoordenada().setLat(cliente.getCoordenada().getLat());
            existente.getCoordenada().setLng(cliente.getCoordenada().getLng());
            clienteService.update(existente);
        } else {
            clienteService.create(cliente);
        }
        return "redirect:/cliente";
    }

    @GetMapping("/cliente")
    public String listarClientes(Model model) {
        Cliente cliente = new Cliente();
        cliente.setCoordenada(new Coordenada());
        model.addAttribute("cliente", cliente);
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "cliente";
    }
    
    @GetMapping("/cliente/eliminarCliente/{id}")
    public String eliminarCliente(@PathVariable Integer id) {
        clienteService.delete(id);
        return "redirect:/cliente";
    }
    
    @GetMapping("/cliente/editarCliente/{id}")
    public String editarVendedor(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteService.findById(id).orElseThrow(()-> new RuntimeException("Cliente no encontrado"));
        logger.info("Cliente recuperado {}", cliente);
        model.addAttribute("cliente", cliente);
        return "crearEditarCliente";
    }
    
    @PostMapping("/cliente")
    public String buscarCliente(Cliente cliente, Model model) {
        model.addAttribute("vendedor", cliente);
        List<Cliente> clientes = clienteService.buscarPorCriterios(
                cliente.getNombre(),
                cliente.getCuit(),
                cliente.getEmail(),
                cliente.getDireccion(),
                cliente.getCoordenada() != null ? cliente.getCoordenada().getLat(): null,
                cliente.getCoordenada() != null ? cliente.getCoordenada().getLng(): null
        );
        model.addAttribute("clientes", clientes);
        logger.info("Clientessss: " + clientes);
        return "cliente";
    }
}