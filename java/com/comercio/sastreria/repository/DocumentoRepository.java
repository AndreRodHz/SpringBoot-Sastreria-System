package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comercio.sastreria.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, String>{

}