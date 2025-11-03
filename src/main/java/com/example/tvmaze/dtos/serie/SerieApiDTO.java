package com.example.tvmaze.dtos.serie;

import java.util.Set;

import com.example.tvmaze.models.vo.Imagem;
import com.google.gson.annotations.SerializedName;

public class SerieApiDTO {

    @SerializedName("id")
    private Integer externoId;

    @SerializedName("name")
    private String nome;

    @SerializedName("language")
    private String linguagem;

    @SerializedName("summary")
    private String sinopse;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("premiered")
    private String dataEstreia;

    @SerializedName("ended")
    private String dataTermino;

    @SerializedName("genres")
    private Set<String> generos;

    @SerializedName("image")
    private Imagem imagem;

    public static class Rating {
        private Double average;

        public Double getAverage() {
            return average;
        }

        public void setAverage(Double average) {
            this.average = average;
        }
    }

    // GETTERS E SETTERS
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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<String> generos) {
        this.generos = generos;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

}
