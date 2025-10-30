package com.example.tvmaze.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Episodio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer episodioId;

  private String nome;

  private Integer temporada;

  private Integer numero;

  private LocalDate dataExibicao;

  private Integer duracao;

  public Episodio() {
  }

  public Integer getEpisodioId() {
    return episodioId;
  }

  public void setEpisodioId(Integer episodioId) {
    this.episodioId = episodioId;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getTemporada() {
    return temporada;
  }

  public void setTemporada(Integer temporada) {
    this.temporada = temporada;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public LocalDate getDataExibicao() {
    return dataExibicao;
  }

  public void setDataExibicao(LocalDate dataExibicao) {
    this.dataExibicao = dataExibicao;
  }

  public Integer getDuracao() {
    return duracao;
  }

  public void setDuracao(Integer duracao) {
    this.duracao = duracao;
  }
}
