package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comercio.sastreria.model.ProductoPedido;

public interface ProductoPedidoRepository extends JpaRepository<ProductoPedido, String> {

}