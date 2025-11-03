package com.example.tvmaze.dtos.participacao;

import com.example.tvmaze.entities.vo.Imagem;
import com.google.gson.annotations.SerializedName;

public class PersonagemApiDTO {

    @SerializedName("id")
    private Integer externoId;
    
    @SerializedName("name") // ou @JsonProperty("name")
    private String nomePersonagem;

    @SerializedName("image")
    private Imagem imagem;

    public PersonagemApiDTO() {}

    // Getters e Setters

    
    public Integer getExternoId() {
        return externoId;
    }

    public void setExternoId(Integer externoId) {
        this.externoId = externoId;
    }

    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    
}
