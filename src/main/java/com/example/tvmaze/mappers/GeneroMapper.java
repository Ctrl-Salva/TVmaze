package com.example.tvmaze.mappers;


import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.genero.GeneroCriacaoDTO;
import com.example.tvmaze.dtos.genero.GeneroRespostaDTO;
import com.example.tvmaze.entities.Genero;


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


}
