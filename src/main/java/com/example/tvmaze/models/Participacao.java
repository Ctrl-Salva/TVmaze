package com.example.tvmaze.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Participacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participacaoId;
    
    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;
    
    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;
    
    @ManyToOne
    @JoinColumn(name = "personagem_id", nullable = false)
    private Personagem personagem;

    public Participacao() {}

    public Participacao(Integer participacaoId, Pessoa pessoa, Serie serie, Personagem personagem) {
        this.participacaoId = participacaoId;
        this.pessoa = pessoa;
        this.serie = serie;
        this.personagem = personagem;
    }

    public Integer getParticipacaoId() {
        return participacaoId;
    }

    public void setParticipacaoId(Integer participacaoId) {
        this.participacaoId = participacaoId;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }
}
