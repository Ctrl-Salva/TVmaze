package com.example.tvmaze.controllers.externo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.episodio.EpisodioRespostaDTO;
import com.example.tvmaze.integration.tvmaze.TvMazeEpisodioIntegracaoService;



@RestController
@RequestMapping("/api/external")
public class TvMazeEpisodioController {
    
    @Autowired
    TvMazeEpisodioIntegracaoService tvMazeEpisodioIntregracaoService;

    @GetMapping("/{id}")
    public List<EpisodioRespostaDTO> buscarEpisodio(@PathVariable Integer externoId){
        return tvMazeEpisodioIntregracaoService.importarEpisodiosPorSerieExterna(externoId);
    }

    @GetMapping("/todos")
    public void buscar(){
        tvMazeEpisodioIntregracaoService.importarEpisodiosDeTodasAsSeries();
    }
}
