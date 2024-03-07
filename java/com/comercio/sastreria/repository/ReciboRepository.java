package com.comercio.sastreria.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.comercio.sastreria.model.Recibo;

public interface ReciboRepository extends JpaRepository<Recibo, String> {
	
	@Query("SELECT r FROM Recibo r ORDER BY r.fechaRecibo DESC")
    List<Recibo> findAllOrderedByFecha();
	
	@Query("SELECT COUNT(r) > 0 FROM Recibo r WHERE r.numeroComprobanteRecibo = :numeroComprobante")
    boolean existsByNumeroComprobante(@Param("numeroComprobante") String numeroComprobante);

	
	List<Recibo> findByFechaReciboBetweenAndEmpleado_IdEmpleado(LocalDateTime fechaInicio, LocalDateTime fechaFin, String idEmpleado);
}