package com.example.tvmaze.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.serie.SerieApiDTO;
import com.example.tvmaze.dtos.serie.SerieCriacaoDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.models.Genero;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.GeneroRepository;


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
        dto.setImagem(serie.getImagem());
        
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
        serie.setNome(dto.getNome());
        serie.setLinguagem(dto.getLinguagem());
        serie.setSinopse(dto.getSinopse());
        serie.setNota(dto.getNota());
        serie.setDataEstreia(dto.getDataEstreia());
        serie.setDataTermino(dto.getDataTermino());
        serie.setImagem(dto.getImagem());
        
        Set<Genero> generosConvertidos = dto.getGeneros().stream()
                .map(this::buscarOuCriarGenero)
                .collect(Collectors.toSet());
        serie.setGeneros(generosConvertidos);
    }

    public Serie toEntity(SerieApiDTO dto) {
        Serie serie = new Serie();
        serie.setExternoId(dto.getExternoId());
        serie.setNome(dto.getNome());
        serie.setLinguagem(dto.getLinguagem());
        serie.setNota(dto.getRating() != null ? dto.getRating().getAverage() : null);
        serie.setImagem(dto.getImagem());

        // Remove tags HTML da sinopse
        if (dto.getSinopse() != null) {
            serie.setSinopse(dto.getSinopse().replaceAll("<[^>]*>", ""));
        }

        // Datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            if (dto.getDataEstreia() != null && !dto.getDataEstreia().isEmpty()) {
                serie.setDataEstreia(LocalDate.parse(dto.getDataEstreia(), formatter));
            }
            if (dto.getDataTermino() != null && !dto.getDataTermino().isEmpty()) {
                serie.setDataTermino(LocalDate.parse(dto.getDataTermino(), formatter));
            }
        } catch (Exception ignored) {}

        // Gêneros
        Set<Genero> generos = new HashSet<>();
        if (dto.getGeneros() != null) {
            for (String nomeGenero : dto.getGeneros()) {
                Genero genero = generoRepository.findByNome(nomeGenero)
                        .orElseGet(() -> {
                            Genero novo = new Genero();
                            novo.setNome(nomeGenero);
                            return generoRepository.save(novo);
                        });
                generos.add(genero);
            }
        }
        serie.setGeneros(generos);

        return serie;
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
