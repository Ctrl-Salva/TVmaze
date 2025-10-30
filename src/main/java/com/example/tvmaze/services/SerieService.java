package com.example.tvmaze.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dto.SerieRespostaDTO;
import com.example.tvmaze.dto.SerieCriacaoDTO;
import com.example.tvmaze.models.Genero;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.GeneroRepository;
import com.example.tvmaze.repositories.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    GeneroRepository generoRepository;

    public List<SerieRespostaDTO> listarSeries() {
        return serieRepository.findAll().stream()
        .map(serie -> {
            SerieRespostaDTO dto = new SerieRespostaDTO();
            dto.setSerieId(serie.getSerieId());
            dto.setExternoId(serie.getExternoId());
            dto.setNome(serie.getNome());
            dto.setLinguagem(serie.getLinguagem());
            dto.setSinopse(serie.getSinopse());
            dto.setNota(serie.getNota());
            dto.setDataEstreia(serie.getDataEstreia());
            dto.setDataTermino(serie.getDataTermino());
            dto.setGenres(
                serie.getGeneros().stream()
                    .map(Genero::getNome)
                    .collect(Collectors.toSet())
            );
            return dto;
        })
        .collect(Collectors.toList());
    }

    public Serie buscarPorId(Integer id) {
        return serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));
    }

    public Serie salvarSerie(SerieCriacaoDTO dto) {
        Serie serie = new Serie();

        serie.setExternoId(dto.getExternoId());
        serie.setNome(dto.getNome());
        serie.setLinguagem(dto.getLinguagem());
        serie.setSinopse(dto.getSinopse());
        serie.setNota(dto.getNota());
        serie.setDataEstreia(dto.getDataEstreia());
        serie.setDataTermino(dto.getDataTermino());

        Set<Genero> generos = new HashSet<>();
        for (String nomeGenero : dto.getGeneros()) {
            Genero genero = generoRepository.findByNome(nomeGenero)
                    .orElseThrow(() -> new RuntimeException("Gênero não encontrado: " + nomeGenero));
            generos.add(genero);
        }

        serie.adicionarGeneros(generos);

        return serieRepository.save(serie);
    }

    public Serie atualizarSerie(Integer id, SerieCriacaoDTO dto) {
        Serie serie = buscarPorId(id);

        serie.setNome(dto.getNome());
        serie.setLinguagem(dto.getLinguagem());
        serie.setSinopse(dto.getSinopse());
        serie.setNota(dto.getNota());
        serie.setDataEstreia(dto.getDataEstreia());
        serie.setDataTermino(dto.getDataTermino());

        return serieRepository.save(serie);
    }

    public void deletarSerie(Integer id) {
        serieRepository.deleteById(id);
    }
}
