package com.example.tvmaze.controllers.externo;


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

import com.example.tvmaze.integration.tvmaze.TvMazeSerieIntegracaoService;
import com.example.tvmaze.models.Serie;

@RestController
@RequestMapping("/api/external")
public class TvMazeSerieController {

    @Autowired
    private TvMazeSerieIntegracaoService tvMazeService;

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
