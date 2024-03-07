package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comercio.sastreria.model.Comprobante;

public interface ComprobanteRepository extends JpaRepository<Comprobante, String> {

}
