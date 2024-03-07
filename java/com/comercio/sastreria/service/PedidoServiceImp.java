package com.comercio.sastreria.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.DetallePedido;
import com.comercio.sastreria.model.Inferior;
import com.comercio.sastreria.model.Pedido;
import com.comercio.sastreria.model.Superior;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.repository.DetallePedidoRepository;
import com.comercio.sastreria.repository.InferiorRepository;
import com.comercio.sastreria.repository.PedidoRepository;
import com.comercio.sastreria.repository.SuperiorRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class PedidoServiceImp implements PedidoService{

	@Autowired
	private PedidoRepository pedidoRepository; // Inyectando un OBJETO
	@Autowired
	private DetallePedidoRepository detallePedidoRepository;
	@Autowired
	private SuperiorRepository superiorRepository;
	@Autowired
	private InferiorRepository inferiorRepository;
	
	@Override
	public Pedido registrar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	@Override
	public DetallePedido registrarDetalle(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}

	@Override
	public Optional<Pedido> get(String idPedido) {
		return pedidoRepository.findById(idPedido);
	}

	@Override
	public void update(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	@Override
	public void delete(String idPedido) {
		pedidoRepository.deleteById(idPedido);
	}
	
	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager

	@Override
	public String obtenerNuevoIdPedido() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_pedido");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}

	@Override
	public BigDecimal calcularTotalPedido(List<DetallePedido> carrito) {
		BigDecimal total = BigDecimal.ZERO;
	    for (DetallePedido item : carrito) {
	        total = total.add(item.getImpDP());
	    }
	    return total;
	}

	@Override
	public Transaccion obtenerTransaccionPorIdPedido(String idPedido) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(idPedido);
	    if (pedidoOptional.isPresent()) {
	        Pedido pedido = pedidoOptional.get();
	        return pedido.getTransaccion();
	    } else {
	        // Manejo de errores si la venta no se encuentra
	        return null;
	    }
	}

	@Override
	public List<DetallePedido> obtenerDetallesPorIdPedido(String idPedido) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(idPedido);
	    if (pedidoOptional.isPresent()) {
	        Pedido pedido = pedidoOptional.get();
	        return pedido.getDetallesPedido();
	    } else {
	        // Manejo de errores si la venta no se encuentra
	        return Collections.emptyList();
	    }
	}

	@Override
	public Superior obtenerMedidasSuperiores(String idPedido) {
		return superiorRepository.findSuperiorByPedidoId(idPedido);
	}

	@Override
	public Inferior obtenerMedidasInferiores(String idPedido) {
		return inferiorRepository.findInferiorByPedidoId(idPedido);
	}

	@Override
	public List<Pedido> findByFecha() {
		return pedidoRepository.findAllOrderedByFecha();
	}

	@Override
	public List<Pedido> findByWeek() {
		LocalDate now = LocalDate.now();
	    LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	    LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

	    LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
	    LocalDateTime endOfWeekDateTime = endOfWeek.atTime(LocalTime.MAX);

	    return pedidoRepository.findByfechaPedidoBetween(startOfWeekDateTime, endOfWeekDateTime);
	}
}