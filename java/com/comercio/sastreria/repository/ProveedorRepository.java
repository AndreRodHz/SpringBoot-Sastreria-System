package com.comercio.sastreria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, String>{
	
	@Query("SELECT p FROM Proveedor p ORDER BY p.fecha DESC")
    List<Proveedor> findAllOrderedByFecha();
}