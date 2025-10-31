package com.example.tvmaze.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.serie.SerieCriacaoDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.entities.Serie;
import com.example.tvmaze.mappers.SerieMapper;
import com.example.tvmaze.repositories.SerieRepository;
import com.example.tvmaze.utils.Quicksort;

@Service
public class SerieService {

    @Autowired
    SerieRepository serieRepository;

    @Autowired
    SerieMapper serieMapper;

    public List<SerieRespostaDTO> listarSeries() {
        return serieRepository.findAll().stream()
                .map(serieMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public List<SerieRespostaDTO> listarSeriesOrdenadas() {
        List<Serie> series = serieRepository.findAll();
        Quicksort.quickSort(series, 0, series.size() - 1);
        
        return series.stream()
                .map(serieMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public SerieRespostaDTO buscarPorId(Integer id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));
                
        return serieMapper.toRespostaDTO(serie);
    }

    public Serie salvarSerie(SerieCriacaoDTO dto) {
        Serie novaSerie = new Serie();
        serieMapper.toEntity(dto, novaSerie);

        return serieRepository.save(novaSerie);
    }

    public Serie atualizarSerie(Integer id, SerieCriacaoDTO dto) {
        Serie existente = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));
        serieMapper.toEntity(dto, existente);

        return serieRepository.save(existente);
    }

    public void deletarSerie(Integer id) {
        if (!serieRepository.existsById(id)) {
            throw new RuntimeException("Série não encontrada com ID: " + id);
        }

        serieRepository.deleteById(id);
    }
}
