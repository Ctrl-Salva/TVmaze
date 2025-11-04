package com.example.tvmaze.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.episodio.EpisodioCriacaoDTO;
import com.example.tvmaze.dtos.episodio.EpisodioRespostaDTO;
import com.example.tvmaze.mappers.EpisodioMapper;
import com.example.tvmaze.models.Episodio;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.EpisodioRepository;
import com.example.tvmaze.repositories.SerieRepository;

@Service
public class EpisodioService {

    @Autowired
    EpisodioRepository episodioRepository;

    @Autowired
    EpisodioMapper episodioMapper;

    @Autowired
    SerieRepository serieRepository;

    public List<EpisodioRespostaDTO> listarEpisodiosPorSerie(Integer serieId) {
        verificarSerieExiste(serieId);

        return episodioRepository.findBySerieSerieId(serieId).stream()
                .map(episodioMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista episódios de uma temporada específica
     */

    public List<EpisodioRespostaDTO> listarEpisodiosPorTemporada(Integer serieId, Integer temporada) {
        verificarSerieExiste(serieId);

        return episodioRepository.findBySerieSerieIdAndTemporada(serieId, temporada).stream()
                .map(episodioMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as temporadas de uma série
     */

    public List<Integer> listarTemporadas(Integer serieId) {
        verificarSerieExiste(serieId);
        return episodioRepository.findTemporadasBySerieId(serieId);
    }

    /**
     * Busca um episódio por ID
     */

    public EpisodioRespostaDTO buscarPorId(Integer episodioId) {
        Episodio episodio = episodioRepository.findById(episodioId)
                .orElseThrow(() -> new RuntimeException("Episódio não encontrado com ID: " + episodioId));
        return episodioMapper.toRespostaDTO(episodio);
    }

    public EpisodioRespostaDTO criarEpisodio(Integer serieId, EpisodioCriacaoDTO episodioCriacaoDTO) {
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + serieId));

        Episodio novoEpisodio = new Episodio();

        episodioMapper.updateEntity(episodioCriacaoDTO, novoEpisodio);

        novoEpisodio.setSerie(serie);

        Episodio episodioSalvo = episodioRepository.save(novoEpisodio);

        return episodioMapper.toRespostaDTO(episodioSalvo);

    }


    /**
     * Atualiza informações de um episódio
     */
    public EpisodioRespostaDTO atualizarEpisodio(Integer episodioId, EpisodioCriacaoDTO dto) {
        Episodio episodio = episodioRepository.findById(episodioId)
                .orElseThrow(() -> new RuntimeException("Episódio não encontrado com ID: " + episodioId));

        episodioMapper.updateEntity(dto, episodio);
        Episodio episodioAtualizado = episodioRepository.save(episodio);
        return episodioMapper.toRespostaDTO(episodioAtualizado);
    }

    /**
     * Deleta um episódio
     */
    public void deletarEpisodio(Integer episodioId) {
        if (!episodioRepository.existsById(episodioId)) {
            throw new RuntimeException("Episódio não encontrado com ID: " + episodioId);
        }
        episodioRepository.deleteById(episodioId);
    }

    /**
     * Conta total de episódios de uma série
     */

    public long contarEpisodiosPorSerie(Integer serieId) {
        verificarSerieExiste(serieId);
        return episodioRepository.countBySerieSerieId(serieId);
    }

    /**
     * Verifica se uma série existe
     */
    private void verificarSerieExiste(Integer serieId) {
        if (!serieRepository.existsById(serieId)) {
            throw new RuntimeException("Série não encontrada com ID: " + serieId);
        }
    }
}
