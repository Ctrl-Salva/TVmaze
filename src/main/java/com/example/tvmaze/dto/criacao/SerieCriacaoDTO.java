package com.example.tvmaze.dto.criacao;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.tvmaze.models.Genero;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.GeneroRepository;

public class SerieCriacaoDTO {
    private Integer externoId;
    private String nome;
    private String linguagem;
    private String sinopse;
    private Double nota;
    private LocalDate dataEstreia;
    private LocalDate dataTermino;
    private Set<String> generos;

    public Serie toEntity(GeneroRepository generoRepository) {
        Serie serie = new Serie();
        serie.setExternoId(externoId);
        serie.setNome(nome);
        serie.setLinguagem(linguagem);
        serie.setSinopse(sinopse);
        serie.setNota(nota);
        serie.setDataEstreia(dataEstreia);
        serie.setDataTermino(dataTermino);

        Set<Genero> generosConvertidos = this.generos.stream()
                .map(nomeGenero -> generoRepository.findByNome(nomeGenero)
                        .orElseGet(() -> {
                            Genero novoGenero = new Genero();
                            novoGenero.setNome(nomeGenero);
                            return generoRepository.save(novoGenero);
                        }))
                .collect(Collectors.toSet());

        serie.adicionarGeneros(generosConvertidos);

        return serie;
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

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public LocalDate getDataEstreia() {
        return dataEstreia;
    }

    public void setDataEstreia(LocalDate dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<String> generos) {
        this.generos = generos;
    }

}
