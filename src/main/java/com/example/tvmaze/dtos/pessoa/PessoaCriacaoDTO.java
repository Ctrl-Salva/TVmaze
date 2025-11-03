package com.example.tvmaze.dtos.pessoa;

import java.time.LocalDate;

import com.example.tvmaze.entities.vo.Imagem;
import com.google.gson.annotations.SerializedName;

public class PessoaCriacaoDTO {

    private Integer externoId;

    private String nome;

    private LocalDate dataNascimento;
    
    private Imagem imagem;

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

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

  
}
