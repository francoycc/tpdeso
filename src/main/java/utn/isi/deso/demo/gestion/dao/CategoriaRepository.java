/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package utn.isi.deso.demo.gestion.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import utn.isi.deso.demo.gestion.modelo.Categoria;


public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
     Optional<Categoria> findByTipoItem(String tipoItem);
}
