package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comercio.sastreria.model.DetalleRecibo;

public interface DetalleReciboRepository extends JpaRepository<DetalleRecibo, Integer> {

}
