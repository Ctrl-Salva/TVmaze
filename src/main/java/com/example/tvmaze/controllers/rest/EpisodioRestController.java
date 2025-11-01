package com.example.tvmaze.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.episodio.EpisodioCriacaoDTO;
import com.example.tvmaze.dtos.episodio.EpisodioRespostaDTO;
import com.example.tvmaze.services.EpisodioService;



@RestController
@RequestMapping("/api/series/{serieId}/episodios")
public class EpisodioRestController {

    @Autowired
    private EpisodioService episodioService;

    @GetMapping
    public List<EpisodioRespostaDTO> listarEpisodios(@PathVariable Integer serieId) {
        return episodioService.listarEpisodiosPorSerie(serieId);
    }

    @GetMapping(params = "temporada")
    public List<EpisodioRespostaDTO> listarEpisodiosPorTemporada(@PathVariable Integer serieId,@RequestParam Integer temporada) {
        return episodioService.listarEpisodiosPorTemporada(serieId, temporada);
    }

    @GetMapping("/temporadas")
    public List<Integer> listarTemporadas(@PathVariable Integer serieId) {
        return episodioService.listarTemporadas(serieId);
    }

    @GetMapping("/{episodioId}")
    public EpisodioRespostaDTO buscarEpisodio(@PathVariable Integer serieId, @PathVariable Integer episodioId) {
        return episodioService.buscarPorId(episodioId);
    }

    @PostMapping
    public EpisodioRespostaDTO criarEpisodio(
            @PathVariable Integer serieId,
            @RequestBody EpisodioCriacaoDTO episodioCriacaoDTO) {

        return episodioService.criarEpisodio(serieId, episodioCriacaoDTO);
    
    }




    @PutMapping("/{episodioId}")
    public EpisodioRespostaDTO atualizarEpisodio(
            @PathVariable Integer serieId,
            @PathVariable Integer episodioId,
            @RequestBody EpisodioCriacaoDTO dto) {
        return episodioService.atualizarEpisodio(episodioId, dto);
    }

    @DeleteMapping("/{episodioId}")
    public ResponseEntity<Void> deletarEpisodio(
            @PathVariable Integer serieId,
            @PathVariable Integer episodioId) {
        episodioService.deletarEpisodio(episodioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public Long contarEpisodios(@PathVariable Integer serieId) {
        return episodioService.contarEpisodiosPorSerie(serieId);
    }
}
