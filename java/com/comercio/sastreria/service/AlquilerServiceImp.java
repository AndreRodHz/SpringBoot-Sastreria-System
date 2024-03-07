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
import com.comercio.sastreria.model.Alquiler;
import com.comercio.sastreria.model.DetalleAlquiler;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.repository.AlquilerRepository;
import com.comercio.sastreria.repository.DetalleAlquilerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class AlquilerServiceImp implements AlquilerService{

	@Autowired
	AlquilerRepository alquilerRepository;
	@Autowired
	DetalleAlquilerRepository detalleAlquilerRepository;
	
	@Override
	public Alquiler registrar(Alquiler alquiler) {
		return alquilerRepository.save(alquiler);
	}

	@Override
	public DetalleAlquiler registrarDetalle(DetalleAlquiler detalleAlquiler) {
		return detalleAlquilerRepository.save(detalleAlquiler);
	}
	
	@Override
	public Optional<Alquiler> get(String idAlquiler) {
		return alquilerRepository.findById(idAlquiler);
	}

	@Override
	public void update(Alquiler alquiler) {
		alquilerRepository.save(alquiler);
	}

	@Override
	public void delete(String idAlquiler) {
		alquilerRepository.deleteById(idAlquiler);
	}
	
	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager

	@Override
	public String obtenerNuevoIdAlquiler() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_alquiler");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<Alquiler> findAll() {
		return alquilerRepository.findAll();
	}
	
	@Override
	public BigDecimal calcularTotalAlquiler(List<DetalleAlquiler> carrito) {
		BigDecimal total = BigDecimal.ZERO;
	    for (DetalleAlquiler item : carrito) {
	        total = total.add(item.getImpDA());
	    }
	    return total;
	}

	@Override
	public Transaccion obtenerTransaccionPorIdAlquiler(String idAlquiler) {
		Optional<Alquiler> alquilerOptional = alquilerRepository.findById(idAlquiler);
	    if (alquilerOptional.isPresent()) {
	        Alquiler alquiler = alquilerOptional.get();
	        return alquiler.getTransaccion();
	    } else {
	        // Manejo de errores si la venta no se encuentra
	        return null;
	    }
	}

	@Override
	public List<DetalleAlquiler> obtenerDetallesPorIdAlquiler(String idAlquiler) {
		Optional<Alquiler> alquilerOptional = alquilerRepository.findById(idAlquiler);
	    if (alquilerOptional.isPresent()) {
	        Alquiler alquiler = alquilerOptional.get();
	        return alquiler.getDetallesAlquiler();
	    } else {
	        // Manejo de errores si la venta no se encuentra
	        return Collections.emptyList();
	    }
	}

	@Override
	public List<Alquiler> findByFecha() {
		return alquilerRepository.findAllOrderedByFecha();
	}

	@Override
	public Alquiler reIngresoAlquiler(String idAlquiler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alquiler> findByWeek() {
		LocalDate now = LocalDate.now();
	    LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	    LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

	    LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
	    LocalDateTime endOfWeekDateTime = endOfWeek.atTime(LocalTime.MAX);

	    return alquilerRepository.findByfechaAlquilerBetween(startOfWeekDateTime, endOfWeekDateTime);
	}
}