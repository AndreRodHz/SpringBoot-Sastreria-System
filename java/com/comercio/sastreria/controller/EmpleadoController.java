package com.comercio.sastreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.comercio.sastreria.model.*;
import com.comercio.sastreria.service.*;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("empleado")
public class EmpleadoController {

	@Autowired
    private EmpleadoService empleadoService;
	@Autowired
    private RolService rolService;
    @Autowired
    private GeneroService generoService;
    @Autowired
    private DocumentoService documentoService;

	@GetMapping("")
	public String empleado(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
		model.addAttribute("empleadosLista", empleadoService.findByFechaDesc());
		return "empleado/empleado";
	}

	@GetMapping("empleado_registrar")
	public String registrar(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
		model.addAttribute("empleadoAuth", empleadoAuth);
		
	    String nuevoIdEmpleado = empleadoService.obtenerNuevoIdEmpleado();
	    List<Rol> roles = rolService.getAllCategorias();
	    List<Genero> generos = generoService.getAllCategorias();
	    List<Documento> documentos = documentoService.getAllCategorias();

	    model.addAttribute("nuevoIdEmpleado", nuevoIdEmpleado);
	    model.addAttribute("roles", roles);
	    model.addAttribute("generos", generos);
	    model.addAttribute("documentos", documentos);
		
	    return "empleado/empleado_registrar";	
	}

    @PostMapping("empleado_registrar")
    public String registrar(@RequestParam String idRol,
    						@RequestParam String idGenero,
                            @RequestParam String idDocumento,
                            Empleado empleado, HttpSession session, Model model) {
        
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
    	if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

    	Timestamp fechaActual = new Timestamp(new Date().getTime());
    	Rol rol = rolService.get(idRol).orElse(null);
        Genero genero = generoService.get(idGenero).orElse(null);
        Documento documento = documentoService.get(idDocumento).orElse(null);

        empleado.setFecha(fechaActual);
        empleado.setRol(rol);
        empleado.setGenero(genero);
        empleado.setDocumento(documento);

        empleadoService.registrar(empleado);

        return "redirect:/empleado";
    }

    @GetMapping("/empleado_editar/{idEmpleado}")
    public String editar(@PathVariable String idEmpleado, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
    	if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	Empleado empleado = new Empleado();
    	Optional<Empleado> optionalEmpleado = empleadoService.get(idEmpleado);
    	empleado = optionalEmpleado.get();
    	List<Rol> roles = rolService.getAllCategorias();
	    List<Genero> generos = generoService.getAllCategorias();
	    List<Documento> documentos = documentoService.getAllCategorias();
	    
	    model.addAttribute("roles", roles);
	    model.addAttribute("generos", generos);
	    model.addAttribute("documentos", documentos);
    	model.addAttribute("empleado", empleado);
    	
    	return "empleado/empleado_editar";
    }

    @PostMapping("empleado_editar")
    public String update(@RequestParam String idRol,
						 @RequestParam String idGenero,
						 @RequestParam String idDocumento,
						 Empleado empleado, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
    	if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

    	Rol rol = rolService.get(idRol).orElse(null);
        Genero genero = generoService.get(idGenero).orElse(null);
        Documento documento = documentoService.get(idDocumento).orElse(null);

        empleado.setFecha(empleado.getFecha());
        empleado.setRol(rol);
        empleado.setGenero(genero);
        empleado.setDocumento(documento);
        
        empleadoService.update(empleado);
    	return "redirect:/empleado";
    }

    @GetMapping("/empleado_eliminar/{idEmpleado}")
    public String delete(@PathVariable String idEmpleado, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
    	if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth); 
    	
    	try {
            empleadoService.delete(idEmpleado);
            return "redirect:/empleado";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar este empleado porque está siendo utilizado en otra tabla.");
            return "redirect:/empleado";
        }
    }

}