package com.example.tvmaze;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.tvmaze.integration.tvmaze.TvMazeEpisodioIntegracaoService;
import com.example.tvmaze.integration.tvmaze.TvMazeParticipacaoIntegracaoService;
import com.example.tvmaze.integration.tvmaze.TvMazeSerieIntegracaoService;

@Component
@Profile("dev") // Opcional: só executa no perfil 'dev'
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TvMazeSerieIntegracaoService serieIntegracaoService;

    @Autowired
    private TvMazeEpisodioIntegracaoService episodioIntegracaoService;

    @Autowired
    private TvMazeParticipacaoIntegracaoService participacaoIntegracaoService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Iniciando DataInitializer...");

        // Exemplo de IDs externos de séries para importar

        
            try {
                System.out.println(">> Importando Série: ");
                serieIntegracaoService.importarSeriesPadrao();

                System.out.println(">> Importando Episódios para a Série: ");
                episodioIntegracaoService.importarEpisodiosDeTodasAsSeries(); 

                System.out.println(">> Importando Participações para a Série: ");
                participacaoIntegracaoService.importarParticipacoesDeTodasAsSeries();;

            } catch (Exception e) {
                System.err.println("❌ Erro ao importar dados para a série "  + ": " + e.getMessage());
                // Continue para a próxima série mesmo se uma falhar
            }
        

        // Se você tiver um método para importar participações de TODAS as séries salvas
        // participacaoIntegracaoService.importarParticipacoesDeTodasAsSeries();

        System.out.println("✅ DataInitializer concluído.");
    }
}
