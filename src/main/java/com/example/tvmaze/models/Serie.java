package com.example.tvmaze.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
  private String sinopse;
  private Double nota;
  private LocalDate dataEstreia;
  private LocalDate dataTermino;

  @ManyToMany
  @JoinTable(
      name = "serie_genero",
      joinColumns = @JoinColumn(name = "serie_id"),
      inverseJoinColumns = @JoinColumn(name = "genero_id")
  )
  private Set<Genero> generos = new HashSet<>();

  @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Episodio> episodios = new ArrayList<>();

  @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Participacao> participacoes = new HashSet<>();

  public Serie() {}

  public Integer getSerieId() {
    return serieId;
  }

  public void setSerieId(Integer serieId) {
    this.serieId = serieId;
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
    episodio.setSerie(this);
  }

  public void removerEpisodio(Episodio episodio) {
    episodios.remove(episodio);
    episodio.setSerie(null);
  }

  public Set<Participacao> getParticipacoes() {
    return participacoes;
  }

  public void adicionarParticipacao(Participacao participacao) {
    participacoes.add(participacao);
    participacao.setSerie(this);
  }

  public void removerParticipacao(Participacao participacao) {
    participacoes.remove(participacao);
    participacao.setSerie(null);
  }
}
