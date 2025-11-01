package com.example.tvmaze.dtos.participacao;


public class ParticipacaoCriacaoDTO {

  private Integer pessoaId; 
  private Integer externoId;
  private Integer serieId;
  private String personagem;

  public ParticipacaoCriacaoDTO() {
  }

  public Integer getExternoId() {
    return externoId;
  }
  
  public void setExternoId(Integer externoId) {
    this.externoId = externoId;
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
  
  public String getPersonagem() {
    return personagem;
  }
  
  public void setPersonagem(String personagem) {
    this.personagem = personagem;
  }

}
