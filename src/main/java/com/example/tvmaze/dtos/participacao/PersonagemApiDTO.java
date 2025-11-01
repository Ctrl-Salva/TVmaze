package com.example.tvmaze.dtos.participacao;

import com.google.gson.annotations.SerializedName;

public class PersonagemApiDTO {
    
    @SerializedName("name") // ou @JsonProperty("name")
    private String nomePersonagem;

    public PersonagemApiDTO() {}

    // Getters e Setters
    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }
}
