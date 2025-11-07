package com.example.tvmaze.mappers;

import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.personagem.PersonagemApiDTO;
import com.example.tvmaze.dtos.personagem.PersonagemRequestDTO;
import com.example.tvmaze.dtos.personagem.PersonagemRespostaDTO;
import com.example.tvmaze.models.Personagem;

@Component
public class PersonagemMapper {
    
    public PersonagemRespostaDTO toRespostaDTO(Personagem personagem) {
        return new PersonagemRespostaDTO(
                personagem.getPersonagemId(),
                personagem.getExternoId(),
                personagem.getNomePersonagem(),
                personagem.getImagem()
        );
    }
    
    public Personagem toEntity(PersonagemRequestDTO dto) {
        return new Personagem(
                null,
                null,
                dto.getNomePersonagem(),
                dto.getImagem()
        );
    }
    
    public Personagem toEntity(PersonagemApiDTO dto) {
        return new Personagem(
                null,
                dto.getExternoId(),
                dto.getNomePersonagem(),
                dto.getImagem()
        );
    }
}

