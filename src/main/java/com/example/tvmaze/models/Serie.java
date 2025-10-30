package com.example.tvmaze.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Serie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long serieId;

  private Long externoId;

  private String nome;

  private String linguagem;

  private String sinopse;

  private Double nota;

  private LocalDate dataEstreia;

  private LocalDate dataTermino;

  private Set<Genero> generos = new HashSet<>();

  private List<Episodio> episodios = new ArrayList<>();

  public Serie() {
  }

  public Long getSerieId() {
    return serieId;
  }

  public void setSerieId(Long serieId) {
    this.serieId = serieId;
  }

  public Long getExternoId() {
    return externoId;
  }

  public void setExternoId(Long externoId) {
    this.externoId = externoId;
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

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  public Double getNota() {
    return nota;
  }

  public void setNota(Double nota) {
    this.nota = nota;
  }

  public LocalDate getDataEstreia() {
    return dataEstreia;
  }

  public void setDataEstreia(LocalDate dataEstreia) {
    this.dataEstreia = dataEstreia;
  }

  public LocalDate getDataTermino() {
    return dataTermino;
  }

  public void setDataTermino(LocalDate dataTermino) {
    this.dataTermino = dataTermino;
  }

  public Set<Genero> getGeneros() {
    return generos;
  }

  public void adicionarGenero(Genero genero) {
    generos.add(genero);
  }

  public void removerGenero(Genero genero) {
    generos.remove(genero);
  }

  public List<Episodio> getEpisodios() {
    return episodios;
  }

  public void adicionarEpisodio(Episodio episodio) {
    episodios.add(episodio);
  }

  public void removerEpisodio(Episodio episodio) {
    episodios.remove(episodio);
  }
}
