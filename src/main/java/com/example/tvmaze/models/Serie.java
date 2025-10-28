package com.example.tvmaze.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class Serie {

  @Id
  private Long serieId;

  private String nome;

  private String linguagem;

  private String status;

  private String sinopse;

  private Float media;

  private LocalDateTime dataEstreia;

  private LocalDateTime dataTermino;

  public Serie(Long serieId, String nome, String linguagem, String status, String sinopse, Float media,
      LocalDateTime dataEstreia, LocalDateTime dataTermino) {
    this.serieId = serieId;
    this.nome = nome;
    this.status = status;
    this.sinopse = sinopse;
    this.media = media;
    this.dataEstreia = dataEstreia;
    this.dataTermino = dataTermino;
  }

  public Serie() {

  }

  public long getSerieId() {
    return serieId;
  }

  public void setSerieId(long serieId) {
    this.serieId = serieId;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getLinguagem() {
    return linguagem;
  }

  public void setLinguagem(String linguagem) {
    this.linguagem = linguagem;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  public float getMedia() {
    return media;
  }

  public void setMedia(float media) {
    this.media = media;
  }

  public LocalDateTime getDataEstreia() {
    return dataEstreia;
  }

  public void setDataEstreia(LocalDateTime dataEstreia) {
    this.dataEstreia = dataEstreia;
  }

  public LocalDateTime getDataTermino() {
    return dataTermino;
  }

  public void setDataTermino(LocalDateTime dataTermino) {
    this.dataTermino = dataTermino;
  }
}
