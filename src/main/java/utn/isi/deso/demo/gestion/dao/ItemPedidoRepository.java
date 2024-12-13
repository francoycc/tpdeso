/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package utn.isi.deso.demo.gestion.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import utn.isi.deso.demo.gestion.modelo.ItemPedido;


public interface ItemPedidoRepository extends CrudRepository<ItemPedido, Long> {
        List<ItemPedido> findByCantidadBetween(double min, double max);

}
