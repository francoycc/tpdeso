package utn.isi.deso.demo.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.isi.deso.demo.gestion.modelo.Coordenada;

@Repository
public interface CoordenadaRepository extends JpaRepository<Coordenada, Integer> {
}