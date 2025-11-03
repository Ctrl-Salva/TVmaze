package com.example.tvmaze.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.genero.GeneroCriacaoDTO;
import com.example.tvmaze.dtos.genero.GeneroRespostaDTO;
import com.example.tvmaze.mappers.GeneroMapper;
import com.example.tvmaze.models.Genero;
import com.example.tvmaze.repositories.GeneroRepository;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    GeneroMapper generoMapper;

    public List<GeneroRespostaDTO> listarGeneros() {
        return generoRepository.findAll().stream()
                .map(generoMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public GeneroRespostaDTO buscarPorId(Integer id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com ID: " + id));

        return generoMapper.toRespostaDTO(genero);
    }

    public GeneroRespostaDTO buscarPorNome(String nome) {
        Genero genero = generoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com nome: " + nome));

        return generoMapper.toRespostaDTO(genero);
    }

    public GeneroRespostaDTO salvarGenero(GeneroCriacaoDTO dto) {

        if (generoRepository.findByNome(dto.getNome()).isPresent()) {
            throw new RuntimeException("Já existe um gênero com o nome: " + dto.getNome());
        }

        Genero novoGenero = new Genero();
        generoMapper.toEntity(dto, novoGenero);
        Genero generoSalvo = generoRepository.save(novoGenero);

        return generoMapper.toRespostaDTO(generoSalvo);
    }

    public GeneroRespostaDTO atualizarGenero(Integer id, GeneroCriacaoDTO dto) {
        Genero existente = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com ID: " + id));

        generoMapper.toEntity(dto, existente);
        Genero generoAtualizado = generoRepository.save(existente);

        return generoMapper.toRespostaDTO(generoAtualizado);
    }

    public void deletarGenero(Integer id) {
        if (!generoRepository.existsById(id)) {
            throw new RuntimeException("Gênero não encontrado com ID: " + id);
        }

        generoRepository.deleteById(id);
    }

    public Genero buscarOuCriarGenero(String nomeGenero) {
        return generoRepository.findByNome(nomeGenero)
                .orElseGet(() -> {
                    Genero novoGenero = new Genero();
                    novoGenero.setNome(nomeGenero);
                    return generoRepository.save(novoGenero);
                });
    }

    public Set<Genero> buscarOuCriarGeneros(Set<String> nomesGeneros) {
        if (nomesGeneros == null || nomesGeneros.isEmpty()) {
            return Collections.emptySet();
        }

        return nomesGeneros.stream()
                .map(this::buscarOuCriarGenero)
                .collect(Collectors.toSet());
    }
}
