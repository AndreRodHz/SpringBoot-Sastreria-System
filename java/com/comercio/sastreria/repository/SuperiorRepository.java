package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Superior;

public interface SuperiorRepository extends JpaRepository<Superior, Integer> {
	
	@Query("SELECT s FROM Superior s WHERE s.pedido.idPedido = :idPedido")
    Superior findSuperiorByPedidoId(String idPedido);

}