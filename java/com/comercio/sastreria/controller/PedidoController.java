package com.comercio.sastreria.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
@RequestMapping("pedido")
public class PedidoController {

	@Autowired
    private PedidoService pedidoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProductoPedidoService productoPedidoService;
    private Cliente clienteBuscar;
    private List<DetallePedido> carrito = new ArrayList<>();
    
    @GetMapping("")
 	public String pedido_menu(HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
 		model.addAttribute("pedidosLista", pedidoService.findByFecha());
 		return "pedido/pedido";
 	}

    @GetMapping("/detalle/{idPedido}")
 	public String verDetallePedido(@PathVariable String idPedido, Model model, HttpSession session) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
 		
    	Transaccion transaccion = pedidoService.obtenerTransaccionPorIdPedido(idPedido);
    	Optional<Pedido> pedido = pedidoService.get(idPedido);
        List<DetallePedido> detallesPedido = pedidoService.obtenerDetallesPorIdPedido(idPedido);
        Superior superior = pedidoService.obtenerMedidasSuperiores(idPedido);
        Inferior inferior = pedidoService.obtenerMedidasInferiores(idPedido);
        
        model.addAttribute("transaccion", transaccion);
        model.addAttribute("pedido", pedido);
        model.addAttribute("detallesPedido", detallesPedido);
        model.addAttribute("superior", superior);
        model.addAttribute("inferior", inferior);
        
 	    return "pedido/pedido_detalle";
 	}

   	@GetMapping("/pedido_nuevo")
      public String nuevoPedido(HttpSession session, Model model) {
   		
   		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

	      BigDecimal totalPedido = calcularTotalPedido(carrito).setScale(2, RoundingMode.HALF_UP);
          List<ProductoPedido> productoPPLista = productoPedidoService.findAll();

          model.addAttribute("clienteBuscar", clienteBuscar);
          model.addAttribute("productoLista", productoPPLista);
          model.addAttribute("carrito", carrito);
          model.addAttribute("totalPedido", totalPedido);

          return "pedido/pedido_nuevo";
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

   	    return "redirect:/pedido/pedido_nuevo";
   	}
   	
 	@GetMapping("/pedido_carrito")
 	public String mostrarPedidoCarrito(HttpSession session, Model model) {
 		
 		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

 	    List<ProductoPedido> productosPedidosDisponibles = productoPedidoService.findAll();

 	    model.addAttribute("productosPedidosDisponibles", productosPedidosDisponibles);
 	    
 	    return "pedido/pedido_carrito";
 	}

    @PostMapping("/agregar_al_carrito")
    public String agregarAlCarrito(@RequestParam("idProductoPedido") String idProductoPedido,
    							   @RequestParam("descripDetalleP") String descripDetalleP,
    							   @RequestParam("precioDetalleProductoPedido") BigDecimal precioDetalleProductoPedido,
    							   @RequestParam("cantidadPedido") int cantidadPedido,
    							   HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        Optional<ProductoPedido> productoPedidoOptional = productoPedidoService.get(idProductoPedido);

        if (productoPedidoOptional.isPresent()) {
            ProductoPedido productoPedido = productoPedidoOptional.get();

            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setProductoPedido(productoPedido);
            detallePedido.setDescripDetalleP(descripDetalleP);
            detallePedido.setPrecioDetalleProductoPedido(precioDetalleProductoPedido);
            detallePedido.setCantidadDetalleProductoPedido(cantidadPedido);

            carrito.add(detallePedido);
        } else {
        	
        }
        return "redirect:/pedido/pedido_nuevo";
    }

    @GetMapping("/eliminar_del_carrito/{idProductoPedido}")
    public String eliminarDelCarrito(@PathVariable String idProductoPedido,
    								 HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        DetallePedido detalleAEliminar = null;
        for (DetallePedido detalle : carrito) {
            if (detalle.getProductoPedido().getIdProductoPedido().equals(idProductoPedido)) {
                detalleAEliminar = detalle;
                break;
            }
        }
        if (detalleAEliminar != null) {
            carrito.remove(detalleAEliminar);
        }
        return "redirect:/pedido/pedido_nuevo";
    }

   	private BigDecimal calcularTotalPedido(List<DetallePedido> carrito) {
   		BigDecimal total = BigDecimal.ZERO;
   	    for (DetallePedido item : carrito) {
   	        total = total.add(item.getImpDP());
   	    }
   	    return total;
  	}
   	
 	@Autowired
 	private TransaccionService transaccionService;
 	@Autowired
 	private EmpleadoService empleadoService;
 	@Autowired
 	private SuperiorService superiorService;
 	@Autowired
 	private InferiorService inferiorService;
   	
 	@PostMapping("/guardar_pedido")
 	public String guardarPedido(@RequestParam String idCliente,
		   	  					@RequestParam BigDecimal totalPedido,
		   	  					@RequestParam String descripcionPedido,
		   	  					@RequestParam(name = "medidasSuperiores", required = false) String medidasSuperiores,
		   	  					@RequestParam(name = "medidasInferiores", required = false) String medidasInferiores,
		   	  					@RequestParam Map<String, String> allParams,
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
 	    String idPedido = pedidoService.obtenerNuevoIdPedido();
	    Timestamp fechaActual_2 = new Timestamp(new Date().getTime());
 	    
 	    Transaccion nuevaTransaccion = new Transaccion();
	    nuevaTransaccion.setIdTransaccion(idTransaccion);
	    nuevaTransaccion.setTipoTransaccion("Pedido");
	    nuevaTransaccion.setFechaTransaccion(fechaActual_1);
	    nuevaTransaccion.setMonto(totalPedido);
	    nuevaTransaccion.setIdOperacion(idPedido);
	    
	    transaccionService.registrar(nuevaTransaccion);

 	    Pedido nuevoPedido = new Pedido();
 	    nuevoPedido.setIdPedido(idPedido);
 	    nuevoPedido.setTransaccion(nuevaTransaccion);
 	    nuevoPedido.setCliente(cliente);
 	    nuevoPedido.setEmpleado(empleado);
 	    nuevoPedido.setFechaPedido(fechaActual_2);
 	    nuevoPedido.setDescripcionPedido(descripcionPedido);
 	    nuevoPedido.setTotalPedido(totalPedido);

 	    pedidoService.registrar(nuevoPedido);
 	    
 	    if (allParams.containsKey("medidasSuperiores") && allParams.get("medidasSuperiores").equals("on")) {

 	    	String cuello = allParams.get("cuello");
 	        String longitud = allParams.get("longitud");
 	        String hombros = allParams.get("hombros");
 	        String sisa = allParams.get("sisa");
 	        String biceps = allParams.get("biceps");
 	        String pecho = allParams.get("pecho");
 	        String brazos = allParams.get("brazos");
 	        String largo_cha = allParams.get("largo_cha");
 	        String largo_abri = allParams.get("largo_abri");
 	        
 	        Superior superior = new Superior();
 	        superior.setPedido(nuevoPedido);
 	        superior.setCuello(new BigDecimal(cuello));
 	        superior.setLongitud(new BigDecimal(longitud));
 	        superior.setHombros(new BigDecimal(hombros));
 	        superior.setSisa(new BigDecimal(sisa));
 	        superior.setBiceps(new BigDecimal(biceps));
 	        superior.setPecho(new BigDecimal(pecho));
 	        superior.setBrazos(new BigDecimal(brazos));
 	        superior.setLargoChaleco(new BigDecimal(largo_cha));
 	        superior.setLargoAbrigo(new BigDecimal(largo_abri));
 	        
 	        superiorService.registrar(superior);
 	    }
 	    
 	    if(allParams.containsKey("medidasInferiores") && allParams.get("medidasInferiores").equals("on")) {

 	    	String caderas = allParams.get("caderas");
 	        String largo = allParams.get("largo");
 	        String tiro = allParams.get("tiro");
 	        String posicion = allParams.get("posicion");
 	        String muslos = allParams.get("muslos");

 	        Inferior inferior = new Inferior();
 	        inferior.setPedido(nuevoPedido);
 	        inferior.setCaderas(new BigDecimal(caderas));
 	        inferior.setLargo(new BigDecimal(largo));
 	        inferior.setTiro(new BigDecimal(tiro));
 	        inferior.setPosicion(new BigDecimal(posicion));
 	        inferior.setMuslos(new BigDecimal(muslos));
 	        
 	        inferiorService.registrar(inferior);
 	    }

  	   for (DetallePedido detalle : carrito) {
  		   ProductoPedido productoPedido = productoPedidoService.get(detalle.getProductoPedido().getIdProductoPedido()).orElse(null);
  		    
  		   if (productoPedido != null) {
  		        DetallePedido detallePedido = new DetallePedido();
  		        
  		        detallePedido.setPedido(nuevoPedido);
  		        detallePedido.setProductoPedido(productoPedido);
  		        detallePedido.setDescripDetalleP(detalle.getDescripDetalleP());
  		        detallePedido.setPrecioDetalleProductoPedido(detalle.getPrecioDetalleProductoPedido());
  		        detallePedido.setCantidadDetalleProductoPedido(detalle.getCantidadDetalleProductoPedido());

  		        pedidoService.registrarDetalle(detallePedido);
  		    }
  		}
  	    carrito.clear();

 	   	return "redirect:/pedido";
 	}
}