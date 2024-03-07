package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Inferior;

public interface InferiorRepository extends JpaRepository<Inferior, Integer> {

	@Query("SELECT i FROM Inferior i WHERE i.pedido.idPedido = :idPedido")
    Inferior findInferiorByPedidoId(String idPedido);
}