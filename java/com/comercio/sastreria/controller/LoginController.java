package com.comercio.sastreria.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.comercio.sastreria.model.Alquiler;
import com.comercio.sastreria.model.Empleado;
import com.comercio.sastreria.model.Pedido;
import com.comercio.sastreria.model.Producto;
import com.comercio.sastreria.model.Recibo;
import com.comercio.sastreria.model.Venta;
import com.comercio.sastreria.service.AlquilerService;
import com.comercio.sastreria.service.EmpleadoService;
import com.comercio.sastreria.service.PedidoService;
import com.comercio.sastreria.service.ProductoService;
import com.comercio.sastreria.service.ReciboService;
import com.comercio.sastreria.service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private AlquilerService alquilerService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ReciboService reciboService;
    @Autowired
    private ProductoService productoService;
    
    @GetMapping("/login")
    public String login_page() {
        return "login";
    }
    
    @PostMapping("/loguear")
    public String loginSubmit(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {
        Optional<Empleado> empleado = empleadoService.findByEmailEmpleado(email);
        if (empleado.isPresent() && empleado.get().getPasswordEmpleado().equals(password)) {
            session.setAttribute("empleadoAuth", empleado.get());
            
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Credenciales incorrectas. Por favor, inténtalo nuevamente.");
            
            return "login";
        }
    }
    
    @GetMapping("/menu")
    public String loginMenu(HttpSession session, Model model) {
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");

        if (empleadoAuth == null) {
            return "redirect:/login";
        }
        
        List<Venta> ventasSemana = ventaService.findByWeek();
        List<Alquiler> alquileresSemana = alquilerService.findByWeek();
        List<Pedido> pedidosSemana = pedidoService.findByWeek();

        //Venta
        Map<Empleado, Integer> ventasPorEmpleado = new HashMap<>();

        for (Venta venta : ventasSemana) {
            Empleado empleado = venta.getEmpleado();
            if (ventasPorEmpleado.containsKey(empleado)) {
            	ventasPorEmpleado.put(empleado, ventasPorEmpleado.get(empleado) + 1);
            } else {
            	ventasPorEmpleado.put(empleado, 1);
            }
        }
        
        List<String> nombresEmpleadosVentas = new ArrayList<>();
        List<Integer> cantidadVentas = new ArrayList<>();
        
        for (Map.Entry<Empleado, Integer> entry : ventasPorEmpleado.entrySet()) {
        	nombresEmpleadosVentas.add(entry.getKey().getNombreEmpleado());
            cantidadVentas.add(entry.getValue());
        }
        
        //Alquiler
        Map<Empleado, Integer> alquileresPorEmpleado = new HashMap<>();

        for (Alquiler alquiler : alquileresSemana) {
            Empleado empleado = alquiler.getEmpleado();
            if (alquileresPorEmpleado.containsKey(empleado)) {
            	alquileresPorEmpleado.put(empleado, alquileresPorEmpleado.get(empleado) + 1);
            } else {
            	alquileresPorEmpleado.put(empleado, 1);
            }
        }
        
        List<String> nombresEmpleadosAlquileres = new ArrayList<>();
        List<Integer> cantidadAlquileres = new ArrayList<>();
        
        for (Map.Entry<Empleado, Integer> entry : alquileresPorEmpleado.entrySet()) {
        	nombresEmpleadosAlquileres.add(entry.getKey().getNombreEmpleado());
        	cantidadAlquileres.add(entry.getValue());
        }
        
        //Pedido
        Map<Empleado, Integer> pedidosPorEmpleado = new HashMap<>();

        for (Pedido pedido : pedidosSemana) {
            Empleado empleado = pedido.getEmpleado();
            if (pedidosPorEmpleado.containsKey(empleado)) {
            	pedidosPorEmpleado.put(empleado, pedidosPorEmpleado.get(empleado) + 1);
            } else {
            	pedidosPorEmpleado.put(empleado, 1);
            }
        }
        
        List<String> nombresEmpleadosPedidos = new ArrayList<>();
        List<Integer> cantidadPedidos = new ArrayList<>();
        
        for (Map.Entry<Empleado, Integer> entry : pedidosPorEmpleado.entrySet()) {
        	nombresEmpleadosPedidos.add(entry.getKey().getNombreEmpleado());
        	cantidadPedidos.add(entry.getValue());
        }
        
        List<Recibo> recibosSemana = reciboService.obtenerRecibosSemanaEmpleado(empleadoAuth.getIdEmpleado());
        
        // Procesar la cantidad de recibos por día de la semana
        int[] cantidadRecibosPorDiaSemana = new int[7]; // Array para almacenar la cantidad de recibos por día de la semana (índices de 0 a 6)

        for (Recibo recibo : recibosSemana) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(recibo.getFechaRecibo());

            int diaSemana = cal.get(Calendar.DAY_OF_WEEK); // Obtener el día de la semana (Domingo = 1, Lunes = 2, ..., Sábado = 7)
            diaSemana--;

            cantidadRecibosPorDiaSemana[diaSemana]++;
        }
        
        List<Producto> listaProductos = productoService.findByFechaDesc();
        List<String> nombresProductos = new ArrayList<>();
        List<Integer> stockProductos = new ArrayList<>();

        for (Producto producto : listaProductos) {
            nombresProductos.add(producto.getNombreProducto());
            stockProductos.add(producto.getStockProducto());
        }

        model.addAttribute("nombresProductos", nombresProductos);
        model.addAttribute("stockProductos", stockProductos);


        
        // Ahora, tenemos la cantidad de recibos por día de la semana para el empleado actual
        model.addAttribute("cantidadRecibosPorDiaSemana", cantidadRecibosPorDiaSemana);
        // También necesitamos pasar al modelo el nombre del empleado u otra información que desees mostrar
        model.addAttribute("empleadoAuth", empleadoAuth);
        
        

        model.addAttribute("nombresEmpleadosVentas", nombresEmpleadosVentas);
        model.addAttribute("cantidadVentas", cantidadVentas);
        
        model.addAttribute("nombresEmpleadosAlquileres", nombresEmpleadosAlquileres);
        model.addAttribute("cantidadAlquileres", cantidadAlquileres);
        
        model.addAttribute("nombresEmpleadosPedidos", nombresEmpleadosPedidos);
        model.addAttribute("cantidadPedidos", cantidadPedidos);
        
    	model.addAttribute("empleadoAuth", empleadoAuth);
        return "menu";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        
        return "redirect:/login";
    }
}