package com.example.tvmaze.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.SerializedName;

import jakarta.persistence.*;

@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serieId;

    @SerializedName("id")
    private Integer externoId; 

    @SerializedName("name")
    private String nome;

    @SerializedName("language")
    private String linguagem;

    @SerializedName("summary")
    @Lob
    private String sinopse;

    @SerializedName("premiered")
    private LocalDate dataEstreia;

    @SerializedName("ended")
    private LocalDate dataTermino;

    @SerializedName("rating")
    @Transient 
    private Rating rating;

    @SerializedName("genres")
    @Transient 
    private Set<String> generosApi;

    private Double nota;

    @ManyToMany
    @JoinTable(name = "serie_genero",
        joinColumns = @JoinColumn(name = "serie_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id"))
    private Set<Genero> generos = new HashSet<>();

    
    public static class Rating {
        private Double average;
        public Double getAverage() { return average; }
    }

    
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (rating != null) this.nota = rating.getAverage();
        if (sinopse != null) this.sinopse = sinopse.replaceAll("<[^>]*>", "");
    }

   
    public Integer getSerieId() { return serieId; }
    public Integer getExternoId() { return externoId; }
    public void setExternoId(Integer externoId) { this.externoId = externoId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLinguagem() { return linguagem; }
    public void setLinguagem(String linguagem) { this.linguagem = linguagem; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }
    public LocalDate getDataEstreia() { return dataEstreia; }
    public void setDataEstreia(LocalDate dataEstreia) { this.dataEstreia = dataEstreia; }
    public LocalDate getDataTermino() { return dataTermino; }
    public void setDataTermino(LocalDate dataTermino) { this.dataTermino = dataTermino; }

  public Set<Genero> getGeneros() {
    return generos;
  }

  public void setGeneros(Set<Genero> generos) {
    this.generos.clear();
    this.generos.addAll(generos);
  }

  public Set<String> getGenerosApi() { return generosApi; }
}
