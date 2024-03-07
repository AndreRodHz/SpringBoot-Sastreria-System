package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Empleado;
import com.comercio.sastreria.repository.EmpleadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class EmpleadoServiceImp implements EmpleadoService{

	@Autowired
	private EmpleadoRepository empleadoRepository; // Inyectando un OBJETO

	
	@Override
	public Empleado registrar(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	@Override
	public Optional<Empleado> get(String idEmpleado) {
		return empleadoRepository.findById(idEmpleado);
	}

	@Override
	public void update(Empleado empleado) {
		empleadoRepository.save(empleado);
	}

	@Override
	public void delete(String idEmpleado) {
        empleadoRepository.deleteById(idEmpleado);
	}
	
	@PersistenceContext
	private EntityManager entityManager; // Inyectar el EntityManager
	
	@Override
	public String obtenerNuevoIdEmpleado() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_empleado");
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
		query.execute();
		return (String) query.getOutputParameterValue(1);
	}

	@Override
	public List<Empleado> findAll() {
		return empleadoRepository.findAll();
	}

	@Override
	public Optional<Empleado> findByEmailEmpleado(String email) {
		return empleadoRepository.findByEmailEmpleado(email);
	}

	@Override
	public List<Empleado> findByFechaDesc() {
		return empleadoRepository.findAllOrderedByFecha();
	}
}