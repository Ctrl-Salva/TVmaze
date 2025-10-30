package com.example.tvmaze.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dto.criacao.SerieCriacaoDTO;
import com.example.tvmaze.dto.respota.SerieRespostaDTO;
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
                .map(SerieRespostaDTO::new)
                .collect(Collectors.toList());
    }

    public SerieRespostaDTO buscarPorId(Integer id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));
        return new SerieRespostaDTO(serie);
    }

    public Serie salvarSerie(SerieCriacaoDTO dto) {
        Serie serie = dto.toEntity(generoRepository);
        return serieRepository.save(serie);
    }

    public Serie atualizarSerie(Integer id, SerieCriacaoDTO dto) {
        SerieRespostaDTO existente = buscarPorId(id);
        Serie novaSerie = dto.toEntity(generoRepository);

        novaSerie.setSerieId(existente.getSerieId());
        return serieRepository.save(novaSerie);
    }

    public void deletarSerie(Integer id) {
        serieRepository.deleteById(id);
    }
}
