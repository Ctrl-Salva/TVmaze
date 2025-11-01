package com.example.tvmaze.dtos.participacao;

public class ParticipacaoRespostaDTO {

    private Integer participacaoId;

    private Integer externoId;

    private String personagem;

    private Integer pessoaId;

    private String pessoaNome;

    private Integer serieId;

    private String serieNome;

    public ParticipacaoRespostaDTO() {
    }

    public Integer getParticipacaoId() {
        return participacaoId;
    }

    public void setParticipacaoId(Integer participacaoId) {
        this.participacaoId = participacaoId;
    }

    public Integer getExternoId() {
        return externoId;
    }

    public void setExternoId(Integer externoId) {
        this.externoId = externoId;
    }

    public String getPersonagem() {
        return personagem;
    }

    public void setPersonagem(String personagem) {
        this.personagem = personagem;
    }

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
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

    
}
