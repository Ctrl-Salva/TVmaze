package com.example.tvmaze.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.SerieRepository;
import com.google.gson.*;

@Service
public class TvMazeIntegracaoService {

    private static final String BASE_URL = "https://api.tvmaze.com";

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final GeneroService generoService;
    private final SerieRepository serieRepository;

    @Autowired
    public TvMazeIntegracaoService(GeneroService generoService, SerieRepository serieRepository) {
        this.restTemplate = new RestTemplate();

        // 🔧 Gson configurado para entender LocalDate
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                            throws JsonParseException {
                        return json.isJsonNull() ? null
                                : LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_DATE);
                    }
                })
                .create();

        this.generoService = generoService;
        this.serieRepository = serieRepository;
    }

    public Serie importarSeriePorNome(String nome) {
        try {
            String url = BASE_URL + "/singlesearch/shows?q=" + URLEncoder.encode(nome, StandardCharsets.UTF_8);
            String json = restTemplate.getForObject(url, String.class);

            Serie serie = gson.fromJson(json, Serie.class);
            if (serie == null) {
                throw new RuntimeException("Resposta inválida da API");
            }

            boolean existe = serieRepository.findByExternoId(serie.getExternoId()).isPresent()
                    || serieRepository.findByNomeIgnoreCase(serie.getNome()).isPresent();
            if (existe) {
                throw new RuntimeException("A série '" + serie.getNome() + "' já foi importada.");
            }

            // 🔧 limpar HTML da sinopse
            if (serie.getSinopse() != null) {
                serie.setSinopse(serie.getSinopse().replaceAll("<[^>]*>", ""));
            }

            // 🔧 Converter os gêneros (strings) → entidades
            Set<String> generosApi = serie.getGenerosApi();
            if (generosApi != null && !generosApi.isEmpty()) {
                serie.setGeneros(generoService.buscarOuCriarGeneros(generosApi));
            }

            // 🔧 salvar série no banco
            return serieRepository.save(serie);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar série: " + e.getMessage(), e);
        }
    }

    public List<Serie> importarSeriesPadrao() {
        // 🧩 Lista de nomes de séries para importar automaticamente
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
                // ⚠️ Não interrompe o processo, apenas registra falha
                System.err.println("Falha ao importar '" + nome + "': " + e.getMessage());
            }
        }

        return importadas;
    }
}
