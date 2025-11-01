package com.example.tvmaze.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.*;

@Entity
public class Serie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer serieId;

  private Integer externoId;
  private String nome;
  private String linguagem;

  @Column(columnDefinition = "TEXT")
  private String sinopse;

  private Double nota;
  private LocalDate dataEstreia;
  private LocalDate dataTermino;

  @ManyToMany
  @JoinTable(name = "serie_genero", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "genero_id"))
  private Set<Genero> generos = new HashSet<>();

  public Integer getSerieId() {
    return serieId;
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

  public void setGeneros(Set<Genero> generos) {
    this.generos.clear();
    this.generos.addAll(generos);
  }
}
