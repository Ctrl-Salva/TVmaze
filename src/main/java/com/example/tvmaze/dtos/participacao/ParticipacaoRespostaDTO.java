package com.example.tvmaze.dtos.participacao;

import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;

public class ParticipacaoRespostaDTO {

    private Integer participacao_id;

    private Integer serie_id;

    private String serie_nome;

    private PessoaRespostaDTO pessoa;

    private PersonagemApiDTO personagem;



    public ParticipacaoRespostaDTO() {
    }


    public Integer getParticipacao_id() {
        return participacao_id;
    }


    public void setParticipacao_id(Integer participacao_id) {
        this.participacao_id = participacao_id;
    }



    public Integer getSerie_id() {
        return serie_id;
    }


    public void setSerie_id(Integer serie_id) {
        this.serie_id = serie_id;
    }


    public String getSerie_nome() {
        return serie_nome;
    }


    public void setSerie_nome(String serie_nome) {
        this.serie_nome = serie_nome;
    }


    public PersonagemApiDTO getPersonagem() {
        return personagem;
    }


    public void setPersonagem(PersonagemApiDTO personagem) {
        this.personagem = personagem;
    }


    public PessoaRespostaDTO getPessoa() {
        return pessoa;
    }


    public void setPessoa(PessoaRespostaDTO pessoa) {
        this.pessoa = pessoa;
    }

    
  
    
}
