package com.comercio.sastreria.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Alquiler;
import com.comercio.sastreria.model.DetalleRecibo;
import com.comercio.sastreria.model.Pedido;
import com.comercio.sastreria.model.Recibo;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.model.Venta;
import com.comercio.sastreria.repository.AlquilerRepository;
import com.comercio.sastreria.repository.DetalleReciboRepository;
import com.comercio.sastreria.repository.PedidoRepository;
import com.comercio.sastreria.repository.ReciboRepository;
import com.comercio.sastreria.repository.VentaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class ReciboServiceImp implements ReciboService{
	
	@Autowired
	ReciboRepository reciboRepository;
	@Autowired
	private VentaRepository ventaRepository;
	@Autowired
	private AlquilerRepository alquilerRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private DetalleReciboRepository detalleReciboRepository;
	
	@Override
	public Recibo registrar(Recibo recibo) {
		return reciboRepository.save(recibo);
	}
	
	@Override
	public DetalleRecibo registrarDetalle(DetalleRecibo detalleRecibo) {
		return detalleReciboRepository.save(detalleRecibo);
	}

	@Override
	public Optional<Recibo> get(String idRecibo) {
		return reciboRepository.findById(idRecibo);
	}

	@Override
	public void update(Recibo recibo) {
		reciboRepository.save(recibo);
	}

	@Override
	public void delete(String idRecibo) {
		reciboRepository.deleteById(idRecibo);
	}
	
	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager

	@Override
	public String obtenerNuevoIdRecibo() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_recibo");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<Recibo> findAll() {
		return reciboRepository.findAll();
	}

	@Override
	public List<Venta> obtenerVentasSinRecibo() {
		return ventaRepository.findVentasSinRecibo();
	}

	@Override
	public List<Alquiler> obtenerAlquileresSinRecibo() {
		return alquilerRepository.findAlquileresSinRecibo();
	}

	@Override
	public List<Pedido> obtenerPedidosSinRecibo() {
		return pedidoRepository.findPedidosSinRecibo();
	}

	@Override
	public BigDecimal calcularTotalRecibo(List<DetalleRecibo> carrito) {
		BigDecimal total = BigDecimal.ZERO;
	    for (DetalleRecibo item : carrito) {
	        total = total.add(item.getMonto());
	    }
	    return total;
	}

	@Override
	public BigDecimal calcularIgv(BigDecimal calcularTotalRecibo) {
		BigDecimal tasaIgv = new BigDecimal("0.18");
		return  calcularTotalRecibo.multiply(tasaIgv);
	}

	@Override
	public BigDecimal calcularTotalFinal(BigDecimal calcularTotalRecibo, BigDecimal calcularIgv) {
		return calcularTotalRecibo.add(calcularIgv);
	}

	@Override
	public List<DetalleRecibo> obtenerDetallesPorIdRecibo(String idRecibo) {
		Optional<Recibo> reciboOptional = reciboRepository.findById(idRecibo);
	    if (reciboOptional.isPresent()) {
	    	Recibo recibo = reciboOptional.get();
	        return recibo.getDetalleRecibo();
	    } else {
	        // Manejo de errores si la venta no se encuentra
	        return Collections.emptyList();
	    }
	}

	@Override
	public List<Recibo> findByFecha() {
		return reciboRepository.findAllOrderedByFecha();
	}

	@Override
	public boolean existsByNumeroComprobante(String numeroComprobante) {
		return reciboRepository.existsByNumeroComprobante(numeroComprobante);
	}

	@Override
	public List<Recibo> obtenerRecibosSemanaEmpleado(String idEmpleado) {
		LocalDate fechaActual = LocalDate.now();
        LocalDate primerDiaSemana = fechaActual.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate ultimoDiaSemana = primerDiaSemana.plusDays(6);

        return reciboRepository.findByFechaReciboBetweenAndEmpleado_IdEmpleado(primerDiaSemana.atStartOfDay(),
                ultimoDiaSemana.atTime(LocalTime.MAX), idEmpleado);
	}

	@Override
	public String generarSerieComprobante(String inputIdCom) {
		Query query = entityManager.createNativeQuery("SELECT generar_serie_comprobante(:inputIdCom)");
        query.setParameter("inputIdCom", inputIdCom);

        Object result = query.getSingleResult();
        return Objects.toString(result, null);
	}

	@Override
	public Object obtenerDetalleEspecifico(Transaccion transaccion) {
		switch (transaccion.getTipoTransaccion()) {
	        case "Venta":
	        	//Obtener Venta con el idTransaccion
	        	Venta ventaEncontrada = ventaRepository.findVentaBytransaccion(transaccion);
	        	//Obtener DetalleVenta con el idVenta
	        	Optional<Venta> ventaOptional = ventaRepository.findById(ventaEncontrada.getIdVenta());
	    	    if (ventaOptional.isPresent()) {
	    	        Venta venta = ventaOptional.get();
	    	        return venta.getDetallesVenta();
	    	    } else {
	    	        return Collections.emptyList();
	    	    }
	        case "Alquiler":
	        	//Obtener Alquiler con el idTransaccion
	        	Alquiler alquilerEncontrada = alquilerRepository.findAlquilerBytransaccion(transaccion);
	        	//Obtener DetalleAlquiler con el idAlquiler
	        	Optional<Alquiler> alquilerOptional = alquilerRepository.findById(alquilerEncontrada.getIdAlquiler());
	    	    if (alquilerOptional.isPresent()) {
	    	    	Alquiler alquiler = alquilerOptional.get();
	    	        return alquiler.getDetallesAlquiler();
	    	    } else {
	    	        return Collections.emptyList();
	    	    }
	        case "Pedido":
	        	//Obtener Pedido con el idTransaccion
	        	Pedido pedidoEncontrada = pedidoRepository.findPedidoBytransaccion(transaccion);
	        	Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoEncontrada.getIdPedido());
	    	    if (pedidoOptional.isPresent()) {
	    	    	Pedido pedido = pedidoOptional.get();
	    	        return pedido.getDetallesPedido();
	    	    } else {
	    	        return Collections.emptyList();
	    	    }
	        default:
	            return null;
		}
	}
}