package com.example.tvmaze.dtos.episodio;

import java.time.LocalDate;

import com.example.tvmaze.entities.vo.Imagem;

public class EpisodioCriacaoDTO {
    private Integer externoId;
    private String nome;
    private Integer temporada;
    private Integer numero;
    private LocalDate dataExibicao;
    private Integer duracao;
    private Imagem imagem;
  

    // Construtores
    public EpisodioCriacaoDTO() {
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

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }


    
}
