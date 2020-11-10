package com.matheus.catalogo.repository;
import com.matheus.catalogo.model.Musica;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogoRepository extends JpaRepository<Musica, Long> {
    
}
