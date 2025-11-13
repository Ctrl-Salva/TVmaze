package com.example.tvmaze.dtos.pessoa;

import java.time.LocalDate;

import com.example.tvmaze.models.vo.Imagem;


public class PessoaCriacaoDTO {


    private String nome;

    private LocalDate dataNascimento;
    
    private Imagem imagem;

    public PessoaCriacaoDTO() {
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
