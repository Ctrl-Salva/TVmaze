package com.example.tvmaze.dtos.participacao;


import com.example.tvmaze.dtos.personagem.PersonagemApiDTO;
import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.google.gson.annotations.SerializedName;

public class ParticipacaoApiDTO {
    
    @SerializedName("person")
    private PessoaApiDTO pessoa;
    
    @SerializedName("character")
    private PersonagemApiDTO personagem;

    public ParticipacaoApiDTO() {}

    public ParticipacaoApiDTO(PessoaApiDTO pessoa, PersonagemApiDTO personagem) {
        this.pessoa = pessoa;
        this.personagem = personagem;
    }

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
