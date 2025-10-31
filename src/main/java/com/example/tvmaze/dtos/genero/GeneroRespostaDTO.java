package com.example.tvmaze.dtos.genero;

public class GeneroRespostaDTO {

    private Integer generoId;
    private String nome;
    
    public GeneroRespostaDTO() {}

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
