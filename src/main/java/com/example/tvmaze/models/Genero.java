package com.example.tvmaze.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Genero {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer generoId;

  @Column(nullable = false, unique = true)
  private String nome;

  @ManyToMany(mappedBy = "generos")
  @JsonIgnore
  private Set<Serie> series = new HashSet<>();

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

  public Set<Serie> getSeries() {
    return series;
  }

  public void setSeries(Set<Serie> series) {
    this.series = series;
  }
}
