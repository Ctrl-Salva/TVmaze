package com.example.tvmaze.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.models.Serie;
import com.example.tvmaze.services.SerieService;

@RestController
@RequestMapping("/api/series")
public class SerieRestController {
    
    @Autowired
    SerieService serieService;

    @GetMapping
    public List<Serie> listar(){
        return serieService.listarSeries();
    }

    @GetMapping("/{id}")
    public Serie buscarPorId(@PathVariable Integer id){
        return serieService.buscarPorId(id);
    }

    @PostMapping
    public Serie criar(@RequestBody Serie serie){
        return serieService.salvarSerie(serie);
    }

    @PutMapping("/{id}")
    public Serie atualizar(@PathVariable Integer id, @RequestBody Serie serieAtualizada){
        return serieService.atualizarSerie(id, serieAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        serieService.deletarSerie(id);
    }
}
