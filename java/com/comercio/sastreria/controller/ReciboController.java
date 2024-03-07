package com.comercio.sastreria.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comercio.sastreria.model.*;
import com.comercio.sastreria.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("recibo")
public class ReciboController {
	
	@Autowired
    private ReciboService reciboService;
	@Autowired
    private TransaccionService transaccionService;
	@Autowired
	private ComprobanteService comprobanteService;
	@Autowired
    private ClienteService clienteService;
	@Autowired
    private EmpleadoService empleadoService;
	
	private Cliente clienteBuscar;
	private List<DetalleRecibo> carrito = new ArrayList<>();

	@GetMapping("")
 	public String reciboMenu(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
 		model.addAttribute("recibosLista", reciboService.findByFecha());
 		return "recibo/recibo";
 	}
	
	@PostMapping("/buscar_cliente")
 	public String buscarCliente(@RequestParam("numeroDocC") String numeroDocC, HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

 	    Optional<Cliente> clienteOptional = clienteService.findByDocumento(numeroDocC);

 	    if (clienteOptional.isPresent()) {
 	        clienteBuscar = clienteOptional.get();
 	    } else {
 	        clienteBuscar = null;
 	    }

 	    return "redirect:/recibo/recibo_nuevo";
 	}
	
	@GetMapping("/recibo_nuevo")
	public String nuevoRecibo(HttpSession session, Model model) {
		
	    Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
	    if (empleadoAuth == null) {
	        return "redirect:/login";
	    }
	    model.addAttribute("empleadoAuth", empleadoAuth);

	    String idRecibo = reciboService.obtenerNuevoIdRecibo();
	    List<Comprobante> comprobantes = comprobanteService.getAllCategorias();
	    Timestamp fechaActual = new Timestamp(new Date().getTime());
 	    BigDecimal totalRecibo = calcularTotalRecibo(carrito).setScale(2, RoundingMode.HALF_UP);
 	    BigDecimal igv = reciboService.calcularIgv(totalRecibo).setScale(2, RoundingMode.HALF_UP);
 	    BigDecimal totalFinal = reciboService.calcularTotalFinal(totalRecibo, igv).setScale(2, RoundingMode.HALF_UP);

 	    model.addAttribute("idRecibo", idRecibo);
 	    model.addAttribute("clienteBuscar", clienteBuscar);
 	    model.addAttribute("comprobantes", comprobantes);
 	    model.addAttribute("fechaActual", fechaActual);
        model.addAttribute("carrito", carrito);
 	    model.addAttribute("totalRecibo", totalRecibo);
 	    model.addAttribute("igv", igv);
 	    model.addAttribute("totalFinal", totalFinal);

        return "recibo/recibo_nuevo"; // Asume que tienes una vista llamada "venta_nueva.html"
	}

 	@GetMapping("/recibo_lista_ventas")
 	public String mostrarVentasSinRecibo(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
 		List<Venta> ventasSinRecibo = reciboService.obtenerVentasSinRecibo();
        model.addAttribute("ventasSinRecibo", ventasSinRecibo);
        return "recibo/recibo_lista_ventas";
 	}

    @PostMapping("/agregar_al_carrito")
    public String agregarAlCarrito(@RequestParam("idTransaccion") String idTransaccion,
    							   HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        Optional<Transaccion> transaccionOptional = transaccionService.get(idTransaccion);

        if (transaccionOptional.isPresent()) {
            Transaccion transaccion = transaccionOptional.get();

            DetalleRecibo detalleRecibo = new DetalleRecibo();
            detalleRecibo.setTransaccion(transaccion);
            detalleRecibo.setMonto(transaccion.getMonto());

            carrito.add(detalleRecibo);
        } else {
        	
        }
        return "redirect:/recibo/recibo_nuevo";
    }
 	
    
    @GetMapping("/eliminar_del_carrito/{idTransaccion}")
    public String eliminarDelCarrito(@PathVariable String idTransaccion, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        DetalleRecibo detalleAEliminar = null;
        for (DetalleRecibo detalle : carrito) {
            if (detalle.getTransaccion().getIdTransaccion().equals(idTransaccion)) {
                detalleAEliminar = detalle;
                break;
            }
        }

        if (detalleAEliminar != null) {
            carrito.remove(detalleAEliminar);
        }

        return "redirect:/recibo/recibo_nuevo";
    }

 	private BigDecimal calcularTotalRecibo(List<DetalleRecibo> carrito) {
 		BigDecimal total = BigDecimal.ZERO;
 	    for (DetalleRecibo item : carrito) {
 	        total = total.add(item.getImpDR());
 	    }
 	    return total;
	}
 	
 	@GetMapping("/recibo_lista_alquileres")
 	public String mostrarAlquileresSinRecibo(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
 		List<Alquiler> alquileresSinRecibo = reciboService.obtenerAlquileresSinRecibo();
        model.addAttribute("alquileresSinRecibo", alquileresSinRecibo);
        return "recibo/recibo_lista_alquileres";
 	}
 	
 	@GetMapping("/recibo_lista_pedidos")
 	public String mostrarPedidosSinRecibo(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
 		List<Pedido> pedidosSinRecibo = reciboService.obtenerPedidosSinRecibo();
        model.addAttribute("pedidosSinRecibo", pedidosSinRecibo);
        return "recibo/recibo_lista_pedidos";
 	}
 	
 	@PostMapping("/guardar_recibo")
 	public String guardarVenta(@RequestParam String idRecibo,
 							   @RequestParam String idCliente,
					    	   @RequestParam String idComprobante,
 							   @RequestParam BigDecimal totalRecibo,
 			    			   @RequestParam BigDecimal igv,
 			    			   @RequestParam BigDecimal totalFinal,
			    			   @RequestParam String estado,
			    			   @RequestParam String numeroComprobanteRecibo,
 			    			   HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	Empleado empleado = empleadoService.get(empleadoAuth.getIdEmpleado()).orElse(null);
    	Comprobante comprobante = comprobanteService.get(idComprobante).orElse(null);
    	Cliente cliente = clienteService.get(idCliente).orElse(null);
    	Timestamp fechaActual = new Timestamp(new Date().getTime());
    	
 	    Recibo nuevoRecibo = new Recibo();
 	    nuevoRecibo.setIdRecibo(idRecibo);
 	    nuevoRecibo.setCliente(cliente);
 	    nuevoRecibo.setComprobante(comprobante);
 	    nuevoRecibo.setNumeroComprobanteRecibo(numeroComprobanteRecibo);
 	    nuevoRecibo.setFechaRecibo(fechaActual);
 	    nuevoRecibo.setBaseRecibo(totalRecibo);
 	    nuevoRecibo.setImpuestoRecibo(igv);
 	    nuevoRecibo.setTotalRecibo(totalFinal);
 	    nuevoRecibo.setEstadoRecibo(estado);
 	    nuevoRecibo.setEmpleado(empleado);
 	    
 	    reciboService.registrar(nuevoRecibo);

 	   for (DetalleRecibo detalle : carrito) {
 		   Transaccion transaccion = transaccionService.get(detalle.getTransaccion().getIdTransaccion()).orElse(null);
 		    
 		   if (transaccion != null) {
 			   DetalleRecibo detalleRecibo = new DetalleRecibo();
 		        
 			   detalleRecibo.setRecibo(nuevoRecibo);
 			   detalleRecibo.setTransaccion(transaccion);
 			   detalleRecibo.setMonto(transaccion.getMonto());

 		       reciboService.registrarDetalle(detalleRecibo);
 		    }
 		}
 	    carrito.clear();

 	    return "redirect:/recibo";
 	}
 	
 	@GetMapping("/detalle/{idRecibo}")
 	public String verDetalleRecibo(@PathVariable String idRecibo, Model model, HttpSession session) {
 	    Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
 	    if (empleadoAuth == null) {
 	        return "redirect:/login";
 	    }
 	    model.addAttribute("empleadoAuth", empleadoAuth);

 	    Optional<Recibo> reciboOptional = reciboService.get(idRecibo);
 	    if (reciboOptional.isPresent()) {
 	        Recibo recibo = reciboOptional.get();
 	        List<DetalleRecibo> detallesRecibo = reciboService.obtenerDetallesPorIdRecibo(idRecibo);

 	        // Crear listas para almacenar detalles específicos de cada tipo de transacción
 	        List<Object> detallesVenta = new ArrayList<>();
 	        List<Object> detallesAlquiler = new ArrayList<>();
 	        List<Object> detallesPedido = new ArrayList<>();

 	        // Iterar sobre los detalles del recibo para obtener los detalles específicos de cada transacción
 	        for (DetalleRecibo detalle : detallesRecibo) {
 	            Transaccion transaccion = detalle.getTransaccion();
 	            switch (transaccion.getTipoTransaccion()) {
 	                case "Venta":
 	                    // Obtener detalles específicos de la venta y agregarlos a la lista
 	                    Object detalleVenta = reciboService.obtenerDetalleEspecifico(transaccion);
 	                    detallesVenta.add(detalleVenta);
 	                    break;
 	                case "Alquiler":
 	                    // Obtener detalles específicos del alquiler y agregarlos a la lista
 	                    Object detalleAlquiler = reciboService.obtenerDetalleEspecifico(transaccion);
 	                    detallesAlquiler.add(detalleAlquiler);
 	                    break;
 	                case "Pedido":
 	                    // Obtener detalles específicos del pedido y agregarlos a la lista
 	                    Object detallePedido = reciboService.obtenerDetalleEspecifico(transaccion);
 	                    detallesPedido.add(detallePedido);
 	                    break;
 	                default:
 	                    // Manejo de otros tipos de transacción si es necesario
 	                    break;
 	            }
 	           
 	        }

 	        model.addAttribute("recibo", recibo);
 	        model.addAttribute("detallesRecibo", detallesRecibo);
 	        model.addAttribute("detallesVenta", detallesVenta);
 	        model.addAttribute("detallesAlquiler", detallesAlquiler);
 	        model.addAttribute("detallesPedido", detallesPedido);

 	        return "recibo/recibo_detalle";
 	    } else {
 	        // Manejo de errores si el recibo no se encuentra
 	        return "redirect:/recibo"; // O renderizar una vista de error
 	    }
 	}

 	
 	@GetMapping("/verificar_comprobante")
    public ResponseEntity<?> verificarComprobante(@RequestParam("numeroComprobante") String numeroComprobante) {
        boolean existeComprobante = reciboService.existsByNumeroComprobante(numeroComprobante);
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existeComprobante);
        return ResponseEntity.ok(response);
    }
 	
 	@GetMapping("/obtenerNumeroComprobante")
 	@ResponseBody // Devolverá la respuesta como cuerpo de la respuesta HTTP
 	public String obtenerNumeroComprobante(@RequestParam("idComprobante") String idComprobante) {
 	    // Lógica para obtener el número del comprobante basado en idComprobante
 	    String numeroComprobante = reciboService.generarSerieComprobante(idComprobante);
 	    return numeroComprobante;
 	}

}