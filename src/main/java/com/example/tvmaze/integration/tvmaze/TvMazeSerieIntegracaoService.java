package com.example.tvmaze.integration.tvmaze;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.tvmaze.dtos.serie.SerieApiDTO;
import com.example.tvmaze.entities.Serie;
import com.example.tvmaze.mappers.SerieMapper;
import com.example.tvmaze.repositories.SerieRepository;
import com.example.tvmaze.services.GeneroService;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class TvMazeSerieIntegracaoService {

    @Autowired
    private TvMazeClient tvMazeClient;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private SerieMapper serieMapper;

    @Autowired
    private GeneroService generoService;

    /**
     * Importa uma série da API TVMaze pelo nome.
     */
    public Serie importarSeriePorNome(String nome) {
        try {
            // 🔗 Busca JSON da API
            String endpoint = "/singlesearch/shows?q=" + URLEncoder.encode(nome, StandardCharsets.UTF_8);
            SerieApiDTO dto = tvMazeClient.get(endpoint, SerieApiDTO.class);

            if (dto == null) {
                throw new RuntimeException("Resposta inválida ou vazia da API TVMaze para: " + nome);
            }

            // 🚫 Evita duplicação
            boolean existe = serieRepository.findByExternoId(dto.getExternoId()).isPresent()
                    || serieRepository.findByNomeIgnoreCase(dto.getNome()).isPresent();

            if (existe) {
                throw new RuntimeException("A série '" + dto.getNome() + "' já foi importada.");
            }

            // 🧩 Converte DTO em entidade
            Serie serie = serieMapper.toEntity(dto);

            // 🧹 Limpa HTML da sinopse
            if (serie.getSinopse() != null) {
                serie.setSinopse(serie.getSinopse().replaceAll("<[^>]*>", ""));
            }

            // 🎭 Converter gêneros da API → entidades locais
            Set<String> generosApi = dto.getGeneros();
            if (generosApi != null && !generosApi.isEmpty()) {
                serie.setGeneros(generoService.buscarOuCriarGeneros(generosApi));
            }

            // 💾 Salva no banco
            return serieRepository.save(serie);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar série: " + e.getMessage(), e);
        }
    }

    /**
     * Importa um conjunto de séries padrão da TVMaze.
     */
    public List<Serie> importarSeriesPadrao() {
        List<String> nomes = List.of(
                "Breaking Bad",
                "Game of Thrones",
                "Friends",
                "Stranger Things",
                "The Office",
                "The Mandalorian",
                "Peaky Blinders",
                "The Witcher",
                "The Boys",
                "Sherlock");

        List<Serie> importadas = new ArrayList<>();

        for (String nome : nomes) {
            try {
                Serie serie = importarSeriePorNome(nome);
                importadas.add(serie);
            } catch (RuntimeException e) {
                System.err.println("⚠️ Falha ao importar '" + nome + "': " + e.getMessage());
            }
        }

        System.out.println("✅ Importação concluída. Séries importadas: " + importadas.size());
        return importadas;
    }
}
