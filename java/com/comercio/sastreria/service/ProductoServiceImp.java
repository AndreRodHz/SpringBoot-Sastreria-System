package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Producto;
import com.comercio.sastreria.repository.ProductoRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class ProductoServiceImp implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository; // Inyectando un OBJETO
	
	@Override
	public Producto registrar(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(String idProducto) {
		return productoRepository.findById(idProducto);
	}

	@Override
	public void update(Producto producto) {
		productoRepository.save(producto);
	}

	@Override
	public void delete(String idProducto) {
		productoRepository.deleteById(idProducto);
	}

	@PersistenceContext
    private EntityManager entityManager; // Inyectar el EntityManager
	
    @Override
    public String obtenerNuevoIdProducto() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("obtener_nuevo_id_producto");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue(1);
    }

	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

	@Override
	public List<Producto> findByFechaDesc() {
		return productoRepository.findAllOrderedByFecha();
	}

	@Override
	public void actualizarStock(String idProducto, Integer nuevoStock) {
		Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto != null) {
            producto.setStockProducto(nuevoStock);
            productoRepository.save(producto);
        } else {
        	
        }
		
	}

}