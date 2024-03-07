package com.comercio.sastreria.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, String> {
	 Optional<Cliente> findByNumeroDocCliente(String numeroDocCliente);
	 
	 @Query("SELECT c FROM Cliente c ORDER BY c.fecha DESC")
	 List<Cliente> findAllOrderedByFecha();
}