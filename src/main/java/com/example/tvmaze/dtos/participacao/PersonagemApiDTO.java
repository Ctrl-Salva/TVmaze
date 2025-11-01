package com.example.tvmaze.dtos.participacao;

import com.google.gson.annotations.SerializedName;

public class PersonagemApiDTO {

    @SerializedName("id")
    private Integer externoId;
    
    @SerializedName("name") // ou @JsonProperty("name")
    private String nomePersonagem;

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

}
