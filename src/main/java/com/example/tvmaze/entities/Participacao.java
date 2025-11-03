package com.example.tvmaze.entities;

import jakarta.persistence.CascadeType;
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
  @JoinColumn(name = "pessoa_id")
  private Pessoa pessoa;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "personagem_id", nullable = false)
  private Personagem personagem;

  @ManyToOne
  @JoinColumn(name = "serie_id")
  private Serie serie;

  public Participacao() {
  }

  public Integer getParticipacaoId() {
    return participacaoId;
  }

  public void setParticipacaoId(Integer participacaoId) {
    this.participacaoId = participacaoId;
  }

  public Personagem getPersonagem() {
    return personagem;
  }

  public void setPersonagem(Personagem personagem) {
    this.personagem = personagem;
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

}
