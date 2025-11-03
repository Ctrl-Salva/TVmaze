package com.example.tvmaze.mappers;


import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.episodio.EpisodioApiDTO;
import com.example.tvmaze.dtos.genero.GeneroCriacaoDTO;
import com.example.tvmaze.dtos.genero.GeneroRespostaDTO;
import com.example.tvmaze.models.Episodio;
import com.example.tvmaze.models.Genero;
import com.example.tvmaze.models.Serie;


@Component
public class GeneroMapper {

    public GeneroRespostaDTO toRespostaDTO(Genero genero) {
        if (genero == null) {
            return null;
        }

        GeneroRespostaDTO dto = new GeneroRespostaDTO();
        dto.setGeneroId(genero.getGeneroId());
        dto.setNome(genero.getNome());

        return dto;
    }

    public void toEntity(GeneroCriacaoDTO dto, Genero genero) {
        if (dto == null || genero == null) {
            return;
        }

        genero.setNome(dto.getNome());
    }

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
