package com.comercio.sastreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comercio.sastreria.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, String>{

}