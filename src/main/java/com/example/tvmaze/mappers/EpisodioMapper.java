package com.example.tvmaze.mappers;

import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.episodio.EpisodioCriacaoDTO;
import com.example.tvmaze.dtos.episodio.EpisodioRespostaDTO;
import com.example.tvmaze.entities.Episodio;

@Component
public class EpisodioMapper {

    /**
     * Converte uma entidade Episodio para EpisodioRespostaDTO (completo)
     */
    public EpisodioRespostaDTO toRespostaDTO(Episodio episodio) {
        if (episodio == null) {
            return null;
        }

        EpisodioRespostaDTO dto = new EpisodioRespostaDTO();
        dto.setEpisodioId(episodio.getEpisodioId());
        dto.setExternoId(episodio.getExternoId());
        dto.setNome(episodio.getNome());
        dto.setTemporada(episodio.getTemporada());
        dto.setNumero(episodio.getNumero());
        dto.setDataExibicao(episodio.getDataExibicao());
        dto.setDuracao(episodio.getDuracao());

        if (episodio.getSerie() != null) {
            dto.setSerieId(episodio.getSerie().getSerieId());
            dto.setSerieNome(episodio.getSerie().getNome());
        }

        return dto;
    }

    /**
     * Atualiza uma entidade Episodio com dados do EpisodioAtualizacaoDTO
     */
    public void updateEntity(EpisodioCriacaoDTO dto, Episodio episodio) {

        episodio.setNome(dto.getNome());

        episodio.setTemporada(dto.getTemporada());

        episodio.setNumero(dto.getNumero());

        episodio.setDataExibicao(dto.getDataExibicao());

        episodio.setDuracao(dto.getDuracao());

    }
}
