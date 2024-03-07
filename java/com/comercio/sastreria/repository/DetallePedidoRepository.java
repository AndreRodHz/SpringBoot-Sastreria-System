package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comercio.sastreria.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

}