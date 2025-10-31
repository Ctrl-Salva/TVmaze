package com.example.tvmaze.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.genero.GeneroCriacaoDTO;
import com.example.tvmaze.dtos.genero.GeneroRespostaDTO;
import com.example.tvmaze.entities.Genero;
import com.example.tvmaze.services.GeneroService;

@RestController
@RequestMapping("/api/generos")
public class GeneroRestController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public List<GeneroRespostaDTO> listar() {
        return generoService.listarGeneros();
    }

    @GetMapping("/buscar")
    GeneroRespostaDTO listarNomes(@RequestParam String nome){
        return generoService.buscarPorNome(nome);
    }

    @GetMapping("/{id}")
    public GeneroRespostaDTO buscarPorId(@PathVariable Integer id) {
        return generoService.buscarPorId(id);
    }

    @PostMapping
    public GeneroRespostaDTO criar(@RequestBody GeneroCriacaoDTO dto) {
        return generoService.salvarGenero(dto);
    }

    @PutMapping("/{id}")
    public GeneroRespostaDTO atualizar(@PathVariable Integer id, @RequestBody GeneroCriacaoDTO dto){
        return generoService.atualizarGenero(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        generoService.deletarGenero(id);
    }
}