package com.example.tvmaze.entities;

import com.example.tvmaze.entities.vo.Personagem;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
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

  @AttributeOverride(
        name = "externoId", // Nome do campo no objeto embutido (Personagem)
        column = @Column(name = "personagem_externo_id") // Novo nome da coluna no banco
    )
  private Personagem personagem;

  @ManyToOne  
  @JoinColumn(name = "pessoa_id")
  private Pessoa pessoa;

  @ManyToOne 
  @JoinColumn(name = "serie_id")
  private Serie serie;

  public Participacao() {}
  
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
