package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comercio.sastreria.model.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, String> {

}