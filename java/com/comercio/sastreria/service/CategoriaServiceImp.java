package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Categoria;
import com.comercio.sastreria.repository.CategoriaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class CategoriaServiceImp implements CategoriaService{

	@Autowired
	private CategoriaRepository categoriaRepository; // Inyectando un OBJETO
	
	@Override
	public Categoria registrar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	public Optional<Categoria> get(String idCategoria) {
		return categoriaRepository.findById(idCategoria);
	}

	@Override
	public void update(Categoria categoria) {
		categoriaRepository.save(categoria);
	}

	@Override
	public void delete(String idCategoria) {
		categoriaRepository.deleteById(idCategoria);
	}

	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager
	
	@Override
	public String obtenerNuevoIdCategoria() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_categoria");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
	}
	
	@Override
	public List<Categoria> getAllCategorias() {
		return categoriaRepository.findAll();
	}
}