package com.matheus.catalogo.service;
import java.util.List;
import com.matheus.catalogo.model.Musica;
public interface CatalogoService {

    List<Musica> findAll();
    Musica findById(long id);
    Musica save(Musica musica);
    // Musica update(Musica musica, long id);
    void excluir(long id);
}