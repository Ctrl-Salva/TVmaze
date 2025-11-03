package com.example.tvmaze.models.vo;

import com.google.gson.annotations.SerializedName;

import jakarta.persistence.Embeddable;

@Embeddable
public class Imagem {
    
    @SerializedName("original")
    private String original;

    @SerializedName("medium")
    private String medio;

    public Imagem(){}

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    
}