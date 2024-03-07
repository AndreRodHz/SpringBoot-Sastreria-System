package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comercio.sastreria.model.ProductoPedido;
import com.comercio.sastreria.repository.ProductoPedidoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class ProductoPedidoServiceImp implements ProductoPedidoService{

	@Autowired
	ProductoPedidoRepository productoPedidoRepository;
	
	@Override
	public ProductoPedido registrar(ProductoPedido productoPedido) {
		return productoPedidoRepository.save(productoPedido);
	}

	@Override
	public Optional<ProductoPedido> get(String idProductoPedido) {
		return productoPedidoRepository.findById(idProductoPedido);
	}

	@Override
	public void update(ProductoPedido productoPedido) {
		productoPedidoRepository.save(productoPedido);
	}

	@Override
	public void delete(String idProductoPedido) {
		productoPedidoRepository.deleteById(idProductoPedido);
	}

	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager
	
	@Override
	public String obtenerNuevoIdProductoPedido() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_pro_pedi");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<ProductoPedido> findAll() {
		return productoPedidoRepository.findAll();
	}
}