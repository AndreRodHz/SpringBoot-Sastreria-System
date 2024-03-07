package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Categoria;
public interface CategoriaService {
	
	public Categoria registrar(Categoria categoria);
	public Optional<Categoria> get(String idCategoria);
	public void update(Categoria categoria);
	public void delete(String idCategoria);
	String obtenerNuevoIdCategoria(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	List<Categoria> getAllCategorias();
}