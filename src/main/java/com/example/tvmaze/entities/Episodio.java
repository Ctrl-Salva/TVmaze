package com.example.tvmaze.entities;

import java.time.LocalDate;

import com.example.tvmaze.entities.vo.Imagem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Episodio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer episodioId;
  private Integer externoId;

  private String nome;
  private Integer temporada;
  private Integer numero;
  private LocalDate dataExibicao;
  private Integer duracao;

  @ManyToOne                
  @JoinColumn(name = "serie_id")  
  private Serie serie;

  private Imagem imagem;
  
  public Episodio() {}
  
  public Integer getEpisodioId() {
    return episodioId;
  }
  
  public void setEpisodioId(Integer episodioId) {
    this.episodioId = episodioId;
  }
  
  public Integer getExternoId() {
    return externoId;
  }

  public void setExternoId(Integer externoId) {
    this.externoId = externoId;
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

  public Serie getSerie() {
    return serie;
  }

  public void setSerie(Serie serie) {
    this.serie = serie;
  }

  public Imagem getImagem() {
    return imagem;
  }

  public void setImagem(Imagem imagem) {
    this.imagem = imagem;
  }

  
}
