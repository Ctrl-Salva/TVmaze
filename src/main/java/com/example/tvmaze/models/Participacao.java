package com.example.tvmaze.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Participacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long participacaoId;

  private String personagem;

  private Pessoa pessoa;

  public Participacao() {
  }

  public Long getParticipacaoId() {
    return participacaoId;
  }

  public void setParticipacaoId(Long participacaoId) {
    this.participacaoId = participacaoId;
  }

  public String getPersonagem() {
    return personagem;
  }

  public void setPersonagem(String personagem) {
    this.personagem = personagem;
  }

  public Pessoa getPessoa() {
    return pessoa;
  }

  public void setPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
  }

}
