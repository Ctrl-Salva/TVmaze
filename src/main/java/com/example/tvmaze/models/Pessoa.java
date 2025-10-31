package com.example.tvmaze.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer pessoaId;

  private String nome;

  private LocalDate dataNascimento;

  @OneToMany(mappedBy = "pessoa") 
  private Set<Participacao> participacoes = new HashSet<>();

  public Pessoa() {}

  public Integer getPessoaId() {
    return pessoaId;
  }

  public void setPessoaId(Integer pessoaId) {
    this.pessoaId = pessoaId;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public Set<Participacao> getParticipacoes() {
    return participacoes;
  }

  public void setParticipacoes(Set<Participacao> participacoes) {
    this.participacoes.clear();
    this.participacoes.addAll(participacoes);
  }
}
