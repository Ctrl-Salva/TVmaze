package com.example.tvmaze.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.example.tvmaze.dtos.pessoa.PessoaCriacaoDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.entities.Pessoa;
import com.example.tvmaze.repositories.PessoaRepository;

@Component
public class PessoaMapper {
    
    @Autowired
    PessoaRepository pessoaRepository;

    public PessoaRespostaDTO toRespostaDTO(Pessoa pessoa){
        PessoaRespostaDTO dto = new PessoaRespostaDTO();

        dto.setPessoaId(pessoa.getPessoaId());
        dto.setExternoId(pessoa.getExternoId());
        dto.setNome(pessoa.getNome());
        dto.setDataNascimento(pessoa.getDataNascimento());

        return dto;
    }

    public void toEntity(PessoaCriacaoDTO dto, Pessoa pessoa){
        dto.setExternoId(pessoa.getExternoId());
        dto.setNome(pessoa.getNome());
        dto.setDataNascimento(pessoa.getDataNascimento());

    }

    public Pessoa toEntity(PessoaApiDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setExternoId(dto.getExternoId());
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());
        return pessoa;
    }

}
