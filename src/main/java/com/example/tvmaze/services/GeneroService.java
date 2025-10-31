package com.example.tvmaze.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Genero atualizar(Integer id, GeneroDTO dto) {
        Genero exitente = buscarPorId(id);

        exitente.setNome(dto.getNome());

        return generoRepository.save(exitente);
    }

    public void deletar(Integer id) {
        generoRepository.deleteById(id);
    }

    public Set<Genero> buscarOuCriarGeneros(Set<String> nomes) {
        if (nomes == null || nomes.isEmpty()) {
            return Collections.emptySet();
        }

        return nomes.stream()
                .map(nome -> generoRepository.findByNome(nome)
                        .orElseGet(() -> {
                            Genero novo = new Genero();
                            novo.setNome(nome);
                            return generoRepository.save(novo);
                        }))
                .collect(Collectors.toSet());
    }
}
