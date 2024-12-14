/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package utn.isi.deso.demo.gestion.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import utn.isi.deso.demo.gestion.modelo.ItemMenu;


public interface ItemMenuRepository extends CrudRepository<ItemMenu, Integer> {
    List<ItemMenu> findByCategoria_TipoItem(String tipoItem);
    List<ItemMenu> findByVendedor_Id(long vendedorId);
}
