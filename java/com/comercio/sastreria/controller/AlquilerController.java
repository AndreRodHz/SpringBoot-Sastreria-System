package com.comercio.sastreria.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("alquiler")
public class AlquilerController {
	
	@Autowired
    private AlquilerService alquilerService;
	@Autowired
    private ProductoService productoService;
 	private Cliente clienteBuscar;
 	private List<DetalleAlquiler> carrito = new ArrayList<>();
 	@Autowired
    private ClienteService clienteService;
 	@Autowired
 	private TransaccionService transaccionService;
 	@Autowired
 	private EmpleadoService empleadoService;

 	@GetMapping("")
 	public String alquiler_menu(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		model.addAttribute("alquileresLista", alquilerService.findByFecha());
 		
 		return "alquiler/alquiler";
 	}

 	@GetMapping("/detalle/{idAlquiler}")
 	public String verDetalleAlquiler(@PathVariable String idAlquiler, Model model, HttpSession session) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
    	Transaccion transaccion = alquilerService.obtenerTransaccionPorIdAlquiler(idAlquiler);
    	Optional<Alquiler> alquiler = alquilerService.get(idAlquiler);
        List<DetalleAlquiler> detallesAlquiler = alquilerService.obtenerDetallesPorIdAlquiler(idAlquiler);
        
        model.addAttribute("transaccion", transaccion);
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("detallesAlquiler", detallesAlquiler);
        
 	    return "alquiler/alquiler_detalle";
 	}
 	
 	@GetMapping("/alquiler_nueva")
    public String nuevaAlquiler(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

 		BigDecimal totalAlquiler = calcularTotalAlquiler(carrito).setScale(2, RoundingMode.HALF_UP);
        List<Producto> productoLista = productoService.findAll();

        model.addAttribute("clienteBuscar", clienteBuscar);
        model.addAttribute("productoLista", productoLista);
        model.addAttribute("carrito", carrito);
 	    model.addAttribute("totalAlquiler", totalAlquiler);

        return "alquiler/alquiler_nueva";
    }
 	
 	@GetMapping("/alquiler_re_ingreso")
    public String alquilerReIngreso(HttpSession session, Model model) {

 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        return "alquiler/alquiler_re_ingreso";
    }
 	
 	@PostMapping("/buscar_alquiler")
 	public String buscarAlquiler(@RequestParam("idAlquilerB") String idAlquilerB, HttpSession session, Model model) {
 	    Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
 	    if (empleadoAuth == null) {
 	        return "redirect:/login";
 	    }
 	    model.addAttribute("empleadoAuth", empleadoAuth);

 	    Optional<Alquiler> alquilerOptional = alquilerService.get(idAlquilerB);
 	    Alquiler alquilerBuscado = alquilerOptional.orElse(null);
 	    
 	   if (alquilerBuscado.getEstado().equals("Cerrado") ) {
           model.addAttribute("error", "Este alquiler ya se ha re-ingresado");
           
           return "alquiler/alquiler_re_ingreso";
       }else {
    	   List<DetalleAlquiler> detaAlquiRe = alquilerService.obtenerDetallesPorIdAlquiler(idAlquilerB);
    	    
		   model.addAttribute("alquilerBuscado", alquilerBuscado);
		   model.addAttribute("detaAlquiRe", detaAlquiRe);
	
		   return "alquiler/alquiler_re_ingreso";
       }    
 	}
 	
 	@PostMapping("/restablecer_stock_alquiler/{idAlquiler}")
 	public String restablecerStockAlquiler(@PathVariable String idAlquiler, HttpSession session, Model model) {
 	    Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
 	    if (empleadoAuth == null) {
 	        return "redirect:/login";
 	    }
 	    model.addAttribute("empleadoAuth", empleadoAuth);

 	    List<DetalleAlquiler> detallesAlquiler = alquilerService.obtenerDetallesPorIdAlquiler(idAlquiler);

 	    for (DetalleAlquiler detalleAlquiler : detallesAlquiler) {
 	        Producto producto = detalleAlquiler.getProducto();
 	        int cantidadAlquilada = detalleAlquiler.getCantidadAlquiler();
 	        int stockActual = producto.getStockProducto();

 	        producto.setStockProducto(stockActual + cantidadAlquilada);
 	        productoService.registrar(producto);
 	    }
 	    
 	    Optional<Alquiler> alquilerOptional = alquilerService.get(idAlquiler);
	    Alquiler restauracionAlquiler = alquilerOptional.orElse(null);
	    restauracionAlquiler.setEstado("Cerrado");
	    alquilerService.registrar(restauracionAlquiler);

 	    return "redirect:/alquiler";
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

 	    return "redirect:/alquiler/alquiler_nueva";
 	}
 	
 	@GetMapping("/alquiler_carrito")
 	public String mostrarAlquilerCarrito(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

 	    List<Producto> productosDisponibles = productoService.findByFechaDesc();

 	    model.addAttribute("productosDisponibles", productosDisponibles);
 	    
 	    return "alquiler/alquiler_carrito";
 	}

    @PostMapping("/agregar_al_carrito")
    public String agregarAlCarrito(@RequestParam("idProducto") String idProducto,
    							   @RequestParam("precio_alqui") BigDecimal precio_alqui,
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
                List<Producto> productosDisponibles = productoService.findByFechaDesc();
                
                model.addAttribute("error", "La cantidad solicitada es mayor al stock disponible");
                model.addAttribute("productosDisponibles", productosDisponibles);
                return "alquiler/alquiler_carrito";
            }else {
                DetalleAlquiler detalleAlquiler = new DetalleAlquiler();
                detalleAlquiler.setProducto(producto);
                detalleAlquiler.setPrecioUnidadAlquiler(precio_alqui);
                detalleAlquiler.setCantidadAlquiler(cantidad);
                carrito.add(detalleAlquiler);
            }
        }
        return "redirect:/alquiler/alquiler_nueva";
    }

    @GetMapping("/eliminar_del_carrito/{idProducto}")
    public String eliminarDelCarrito(@PathVariable String idProducto, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
        DetalleAlquiler detalleAEliminar = null;
        for (DetalleAlquiler detalle : carrito) {
            if (detalle.getProducto().getIdProducto().equals(idProducto)) {
                detalleAEliminar = detalle;
                break;
            }
        }
        if (detalleAEliminar != null) {
            carrito.remove(detalleAEliminar);
        }
        return "redirect:/alquiler/alquiler_nueva";
    }

   	private BigDecimal calcularTotalAlquiler(List<DetalleAlquiler> carrito) {
   		BigDecimal total = BigDecimal.ZERO;
   	    for (DetalleAlquiler item : carrito) {
   	        total = total.add(item.getImpDA());
   	    }
   	    return total;
  	}

 	@PostMapping("/guardar_alquiler")
 	public String guardarAlquiler(@RequestParam String idCliente,
 			    			   	  @RequestParam BigDecimal totalAlquiler,
 			    			   	  HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
 	    String idTransaccion = transaccionService.obtenerNuevoIdTransaccion();
 	    Timestamp fechaActual_1 = new Timestamp(new Date().getTime());
 	    String idAlquiler = alquilerService.obtenerNuevoIdAlquiler();
	    Timestamp fechaActual_2 = new Timestamp(new Date().getTime());
		Cliente cliente = clienteService.get(idCliente).orElse(null);
		Empleado empleado = empleadoService.get(empleadoAuth.getIdEmpleado()).orElse(null);
 	    
 	    Transaccion nuevaTransaccion = new Transaccion();
 	    nuevaTransaccion.setIdTransaccion(idTransaccion);
 	    nuevaTransaccion.setTipoTransaccion("Alquiler");
 	    nuevaTransaccion.setFechaTransaccion(fechaActual_1);
 	    nuevaTransaccion.setMonto(totalAlquiler);
 	    nuevaTransaccion.setIdOperacion(idAlquiler);
 	    
 	    transaccionService.registrar(nuevaTransaccion);

 	    Alquiler nuevaAlquiler = new Alquiler();
 	    nuevaAlquiler.setIdAlquiler(idAlquiler);
 	    nuevaAlquiler.setTransaccion(nuevaTransaccion);
 	    nuevaAlquiler.setCliente(cliente);
 	    nuevaAlquiler.setEmpleado(empleado);
 	    nuevaAlquiler.setEstado("Pendiente");
 	    nuevaAlquiler.setFechaAlquiler(fechaActual_2);
 	    nuevaAlquiler.setTotalAlquiler(totalAlquiler);

 	    alquilerService.registrar(nuevaAlquiler);

 	   for (DetalleAlquiler detalle : carrito) {
 		   Producto producto = productoService.get(detalle.getProducto().getIdProducto()).orElse(null);
 		    
 		   if (producto != null) {
 			  DetalleAlquiler detalleAlquiler = new DetalleAlquiler();
 		        
 			  detalleAlquiler.setAlquiler(nuevaAlquiler);
 			  detalleAlquiler.setProducto(producto);
 			  detalleAlquiler.setPrecioUnidadAlquiler(detalle.getPrecioUnidadAlquiler());
 			  detalleAlquiler.setCantidadAlquiler(detalle.getCantidadAlquiler());

		      alquilerService.registrarDetalle(detalleAlquiler); 

		      Integer nuevoStock = producto.getStockProducto() - detalle.getCantidadAlquiler();
		      productoService.actualizarStock(producto.getIdProducto(), nuevoStock);
 		    }
 		}
 	    carrito.clear();

 	    return "redirect:/alquiler";
 	}
}