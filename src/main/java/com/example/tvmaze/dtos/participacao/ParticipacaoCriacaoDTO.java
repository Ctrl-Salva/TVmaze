package com.example.tvmaze.dtos.participacao;


public class ParticipacaoCriacaoDTO {

  private Integer serieId;

  private Integer pessoaId;

  private Integer personagemId;

  public ParticipacaoCriacaoDTO() {
  }

  public ParticipacaoCriacaoDTO(Integer serieId,Integer pessoaId, Integer personagemId) {
    this.serieId = serieId;
    this.pessoaId = pessoaId;
    this.personagemId = personagemId;
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

  public Integer getPersonagemId() {
    return personagemId;
  }

  public void setPersonagemId(Integer personagemId) {
    this.personagemId = personagemId;
  }
}
