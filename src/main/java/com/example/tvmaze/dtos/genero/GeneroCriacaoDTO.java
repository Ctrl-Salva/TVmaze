package com.example.tvmaze.dtos.genero;

public class GeneroCriacaoDTO {
    
    private String nome;

    public GeneroCriacaoDTO() {}

    public GeneroCriacaoDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

