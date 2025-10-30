package com.example.tvmaze.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Genero {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer generoId;

  private String nome;

  public Genero() {

  }

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
