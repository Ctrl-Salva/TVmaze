package com.example.tvmaze.dtos.personagem;

import com.example.tvmaze.models.vo.Imagem;
import com.google.gson.annotations.SerializedName;

public class PersonagemApiDTO {
    
    @SerializedName("id")
    private Integer externoId;
    
    @SerializedName("name")
    private String nomePersonagem;
    
    @SerializedName("image")
    private Imagem imagem;

    public PersonagemApiDTO() {}

    public PersonagemApiDTO(Integer externoId, String nomePersonagem, Imagem imagem) {
        this.externoId = externoId;
        this.nomePersonagem = nomePersonagem;
        this.imagem = imagem;
    }

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
