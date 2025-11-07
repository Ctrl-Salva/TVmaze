package com.example.tvmaze.dtos.participacao;

import com.example.tvmaze.dtos.personagem.PersonagemRespostaDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;

public class ParticipacaoRespostaDTO {
    
    private Integer participacaoId;
    private Integer serieId;
    private String serieNome;
    
    private PessoaRespostaDTO pessoa;

    private PersonagemRespostaDTO personagem;

    public ParticipacaoRespostaDTO() {}

    public ParticipacaoRespostaDTO(Integer participacaoId, Integer serieId, String serieNome, 
                                   PessoaRespostaDTO pessoa, PersonagemRespostaDTO personagem) {
        this.participacaoId = participacaoId;
        this.serieId = serieId;
        this.serieNome = serieNome;
        this.pessoa = pessoa;
        this.personagem = personagem;
    }

    public Integer getParticipacaoId() {
        return participacaoId;
    }

    public void setParticipacaoId(Integer participacaoId) {
        this.participacaoId = participacaoId;
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

    public PessoaRespostaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaRespostaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public PersonagemRespostaDTO getPersonagem() {
        return personagem;
    }

    public void setPersonagem(PersonagemRespostaDTO personagem) {
        this.personagem = personagem;
    }
}
