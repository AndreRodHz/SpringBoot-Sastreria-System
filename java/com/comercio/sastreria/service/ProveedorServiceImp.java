package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Proveedor;
import com.comercio.sastreria.repository.ProveedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class ProveedorServiceImp implements ProveedorService{

	@Autowired
	private ProveedorRepository proveedorRepository; // Inyectando un OBJETO
	
	@Override
	public Proveedor registrar(Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
	}

	@Override
	public Optional<Proveedor> get(String idProveedor) {
		return proveedorRepository.findById(idProveedor);
	}

	@Override
	public void update(Proveedor proveedor) {
		proveedorRepository.save(proveedor);
	}

	@Override
	public void delete(String idProveedor) {
		proveedorRepository.deleteById(idProveedor);
	}

	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager
	
	@Override
	public String obtenerNuevoIdProveedor() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_proveedor");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<Proveedor> findAll() {
		return proveedorRepository.findAll();
	}

	@Override
	public List<Proveedor> findByFechaDesc() {
		return proveedorRepository.findAllOrderedByFecha();
	}
}