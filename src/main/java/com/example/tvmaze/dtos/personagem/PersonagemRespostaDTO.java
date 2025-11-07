package com.example.tvmaze.dtos.personagem;

import com.example.tvmaze.models.vo.Imagem;

public class PersonagemRespostaDTO {
    
    private Integer personagemId;
    private Integer externoId;
    private String nomePersonagem;
    private Imagem imagem;

    public PersonagemRespostaDTO() {}

    public PersonagemRespostaDTO(Integer personagemId, Integer externoId, String nomePersonagem, Imagem imagem) {
        this.personagemId = personagemId;
        this.externoId = externoId;
        this.nomePersonagem = nomePersonagem;
        this.imagem = imagem;
    }

    public Integer getPersonagemId() {
        return personagemId;
    }

    public void setPersonagemId(Integer personagemId) {
        this.personagemId = personagemId;
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
