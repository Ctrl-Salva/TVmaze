package com.example.tvmaze.controllers.restcontrollers;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.models.Serie;
import com.example.tvmaze.services.TvMazeIntegracaoService;

@RestController
@RequestMapping("/api/external")
public class SerieExternaRestController {

    @Autowired
    private TvMazeIntegracaoService tvMazeService;

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarSeriePorNome(@RequestParam String nome) {
        try {
            Serie serie = tvMazeService.importarSeriePorNome(nome);
            return ResponseEntity.ok(serie);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/importar")
    public ResponseEntity<?> importarSeriesPadrao() {
        try {
            List<Serie> series = tvMazeService.importarSeriesPadrao();
            return ResponseEntity.ok(series);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}
