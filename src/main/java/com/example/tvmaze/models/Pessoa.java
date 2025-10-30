package com.example.tvmaze.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pessoaId;

  private String nome;

  private LocalDate dataNacimento;

  public Pessoa() {
  }

  public Long getPessoaId() {
    return pessoaId;
  }

  public void setPessoaId(Long pessoaId) {
    this.pessoaId = pessoaId;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDate getDataNacimento() {
    return dataNacimento;
  }

  public void setDataNacimento(LocalDate dataNacimento) {
    this.dataNacimento = dataNacimento;
  }

}
