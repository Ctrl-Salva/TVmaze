package com.example.tvmaze.dtos.personagem;

import com.example.tvmaze.entities.vo.Imagem;

public class PersonagemCriacaoDTO {
    private String nomePersonagem;
    private Imagem imagem;

    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }
}

