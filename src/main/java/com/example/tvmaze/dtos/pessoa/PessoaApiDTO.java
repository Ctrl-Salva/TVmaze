package com.example.tvmaze.dtos.pessoa;

import java.time.LocalDate;

import com.google.gson.annotations.SerializedName;

public class PessoaApiDTO {

    @SerializedName("id")
    private Integer externoId;

    @SerializedName("name")
    private String nome;

    @SerializedName("birthday")
    private LocalDate dataNascimento;

    public PessoaApiDTO() {
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

    

}
