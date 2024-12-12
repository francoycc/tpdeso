package utn.isi.deso.demo.gestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.isi.deso.demo.gestion.modelo.Vendedor;

import java.util.List;
import java.util.Optional;
import utn.isi.deso.demo.gestion.dao.CoordenadaRepository;
import utn.isi.deso.demo.gestion.dao.VendedorRepository;
import utn.isi.deso.demo.gestion.modelo.Coordenada;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;
    
    @Autowired
    private CoordenadaRepository coordenadaRepository;

    public List<Vendedor> findAll() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> findById(Integer id) {
        return vendedorRepository.findById(id);
    }

    public List<Vendedor> findByDescripcion(String description) {
        return vendedorRepository.findByNombreContaining(description);
    }

    public Vendedor create(Vendedor vendedor) {
        coordenadaRepository.save(vendedor.getCoordenada());
        return vendedorRepository.save(vendedor);
    }

    public Vendedor update(Vendedor vendedor) {
        Coordenada coordenadaExistente = coordenadaRepository.findById(vendedor.getCoordenada().getId()).
                orElseThrow(() -> new RuntimeException("Coordenada no encontrada"));
        coordenadaExistente.setLat(vendedor.getCoordenada().getLat());
        coordenadaExistente.setLng(vendedor.getCoordenada().getLng());
        coordenadaRepository.save(coordenadaExistente);
        return vendedorRepository.save(vendedor);
    }

    public void delete(Integer id) {
        vendedorRepository.deleteById(id);
    }
    
    public List<Vendedor> buscarPorCriterios(String nombre, String direccion, Double latitud, Double longitud) {
        return vendedorRepository.buscarPorCriterios(nombre, direccion, latitud, longitud);
    }

}