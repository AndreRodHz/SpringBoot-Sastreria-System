package com.comercio.sastreria.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Pedido;
import com.comercio.sastreria.model.Transaccion;

public interface PedidoRepository extends JpaRepository<Pedido, String> {

	@Query("SELECT p FROM Pedido p LEFT JOIN DetalleRecibo r ON p.transaccion.idTransaccion = r.transaccion.idTransaccion WHERE r.recibo.idRecibo IS NULL ORDER BY p.fechaPedido DESC")
	List<Pedido> findPedidosSinRecibo();
	
	@Query("SELECT p FROM Pedido p ORDER BY p.fechaPedido DESC")
    List<Pedido> findAllOrderedByFecha();
	
	List<Pedido> findByfechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	Pedido findPedidoBytransaccion(Transaccion transaccion);
}