package com.comercio.sastreria.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.repository.TransaccionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class TransaccionServiceImp implements TransaccionService{
	
	@Autowired
	TransaccionRepository transaccionRepository;

	@Override
	public Transaccion registrar(Transaccion transaccion) {
		return transaccionRepository.save(transaccion);
	}

	@Override
	public Optional<Transaccion> get(String idTransaccion) {
		return transaccionRepository.findById(idTransaccion);
	}

	@Override
	public void delete(String idTransaccion) {
		transaccionRepository.deleteById(idTransaccion);
	}
	
	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager

	@Override
	public String obtenerNuevoIdTransaccion() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_transac");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}
}
