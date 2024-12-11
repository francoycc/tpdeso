package utn.isi.deso.demo.gestion.service;

import utn.isi.deso.demo.gestion.modelo.Vendedor;
import utn.isi.deso.demo.gestion.dao.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> obtenerTodos() {
        return vendedorRepository.findAll();
    }

    public Vendedor obtenerPorId(int id) {
        return vendedorRepository.findById(id).orElse(null);
    }

    public void guardar(Vendedor vendedor) {
        vendedorRepository.save(vendedor);
    }

    public void eliminar(int id) {
        vendedorRepository.deleteById(id);
    }
}
