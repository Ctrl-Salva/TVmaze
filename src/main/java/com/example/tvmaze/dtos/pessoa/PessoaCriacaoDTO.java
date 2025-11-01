package com.example.tvmaze.dtos.pessoa;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.example.tvmaze.entities.Participacao;

import jakarta.persistence.OneToMany;

public class PessoaCriacaoDTO {

    private Integer externoId;

    private String nome;

    private LocalDate dataNascimento;
    

    public PessoaCriacaoDTO() {
    }

    public Integer getExternoId() {
        return externoId;
    }

    public void setExternoId(Integer externoId) {
        this.externoId = externoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

  
}
