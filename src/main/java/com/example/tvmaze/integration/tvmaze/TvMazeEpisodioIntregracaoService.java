package com.example.tvmaze.integration.tvmaze;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.episodio.EpisodioApiDTO;
import com.example.tvmaze.dtos.episodio.EpisodioRespostaDTO;

import com.example.tvmaze.entities.Episodio;
import com.example.tvmaze.entities.Serie;
import com.example.tvmaze.integration.apimappers.EpisodioApiMapper;
import com.example.tvmaze.mappers.EpisodioMapper;
import com.example.tvmaze.repositories.EpisodioRepository;
import com.example.tvmaze.repositories.SerieRepository;
import com.example.tvmaze.services.SerieService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TvMazeEpisodioIntregracaoService {

    @Autowired
    private TvMazeClient tvMazeClient;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private EpisodioRepository episodioRepository;

    @Autowired
    private EpisodioApiMapper episodioApiMapper;

    @Autowired
    private EpisodioMapper episodioMapper;

    public List<EpisodioRespostaDTO> importarEpisodiosPorSerieExterna(Integer externoId) {
        try {
            // 1️⃣ Buscar série (DTO ou entidade — depende da sua arquitetura)
            Serie serie = serieRepository.findByExternoId(externoId)
                    .orElseThrow(() -> new RuntimeException(
                        "Série com ID externo " + externoId + " não encontrada. Importe a série primeiro."));

            // 2️⃣ Buscar episódios da API TVMaze
            EpisodioApiDTO[] episodiosApi = tvMazeClient.getList(
                    "/shows/" + externoId + "/episodes",
                    EpisodioApiDTO[].class);

            if (episodiosApi == null || episodiosApi.length == 0) {
                throw new RuntimeException("Nenhum episódio encontrado na API para a série externa " + externoId);
            }

            // 3️⃣ Processar cada episódio
            List<EpisodioRespostaDTO> episodiosImportados = new ArrayList<>();
            int novos = 0;
            int atualizados = 0;

            for (EpisodioApiDTO episodioApi : episodiosApi) {
                try {
                    boolean existe = episodioRepository.findByExternoId(episodioApi.getExternoId()).isPresent();
                    EpisodioRespostaDTO resultado = processarEpisodio(episodioApi, serie);
                    episodiosImportados.add(resultado);

                    if (existe)
                        atualizados++;
                    else
                        novos++;

                } catch (Exception e) {
                    System.err.println(
                            "⚠️ Erro ao processar episódio '" + episodioApi.getNome() + "': " + e.getMessage());
                }
            }

            System.out.println("✅ Importação concluída: " + novos + " novos, " + atualizados + " atualizados.");
            return episodiosImportados;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar episódios: " + e.getMessage(), e);
        }
    }

    private EpisodioRespostaDTO processarEpisodio(EpisodioApiDTO episodioApi, Serie serie) {
        Episodio episodio = episodioRepository.findByExternoId(episodioApi.getExternoId())
                .orElseGet(() -> episodioApiMapper.toEntity(episodioApi, serie));

        episodioApiMapper.updateEntity(episodioApi, episodio);

        Episodio episodioSalvo = episodioRepository.save(episodio);
        return episodioMapper.toRespostaDTO(episodioSalvo);
    }

    public void importarEpisodiosDeTodasAsSeries() {
        List<Serie> series = serieRepository.findAll();

        if (series.isEmpty()) {
            System.out.println("⚠️ Nenhuma série encontrada no banco. Importe as séries primeiro.");
            return;
        }

        int totalSeries = 0;
        for (Serie serie : series) {
            try {
                if (serie.getExternoId() == null) {
                    System.out.println("⚠️ Série '" + serie.getNome() + "' sem ID externo — ignorando.");
                    continue;
                }

                System.out.println("📺 Importando episódios da série: " + serie.getNome());
                importarEpisodiosPorSerieExterna(serie.getExternoId());
                totalSeries++;

            } catch (Exception e) {
                System.err.println("❌ Erro ao importar episódios da série '" + serie.getNome() + "': " + e.getMessage());
            }
        }

        System.out.println("✅ Importação de episódios concluída para " + totalSeries + " séries.");
    }
}
