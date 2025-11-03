package com.example.tvmaze.dtos.participacao;

import com.example.tvmaze.dtos.personagem.PersonagemCriacaoDTO;


public class ParticipacaoCriacaoDTO {

  private Integer pessoaId; 
  private Integer serieId;
  private PersonagemCriacaoDTO personagem;

  public ParticipacaoCriacaoDTO() {
  }
  
    public Integer getPessoaId() {
      return pessoaId;
    }
  
    public void setPessoaId(Integer pessoaId) {
      this.pessoaId = pessoaId;
    }
  
    public Integer getSerieId() {
      return serieId;
    }
  
    public void setSerieId(Integer serieId) {
      this.serieId = serieId;
    }
  
  public PersonagemCriacaoDTO getPersonagem() {
    return personagem;
  }
  
  public void setPersonagem(PersonagemCriacaoDTO personagem) {
    this.personagem = personagem;
  }

}
