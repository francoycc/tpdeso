package utn.isi.deso.demo.gestion.dao;

import utn.isi.deso.demo.gestion.modelo.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
    // Métodos de consulta personalizados si es necesario
}
