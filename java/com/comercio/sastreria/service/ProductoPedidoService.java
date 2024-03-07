package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.ProductoPedido;

public interface ProductoPedidoService {

	public ProductoPedido registrar(ProductoPedido productoPedido);
	public Optional<ProductoPedido> get(String idProductoPedido);
	public void update(ProductoPedido productoPedido);
	public void delete(String idProductoPedido);
	String obtenerNuevoIdProductoPedido(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	
	public List<ProductoPedido> findAll();
}
