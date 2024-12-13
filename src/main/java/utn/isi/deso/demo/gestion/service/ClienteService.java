package utn.isi.deso.demo.gestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import utn.isi.deso.demo.gestion.dao.CoordenadaRepository;
import utn.isi.deso.demo.gestion.dao.ClienteRepository;
import utn.isi.deso.demo.gestion.modelo.Cliente;
import utn.isi.deso.demo.gestion.modelo.Coordenada;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private CoordenadaRepository coordenadaRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> findByDescripcion(String description) {
        return clienteRepository.findByNombreContaining(description);
    }

    public Cliente create(Cliente cliente) {
        coordenadaRepository.save(cliente.getCoordenada());
        return clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        Coordenada coordenadaExistente = coordenadaRepository.findById(cliente.getCoordenada().getId()).
                orElseThrow(() -> new RuntimeException("Coordenada no encontrada"));
        coordenadaExistente.setLat(cliente.getCoordenada().getLat());
        coordenadaExistente.setLng(cliente.getCoordenada().getLng());
        coordenadaRepository.save(coordenadaExistente);
        return clienteRepository.save(cliente);
    }

    public void delete(Integer id) {
        clienteRepository.deleteById(id);
    }
    
    public List<Cliente> buscarPorCriterios(String nombre, Integer cuit, String email, String direccion, Double latitud, Double longitud) {
        return clienteRepository.buscarPorCriterios(nombre, cuit, email, direccion, latitud, longitud);
    }

}