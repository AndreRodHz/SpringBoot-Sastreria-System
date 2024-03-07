package com.comercio.sastreria.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.comercio.sastreria.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, String>{
	Optional<Empleado> findByEmailEmpleado(String emailEmpleado);
	
	@Query("SELECT e FROM Empleado e ORDER BY e.fecha DESC")
    List<Empleado> findAllOrderedByFecha();
}