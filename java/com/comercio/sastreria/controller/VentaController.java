package com.comercio.sastreria.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.comercio.sastreria.model.*;
import com.comercio.sastreria.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("venta")
public class VentaController {
	
	@Autowired
    private VentaService ventaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProductoService productoService;
    private Cliente clienteBuscar;
    private List<DetalleVenta> carrito = new ArrayList<>();
    
 	@GetMapping("")
 	public String venta_menu(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
 		model.addAttribute("ventasLista", ventaService.findByFecha());
 		return "venta/venta";
 	}
 	
 	@GetMapping("/detalle/{idVenta}")
 	public String verDetalleVenta(@PathVariable String idVenta, Model model, HttpSession session) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
    	Transaccion transaccion = ventaService.obtenerTransaccionPorIdVenta(idVenta);
    	Optional<Venta> venta = ventaService.get(idVenta);
        List<DetalleVenta> detallesVenta = ventaService.obtenerDetallesPorIdVenta(idVenta);
        
        model.addAttribute("transaccion", transaccion);
        model.addAttribute("venta", venta);
        model.addAttribute("detallesVenta", detallesVenta);
        
 	    return "venta/venta_detalle";
 	}

 	@GetMapping("/venta_nueva")
    public String nuevaVenta(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

 	    BigDecimal totalVenta = calcularTotalVenta(carrito).setScale(2, RoundingMode.HALF_UP);
        List<Producto> productoLista = productoService.findAll();

        model.addAttribute("clienteBuscar", clienteBuscar);
        model.addAttribute("productoLista", productoLista);
        model.addAttribute("carrito", carrito);
 	    model.addAttribute("totalVenta", totalVenta);

        return "venta/venta_nueva";
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

 	    return "redirect:/venta/venta_nueva";
 	}
 	
 	@GetMapping("/venta_carrito")
 	public String mostrarVentaCarrito(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

 	    List<Producto> productosDisponibles = productoService.findByFechaDesc();

 	    model.addAttribute("productosDisponibles", productosDisponibles);
 	    
 	    return "venta/venta_carrito";
 	}
 	
    @PostMapping("/agregar_al_carrito")
    public String agregarAlCarrito(@RequestParam("idProducto") String idProducto,
    							   @RequestParam("cantidad") int cantidad,
    							   HttpSession session, Model model) {
    	
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        Optional<Producto> productoOptional = productoService.get(idProducto);

        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();

            if (cantidad > producto.getStockProducto()) {
                model.addAttribute("error", "La cantidad solicitada es mayor al stock disponible");

                List<Producto> productosDisponibles = productoService.findByFechaDesc();
                
                model.addAttribute("productosDisponibles", productosDisponibles);
                
                return "venta/venta_carrito";
            }else {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setProducto(producto);
                detalleVenta.setCantidadVenta(cantidad);

                carrito.add(detalleVenta);
            }
        }
        return "redirect:/venta/venta_nueva";
    }

    @GetMapping("/eliminar_del_carrito/{idProducto}")
    public String eliminarDelCarrito(@PathVariable String idProducto, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        DetalleVenta detalleAEliminar = null;
        for (DetalleVenta detalle : carrito) {
            if (detalle.getProducto().getIdProducto().equals(idProducto)) {
                detalleAEliminar = detalle;
                break;
            }
        }
        if (detalleAEliminar != null) {
            carrito.remove(detalleAEliminar);
        }

        return "redirect:/venta/venta_nueva";
    }

 	private BigDecimal calcularTotalVenta(List<DetalleVenta> carrito) {
 		BigDecimal total = BigDecimal.ZERO;
 	    for (DetalleVenta item : carrito) {
 	        total = total.add(item.getImpDV());
 	    }
 	    return total;
	}
 	
 	@Autowired
 	private TransaccionService transaccionService;
 	@Autowired
 	private EmpleadoService empleadoService;
 	
 	@PostMapping("/guardar_venta")
 	public String guardarVenta(@RequestParam String idCliente,
 			    			   @RequestParam BigDecimal totalVenta,
 			    			   HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
        Cliente cliente = clienteService.get(idCliente).orElse(null);
 	    Empleado empleado = empleadoService.get(empleadoAuth.getIdEmpleado()).orElse(null);
 	    String idTransaccion = transaccionService.obtenerNuevoIdTransaccion();
 	    Timestamp fechaActual_1 = new Timestamp(new Date().getTime());
 	    String idVenta = ventaService.obtenerNuevoIdVenta();
	    Timestamp fechaActual_2 = new Timestamp(new Date().getTime());
 	    
 	    Transaccion nuevaTransaccion = new Transaccion();
 	    nuevaTransaccion.setIdTransaccion(idTransaccion);
 	    nuevaTransaccion.setTipoTransaccion("Venta");
 	    nuevaTransaccion.setFechaTransaccion(fechaActual_1);
 	    nuevaTransaccion.setMonto(totalVenta);
 	    nuevaTransaccion.setIdOperacion(idVenta);
 	    
 	    transaccionService.registrar(nuevaTransaccion);

 	    Venta nuevaVenta = new Venta();
 	    nuevaVenta.setIdVenta(idVenta);
 	    nuevaVenta.setTransaccion(nuevaTransaccion);
 	    nuevaVenta.setCliente(cliente);
 	    nuevaVenta.setEmpleado(empleado);
 	    nuevaVenta.setFechaVenta(fechaActual_2);
 	    nuevaVenta.setTotalVenta(totalVenta);

 	    ventaService.registrar(nuevaVenta);

 	   for (DetalleVenta detalle : carrito) {
 		   Producto producto = productoService.get(detalle.getProducto().getIdProducto()).orElse(null);
 		    
 		   if (producto != null) {
 		        DetalleVenta detalleVenta = new DetalleVenta();
 		        
 		        detalleVenta.setVenta(nuevaVenta);
 		        detalleVenta.setProducto(producto);
 		        detalleVenta.setPrecioUnidadVenta(producto.getPrecioProducto());
 		        detalleVenta.setCantidadVenta(detalle.getCantidadVenta());

 		        ventaService.registrarDetalle(detalleVenta);

 		        Integer nuevoStock = producto.getStockProducto() - detalle.getCantidadVenta();
 		        productoService.actualizarStock(producto.getIdProducto(), nuevoStock);
 		    }
 		}
 	    carrito.clear();

 	    return "redirect:/venta";
 	}
}