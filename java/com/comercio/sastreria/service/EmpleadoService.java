package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Empleado;

public interface EmpleadoService {
	
	public Empleado registrar(Empleado empleado);
	public Optional<Empleado> get(String idEmpleado);
	public void update(Empleado empleado);
	public void delete(String idEmpleado);
	String obtenerNuevoIdEmpleado(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	public List<Empleado> findAll();
	
	public Optional<Empleado> findByEmailEmpleado(String email);
	public List<Empleado> findByFechaDesc();
}