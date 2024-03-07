package com.comercio.sastreria.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, String> {
	
	@Query("SELECT v FROM Venta v LEFT JOIN DetalleRecibo r ON v.transaccion.idTransaccion = r.transaccion.idTransaccion WHERE r.recibo.idRecibo IS NULL ORDER BY v.fechaVenta DESC")
	List<Venta> findVentasSinRecibo();
	
	@Query("SELECT v FROM Venta v ORDER BY v.fechaVenta DESC")
    List<Venta> findAllOrderedByFecha();
	
	List<Venta> findByfechaVentaBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	Venta findVentaBytransaccion(Transaccion transaccion);

}