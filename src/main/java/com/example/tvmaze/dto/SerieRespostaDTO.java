package com.example.tvmaze.dto;

import java.time.LocalDate;
import java.util.Set;

public class SerieRespostaDTO {
    private Integer serieId;
    private Integer externoId;
    private String nome;
    private String linguagem;
    private String sinopse;
    private Double nota;
    private LocalDate dataEstreia;
    private LocalDate dataTermino;
    private Set<String> genres;
    

    public Integer getSerieId() {
        return serieId;
    }
    public void setSerieId(Integer serieId) {
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
    public Set<String> getGenres() {
        return genres;
    }
    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }
    public Integer getExternoId() {
        return externoId;
    }
    public void setExternoId(Integer externoId) {
        this.externoId = externoId;
    }

    
}
