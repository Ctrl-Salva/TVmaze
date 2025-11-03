package com.example.tvmaze.dtos.pessoa;

import java.time.LocalDate;

import com.example.tvmaze.models.vo.Imagem;


public class PessoaRespostaDTO {
    
    private Integer pessoaId;

    private Integer externoId;

    private String nome;

    private LocalDate dataNascimento;

    private Imagem imagem;

    
    public PessoaRespostaDTO() {
    }
    
    public Integer getPessoaId() {
        return pessoaId;
    }
    
    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }
    
    public Integer getExternoId() {
        return externoId;
    }
    
    public void setExternoId(Integer externoId) {
        this.externoId = externoId;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    
    
    public Imagem getImagem() {
        return imagem;
    }
    
    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }
}
