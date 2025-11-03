package com.example.tvmaze.models;

import com.example.tvmaze.models.vo.Imagem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Personagem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personagemId;

    private Integer externoId;

    private String nomePersonagem;

    private Imagem imagem;

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

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    
}
