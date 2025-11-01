package com.example.tvmaze.dtos.participacao;


import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.google.gson.annotations.SerializedName;

public class ParticipacaoApiDTO {
    
    @SerializedName("person") // ou @JsonProperty("person")
    private PessoaApiDTO pessoa;
     // AGORA USA SEU PessoaApiDTO
    @SerializedName("character") // ou @JsonProperty("character")
    private PersonagemApiDTO personagem;

    public ParticipacaoApiDTO() {}

    // Getters e Setters
    public PessoaApiDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaApiDTO pessoa) {
        this.pessoa = pessoa;
    }

    public PersonagemApiDTO getPersonagem() {
        return personagem;
    }

    public void setPersonagem(PersonagemApiDTO personagem) {
        this.personagem = personagem;
    }
}
