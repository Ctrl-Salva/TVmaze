package com.example.tvmaze.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dto.GeneroDTO;
import com.example.tvmaze.models.Genero;
import com.example.tvmaze.repositories.GeneroRepository;

@Service
public class GeneroService {
    
    @Autowired
    GeneroRepository generoRepository;

    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }
    
    public Genero buscarPorId(Integer id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado"));
    }

    public Genero salvar(GeneroDTO dto) {
        generoRepository.findByNome(dto.getNome())
            .ifPresent(g -> {
                throw new RuntimeException("Já existe um gênero com o nome: " + dto.getNome());
        });

        Genero genero = new Genero();
        genero.setNome(dto.getNome());

        return generoRepository.save(genero);
    }



    public void deletar(Integer id) {
        generoRepository.deleteById(id);
    }

}
