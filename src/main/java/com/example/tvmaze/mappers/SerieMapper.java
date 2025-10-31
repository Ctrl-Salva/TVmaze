package com.example.tvmaze.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.serie.SerieCriacaoDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.entities.Genero;
import com.example.tvmaze.entities.Serie;
import com.example.tvmaze.repositories.GeneroRepository;
import com.example.tvmaze.services.GeneroService;

@Component
public class SerieMapper {

    @Autowired
    GeneroRepository generoRepository;


    public SerieRespostaDTO toRespostaDTO(Serie serie) {
        SerieRespostaDTO dto = new SerieRespostaDTO();
        dto.setSerieId(serie.getSerieId());
        dto.setExternoId(serie.getExternoId());
        dto.setNome(serie.getNome());
        dto.setLinguagem(serie.getLinguagem());
        dto.setSinopse(serie.getSinopse());
        dto.setNota(serie.getNota());
        dto.setDataEstreia(serie.getDataEstreia());
        dto.setDataTermino(serie.getDataTermino());
        
        Set<String> nomesGeneros = serie.getGeneros().stream()
                .map(Genero::getNome)
                .collect(Collectors.toSet());
        dto.setGeneros(nomesGeneros);
        
        return dto;
    }

    /**
     * Converte um SerieCriacaoDTO para entidade Serie
     * Atualiza a série existente com os dados do DTO
     */
    public void toEntity(SerieCriacaoDTO dto, Serie serie) {
        serie.setExternoId(dto.getExternoId());
        serie.setNome(dto.getNome());
        serie.setLinguagem(dto.getLinguagem());
        serie.setSinopse(dto.getSinopse());
        serie.setNota(dto.getNota());
        serie.setDataEstreia(dto.getDataEstreia());
        serie.setDataTermino(dto.getDataTermino());
        
        Set<Genero> generosConvertidos = dto.getGeneros().stream()
                .map(this::buscarOuCriarGenero)
                .collect(Collectors.toSet());
        serie.setGeneros(generosConvertidos);
    }

    /**
     * Busca um gênero existente ou cria um novo
     */
    private Genero buscarOuCriarGenero(String nomeGenero) {
        return generoRepository.findByNome(nomeGenero)
                .orElseGet(() -> {
                    Genero novoGenero = new Genero();
                    novoGenero.setNome(nomeGenero);
                    return generoRepository.save(novoGenero);
                });
    }

}
