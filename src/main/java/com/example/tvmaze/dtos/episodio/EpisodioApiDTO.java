package com.example.tvmaze.dtos.episodio;

import java.time.LocalDate;

import com.example.tvmaze.models.vo.Imagem;
import com.google.gson.annotations.SerializedName;

public class EpisodioApiDTO {
    

    @SerializedName("id")
    private Integer externoId;

    @SerializedName("name")
    private String nome;

    @SerializedName("season")
    private Integer temporada;

    @SerializedName("number")
    private Integer numero;

    @SerializedName("airdate")
    private LocalDate dataExibicao;

    @SerializedName("runtime")
    private Integer duracao;

    @SerializedName("summary")
    private String sinopse;

    @SerializedName("image")
    private Imagem imagem;


    public EpisodioApiDTO() {}

  
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

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }


    public Imagem getImagem() {
        return imagem;
    }


    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    
}
