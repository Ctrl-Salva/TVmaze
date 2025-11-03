package com.example.tvmaze.dtos.episodio;

import java.time.LocalDate;

import com.example.tvmaze.models.vo.Imagem;



public class EpisodioRespostaDTO {

    private Integer episodioId;
    private Integer externoId;
    private String nome;
    private Integer temporada;
    private Integer numero;
    private LocalDate dataExibicao;
    private Integer duracao;
    private Integer serieId;
    private String serieNome;
    private Imagem imagem;

    // Construtores
    public EpisodioRespostaDTO() {
    }

    // Getters e Setters
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

    public Integer getTemporada() {
        return temporada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer temporada() {
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

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public String getSerieNome() {
        return serieNome;
    }

    public void setSerieNome(String serieNome) {
        this.serieNome = serieNome;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    
}
