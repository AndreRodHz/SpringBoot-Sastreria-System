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
@RequestMapping("cliente")
public class ClienteController {

	@Autowired
    private ClienteService clienteService;
    @Autowired
    private GeneroService generoService;
    @Autowired
    private DocumentoService documentoService;

	@GetMapping("")
	public String cliente(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
		
		model.addAttribute("clientesLista", clienteService.findByFechaDesc());
		return "cliente/cliente";
	}

	@GetMapping("cliente_registrar")
	public String registrar(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
		
	    String nuevoIdCliente = clienteService.obtenerNuevoIdCliente();
	    List<Genero> generos = generoService.getAllCategorias();
	    List<Documento> documentos = documentoService.getAllCategorias();

	    model.addAttribute("nuevoIdCliente", nuevoIdCliente);
	    model.addAttribute("generos", generos);
	    model.addAttribute("documentos", documentos);
		
	    return "cliente/cliente_registrar";
	}

    @PostMapping("cliente_registrar")
    public String registrar(@RequestParam String idGenero,
                            @RequestParam String idDocumento,
                            Cliente cliente, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

    	Timestamp fechaActual = new Timestamp(new Date().getTime());
        Genero genero = generoService.get(idGenero).orElse(null);
        Documento documento = documentoService.get(idDocumento).orElse(null);

        cliente.setFecha(fechaActual);
        cliente.setGenero(genero);
        cliente.setDocumento(documento);

        clienteService.registrar(cliente);

        return "redirect:/cliente";
    }

    @GetMapping("/cliente_editar/{idCliente}")
    public String editar(@PathVariable String idCliente, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	Cliente cliente = new Cliente();
    	Optional<Cliente> optionalCliente = clienteService.get(idCliente);
    	cliente = optionalCliente.get();
    	
	    List<Genero> generos = generoService.getAllCategorias();
	    List<Documento> documentos = documentoService.getAllCategorias();
	    
	    model.addAttribute("generos", generos);
	    model.addAttribute("documentos", documentos);
    	model.addAttribute("cliente", cliente);
    	
    	return "cliente/cliente_editar";
    }

    @PostMapping("cliente_editar")
    public String update(@RequestParam String idGenero,
            			 @RequestParam String idDocumento,
            			 Cliente cliente, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

    	Genero genero = generoService.get(idGenero).orElse(null);
        Documento documento = documentoService.get(idDocumento).orElse(null);

        cliente.setFecha(cliente.getFecha());
        cliente.setGenero(genero);
        cliente.setDocumento(documento);
        
        clienteService.update(cliente);
    	return "redirect:/cliente";
    }

    @GetMapping("/cliente_eliminar/{idCliente}")
    public String delete(@PathVariable String idCliente, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	try {
            clienteService.delete(idCliente);
            return "redirect:/cliente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar este cliente porque está siendo utilizado en otra tabla.");
            return "redirect:/cliente";
        }
    }
}