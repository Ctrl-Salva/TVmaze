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
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.serie.SerieCriacaoDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.services.SerieService;

@RestController
@RequestMapping("/api/series")
public class SerieRestController {

    @Autowired
    SerieService serieService;

    @GetMapping
    public List<SerieRespostaDTO> listar() {
        return serieService.listarSeries();
    }

    @GetMapping("/ordenadas")
    public List<SerieRespostaDTO> listarOrdenadas() {
       return serieService.listarSeriesOrdenadas();
    }

    @GetMapping("/{id}")
    public SerieRespostaDTO buscarPorId(@PathVariable Integer id) {
        return serieService.buscarPorId(id);
    }

    @PostMapping
    public Serie criar(@RequestBody SerieCriacaoDTO dto) {
        return serieService.salvarSerie(dto);
    }

    @PutMapping("/{id}")
    public Serie atualizar(@PathVariable Integer id, @RequestBody SerieCriacaoDTO dto) {
        return serieService.atualizarSerie(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        serieService.deletarSerie(id);
    }
}
