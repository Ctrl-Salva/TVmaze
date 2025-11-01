package com.example.tvmaze;


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

        try {
            System.out.println(">> Importando Séries Padrão...");
            serieIntegracaoService.importarSeriesPadrao(); // Este método deve ter o loop interno

            System.out.println(">> Importando Episódios de Todas as Séries...");
            episodioIntegracaoService.importarEpisodiosDeTodasAsSeries(); // Este método deve ter o loop interno

            System.out.println(">> Importando Participações de Todas as Séries...");
            participacaoIntegracaoService.importarParticipacoesDeTodasAsSeries(); // Este método deve ter o loop interno

            System.out.println("✅ DataInitializer concluído com sucesso.");

        } catch (Exception e) {
            // Este catch agora pega erros gerais que possam ocorrer em qualquer um dos *inícios*
            // dos processos de importação, ou se um dos métodos falhar catastroficamente antes de terminar.
            // Os erros específicos por série devem ser tratados *dentro* dos serviços de integração.
            System.err.println("❌ Erro fatal durante a inicialização de dados: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace para depuração
        }
    }
}