package com.example.tvmaze.entities.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class Personagem {
    
    private Integer personagemId;

    private Integer externoId;

    private String nomePersonagem;

    public Personagem() {
    }

    public Integer getPersonagemId() {
        return personagemId;
    }

    public void setPersonagemId(Integer personagemId) {
        this.personagemId = personagemId;
    }

    public Integer getExternoId() {
        return externoId;
    }

    public void setExternoId(Integer externoId) {
        this.externoId = externoId;
    }

    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    
}
