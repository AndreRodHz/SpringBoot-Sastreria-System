package com.comercio.sastreria.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Alquiler;
import com.comercio.sastreria.model.Transaccion;

public interface AlquilerRepository  extends JpaRepository<Alquiler, String> {

	@Query("SELECT a FROM Alquiler a LEFT JOIN DetalleRecibo r ON a.transaccion.idTransaccion = r.transaccion.idTransaccion WHERE r.recibo.idRecibo IS NULL ORDER BY a.fechaAlquiler DESC")
	List<Alquiler> findAlquileresSinRecibo();
	
	@Query("SELECT a FROM Alquiler a ORDER BY a.fechaAlquiler DESC")
    List<Alquiler> findAllOrderedByFecha();
	
	List<Alquiler> findByfechaAlquilerBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	Alquiler findAlquilerBytransaccion(Transaccion transaccion);
}