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
@RequestMapping("producto")
public class ProductoController {
	
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CondicionService condicionService;

	@GetMapping("")
	public String producto(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
		
		model.addAttribute("productosLista", productoService.findByFechaDesc());
		return "producto/producto";
	}

	@GetMapping("producto_registrar")
	public String registrar(HttpSession session, Model model) {
		
		Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
		
	    String nuevoIdProducto = productoService.obtenerNuevoIdProducto();
	    List<Proveedor> proveedores = proveedorService.findAll();
	    List<Categoria> categorias = categoriaService.getAllCategorias();
	    List<Condicion> condiciones = condicionService.getAllCategorias();

	    model.addAttribute("nuevoIdProducto", nuevoIdProducto);
	    model.addAttribute("proveedores", proveedores);
	    model.addAttribute("categorias", categorias);
	    model.addAttribute("condiciones", condiciones);
		
		return "producto/producto_registrar";
	}

    @PostMapping("producto_registrar")
    public String registrar(@RequestParam String idProveedor,
                            @RequestParam String idCategoria,
                            @RequestParam String idCondicion,
                            Producto producto, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

    	Timestamp fechaActual = new Timestamp(new Date().getTime());
        Proveedor proveedor = proveedorService.get(idProveedor).orElse(null);
        Categoria categoria = categoriaService.get(idCategoria).orElse(null);
        Condicion condicion = condicionService.get(idCondicion).orElse(null);

        producto.setFecha(fechaActual);
        producto.setProveedor(proveedor);
        producto.setCategoria(categoria);
        producto.setCondicion(condicion);

        productoService.registrar(producto);

        return "redirect:/producto";
    }

    @GetMapping("/producto_editar/{idProducto}")
    public String editar(@PathVariable String idProducto, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	Producto producto = new Producto();
    	Optional<Producto> optionalProducto = productoService.get(idProducto);
    	producto = optionalProducto.get();
    	
    	List<Proveedor> proveedores = proveedorService.findAll();
    	List<Categoria> categorias = categoriaService.getAllCategorias();
    	List<Condicion> condiciones = condicionService.getAllCategorias();
    	model.addAttribute("proveedores", proveedores);
	    model.addAttribute("categorias", categorias);
	    model.addAttribute("condiciones", condiciones);
    	model.addAttribute("producto", producto);
    	
    	return "producto/producto_editar";
    }

    @PostMapping("producto_editar")
    public String update(@RequestParam String idProveedor,
            			 @RequestParam String idCategoria,
            			 @RequestParam String idCondicion,
            			 Producto producto, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);

        Proveedor proveedor = proveedorService.get(idProveedor).orElse(null);
        Categoria categoria = categoriaService.get(idCategoria).orElse(null);
        Condicion condicion = condicionService.get(idCondicion).orElse(null);

        producto.setFecha(producto.getFecha());
        producto.setProveedor(proveedor);
        producto.setCategoria(categoria);
        producto.setCondicion(condicion);
    	
    	productoService.update(producto);
    	return "redirect:/producto";
    }

    @GetMapping("/producto_eliminar/{idProducto}")
    public String delete(@PathVariable String idProducto, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
    	
    	Empleado empleadoAuth = (Empleado) session.getAttribute("empleadoAuth");
		if (empleadoAuth == null) {
            return "redirect:/login";
        }
    	model.addAttribute("empleadoAuth", empleadoAuth);
    	
    	try {
            productoService.delete(idProducto);
            return "redirect:/producto";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar este producto porque está siendo utilizado en otra tabla.");
            return "redirect:/producto";
        }
    }
}