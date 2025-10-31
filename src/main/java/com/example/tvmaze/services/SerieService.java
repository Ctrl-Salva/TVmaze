package com.example.tvmaze.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dto.criacao.SerieCriacaoDTO;
import com.example.tvmaze.dto.resposta.SerieRespostaDTO;
import com.example.tvmaze.models.Genero;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.GeneroRepository;
import com.example.tvmaze.repositories.SerieRepository;
import com.example.tvmaze.utils.Quicksort;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    GeneroRepository generoRepository;


    public List<SerieRespostaDTO> listarSeries() {
        List<Serie> series = serieRepository.findAll();

        return series.stream()
                .map(this::converterRepostaDTO)
                .collect(Collectors.toList());
    }

    public List<SerieRespostaDTO> listarSeriesOrdenadas() {
        List<Serie> series = serieRepository.findAll();

        // aplica ordenação personalizada
        Quicksort.quickSort(series, 0, series.size() - 1);

        // converte para DTO antes de retornar
        return series.stream()
                .map(this::converterRepostaDTO)
                .collect(Collectors.toList());
    }

    public SerieRespostaDTO buscarPorId(Integer id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));

        return converterRepostaDTO(serie);
    }

    public Serie salvarSerie(SerieCriacaoDTO dto) {
        Serie novaSerie = new Serie();

        converterEntidadeDTO(dto, novaSerie);

        return serieRepository.save(novaSerie);
    }

    public Serie atualizarSerie(Integer id, SerieCriacaoDTO dto) {
        Serie existente = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));

        converterEntidadeDTO(dto, existente);

        return serieRepository.save(existente);
    }

    public void deletarSerie(Integer id) {
        if (!serieRepository.existsById(id)) {
            throw new RuntimeException("Série não encontrada com ID: " + id);
        }
        serieRepository.deleteById(id);
    }

    /*
     * Metodoos de Conversão pra DTO
     */

    public SerieRespostaDTO converterRepostaDTO(Serie serie) {
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

    private void converterEntidadeDTO(SerieCriacaoDTO dto, Serie serie) {
        serie.setExternoId(dto.getExternoId());
        serie.setNome(dto.getNome());
        serie.setLinguagem(dto.getLinguagem());
        serie.setSinopse(dto.getSinopse());
        serie.setNota(dto.getNota());
        serie.setDataEstreia(dto.getDataEstreia());
        serie.setDataTermino(dto.getDataTermino());

        Set<Genero> generosConvertidos = dto.getGeneros().stream()
                .map(nomeGenero -> generoRepository.findByNome(nomeGenero)
                        .orElseGet(() -> {
                            Genero novoGenero = new Genero();
                            novoGenero.setNome(nomeGenero);
                            return generoRepository.save(novoGenero);
                        }))
                .collect(Collectors.toSet());

        serie.setGeneros(generosConvertidos);
    }
}
