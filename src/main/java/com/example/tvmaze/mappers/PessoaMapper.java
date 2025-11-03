package com.example.tvmaze.mappers;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.example.tvmaze.dtos.pessoa.PessoaCriacaoDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.entities.Pessoa;

@Component
public class PessoaMapper {

    // API → Entidade
    public Pessoa toEntity(PessoaApiDTO dto) {
        if (dto == null) return null;

        Pessoa pessoa = new Pessoa();
        pessoa.setExternoId(dto.getExternoId());
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setImagem(dto.getImagem());
        return pessoa;
    }

    // DTO de criação → Entidade
    public Pessoa toEntity(PessoaCriacaoDTO dto) {
        if (dto == null) return null;

        Pessoa pessoa = new Pessoa();
        pessoa.setExternoId(dto.getExternoId());
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setImagem(dto.getImagem());
        return pessoa;
    }

    // Atualiza entidade existente com dados do DTO de criação
    public void toEntity(PessoaCriacaoDTO dto, Pessoa pessoa) {
        if (dto == null || pessoa == null) return;

        if (dto.getNome() != null)
            pessoa.setNome(dto.getNome());

        if (dto.getDataNascimento() != null)
            pessoa.setDataNascimento(dto.getDataNascimento());

        if (dto.getImagem() != null)
            pessoa.setImagem(dto.getImagem());

        if (dto.getExternoId() != null)
            pessoa.setExternoId(dto.getExternoId());
    }

    // Entidade → Resposta DTO
    public PessoaRespostaDTO toRespostaDTO(Pessoa pessoa) {
        if (pessoa == null) return null;

        PessoaRespostaDTO dto = new PessoaRespostaDTO();
        dto.setPessoaId(pessoa.getPessoaId());
        dto.setExternoId(pessoa.getExternoId());
        dto.setNome(pessoa.getNome());
        dto.setDataNascimento(pessoa.getDataNascimento());
        dto.setImagem(pessoa.getImagem());
        return dto;
    }
}
