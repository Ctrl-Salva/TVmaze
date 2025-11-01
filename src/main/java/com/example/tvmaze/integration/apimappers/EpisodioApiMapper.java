package com.example.tvmaze.integration.apimappers;

import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.episodio.EpisodioApiDTO;
import com.example.tvmaze.entities.Episodio;
import com.example.tvmaze.entities.Serie;

@Component
public class EpisodioApiMapper {
    

    /**
     * Converte EpisodioApiDTO para entidade Episodio
     */
    public Episodio toEntity(EpisodioApiDTO dto, Serie serie) {
        if (dto == null) {
            return null;
        }

        Episodio episodio = new Episodio();
        episodio.setExternoId(dto.getExternoId());
        episodio.setNome(dto.getNome());
        episodio.setTemporada(dto.getTemporada());
        episodio.setNumero(dto.getNumero());
        episodio.setDataExibicao(dto.getDataExibicao());
        episodio.setDuracao(dto.getDuracao());
        episodio.setSerie(serie);

        return episodio;
    }

    /**
     * Atualiza entidade existente com dados da API
     */
    public void updateEntity(EpisodioApiDTO dto, Episodio episodio) {
        if (dto == null || episodio == null) {
            return;
        }

        episodio.setNome(dto.getNome());
        episodio.setTemporada(dto.getTemporada());
        episodio.setNumero(dto.getNumero());
        episodio.setDataExibicao(dto.getDataExibicao());
        episodio.setDuracao(dto.getDuracao());
    }

    /**
     * Remove tags HTML de texto
     */
    public String limparHtml(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        return texto.replaceAll("<[^>]*>", "").trim();
    }
}
