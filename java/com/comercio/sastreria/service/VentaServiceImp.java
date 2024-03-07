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
import com.comercio.sastreria.model.DetalleVenta;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.model.Venta;
import com.comercio.sastreria.repository.DetalleVentaRepository;
import com.comercio.sastreria.repository.VentaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class VentaServiceImp implements VentaService{

	@Autowired
	private VentaRepository ventaRepository;
	@Autowired
	private DetalleVentaRepository detalleVentaRepository;
	
	@Override
	public Venta registrar(Venta venta) {
		return ventaRepository.save(venta);
	}
	
	@Override
	public DetalleVenta registrarDetalle(DetalleVenta detalleVenta) {
		return detalleVentaRepository.save(detalleVenta);
	}

	@Override
	public Optional<Venta> get(String idVenta) {
		return ventaRepository.findById(idVenta);
	}

	@Override
	public void update(Venta venta) {
		ventaRepository.save(venta);
	}

	@Override
	public void delete(String idVenta) {
		ventaRepository.deleteById(idVenta);
	}

	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager
	
	@Override
	public String obtenerNuevoIdVenta() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_venta");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<Venta> findAll() {
		return ventaRepository.findAll();
	}

	@Override
	public BigDecimal calcularTotalVenta(List<DetalleVenta> carrito) {
		BigDecimal total = BigDecimal.ZERO;
	    for (DetalleVenta item : carrito) {
	        total = total.add(item.getImpDV());
	    }
	    return total;
	}

	@Override
	public Transaccion obtenerTransaccionPorIdVenta(String idVenta) {
		Optional<Venta> ventaOptional = ventaRepository.findById(idVenta);
	    if (ventaOptional.isPresent()) {
	        Venta venta = ventaOptional.get();
	        return venta.getTransaccion();
	    } else {
	        return null;
	    }
	}

	@Override
	public List<DetalleVenta> obtenerDetallesPorIdVenta(String idVenta) {
		Optional<Venta> ventaOptional = ventaRepository.findById(idVenta);
	    if (ventaOptional.isPresent()) {
	        Venta venta = ventaOptional.get();
	        return venta.getDetallesVenta();
	    } else {
	        return Collections.emptyList();
	    }
	}

	@Override
	public List<Venta> findByFecha() {
		return ventaRepository.findAllOrderedByFecha();
	}

	@Override
	public List<Venta> findByWeek() {
		LocalDate now = LocalDate.now();
	    LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	    LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

	    LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
	    LocalDateTime endOfWeekDateTime = endOfWeek.atTime(LocalTime.MAX);

	    return ventaRepository.findByfechaVentaBetween(startOfWeekDateTime, endOfWeekDateTime);
	}
}