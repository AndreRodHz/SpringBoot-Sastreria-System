package com.comercio.sastreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.comercio.sastreria.model.*;
import com.comercio.sastreria.service.*;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("proveedor")
public class ProveedorController {

	@Autowired
    private ProveedorService proveedorService;

	@GetMapping("")
	public String proveedor(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
		
		model.addAttribute("proveedoresLista", proveedorService.findByFechaDesc());
		return "proveedor/proveedor";
	}

	@GetMapping("proveedor_registrar")
	public String registrar(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

	    String nuevoIdProveedor = proveedorService.obtenerNuevoIdProveedor();

	    model.addAttribute("nuevoIdProveedor", nuevoIdProveedor);
		
	    return "proveedor/proveedor_registrar";
	}

    @PostMapping("proveedor_registrar")
    public String registrar(Proveedor proveedor, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	Timestamp fechaActual = new Timestamp(new Date().getTime());
    	
    	proveedor.setFecha(fechaActual);
    	
    	proveedorService.registrar(proveedor);

        return "redirect:/proveedor";
    }

    @GetMapping("/proveedor_editar/{idProveedor}")
    public String editar(@PathVariable String idProveedor, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	Proveedor proveedor = new Proveedor();
    	Optional<Proveedor> optionalProveedor = proveedorService.get(idProveedor);
    	proveedor = optionalProveedor.get();

    	model.addAttribute("proveedor", proveedor);
    	
    	return "proveedor/proveedor_editar";
    }

    @PostMapping("proveedor_editar")
    public String update(Proveedor proveedor, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	proveedor.setFecha(proveedor.getFecha());
    	proveedorService.update(proveedor);
    	
    	return "redirect:/proveedor";
    } 

    @GetMapping("/proveedor_eliminar/{idProveedor}")
    public String delete(@PathVariable String idProveedor, RedirectAttributes redirectAttributes,
    				     HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	try {
            proveedorService.delete(idProveedor);
            return "redirect:/proveedor";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar este proveedor porque está siendo utilizado en otra tabla.");
            return "redirect:/proveedor";
        }
    }
}