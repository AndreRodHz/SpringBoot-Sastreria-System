package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Cliente;
import com.comercio.sastreria.repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class ClienteServiceImp implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository; // Inyectando un OBJETO
	
	@Override
	public Cliente registrar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public Optional<Cliente> get(String idCliente) {
		return clienteRepository.findById(idCliente);
	}

	@Override
	public void update(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	public void delete(String idCliente) {
		clienteRepository.deleteById(idCliente);
	}
	
	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager
	
	@Override
	public String obtenerNuevoIdCliente() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_cliente");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	public Optional<Cliente> findByDocumento(String numeroDocCliente) {
		return clienteRepository.findByNumeroDocCliente(numeroDocCliente);
	}

	@Override
	public List<Cliente> findByFechaDesc() {
		return clienteRepository.findAllOrderedByFecha();
	}
}