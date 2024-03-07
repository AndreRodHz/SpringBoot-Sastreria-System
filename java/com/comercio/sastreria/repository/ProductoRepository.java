package com.comercio.sastreria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.comercio.sastreria.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, String> {
	
	@Query("SELECT p FROM Producto p ORDER BY p.fecha DESC")
	List<Producto> findAllOrderedByFecha();
	
	@Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.stockProducto = :nuevoStock WHERE p.idProducto = :idProducto")
    void actualizarStockProducto(String idProducto, Integer nuevoStock);
	
}