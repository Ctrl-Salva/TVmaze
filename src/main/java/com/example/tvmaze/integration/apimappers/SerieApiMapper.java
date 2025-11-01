package com.example.tvmaze.integration.apimappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.serie.SerieApiDTO;
import com.example.tvmaze.entities.Genero;
import com.example.tvmaze.entities.Serie;
import com.example.tvmaze.repositories.GeneroRepository;

@Component
public class SerieApiMapper {
    
    @Autowired
    private GeneroRepository generoRepository;

    public Serie toEntity(SerieApiDTO dto) {
        Serie serie = new Serie();
        serie.setExternoId(dto.getExternoId());
        serie.setNome(dto.getNome());
        serie.setLinguagem(dto.getLinguagem());
        serie.setNota(dto.getRating() != null ? dto.getRating().getAverage() : null);

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
}
