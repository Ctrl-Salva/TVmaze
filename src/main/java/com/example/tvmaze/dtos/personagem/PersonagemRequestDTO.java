package com.example.tvmaze.dtos.personagem;

import com.example.tvmaze.models.vo.Imagem;

public class PersonagemRequestDTO {
    
    private String nomePersonagem;
    
    private Imagem imagem;

    public PersonagemRequestDTO() {}

    public PersonagemRequestDTO( String nomePersonagem, Imagem imagem) {
        this.nomePersonagem = nomePersonagem;
        this.imagem = imagem;
    }

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

