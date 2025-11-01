package com.example.tvmaze.controllers.externo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.integration.tvmaze.TvMazeParticipacaoIntegracaoService;

@RestController
@RequestMapping("/integracao/tvmaze/participacoes") // Endpoint base para integrações de participações
public class TvMazeParticipacaoController {

    @Autowired
    private TvMazeParticipacaoIntegracaoService tvMazeParticipacaoIntegracaoService;

    /**
     * Endpoint para importar as participações (cast) de uma série específica
     * da API TVMaze usando o ID externo da série.
     *
     * Exemplo de uso: GET /integracao/tvmaze/participacoes/importar/serie/{serieExternoId}
     * Onde {serieExternoId} é o ID da série no TVMaze (ex: 139 para Game of Thrones).
     */
    @PostMapping("/importar/serie/{serieExternoId}")
    public ResponseEntity<List<ParticipacaoRespostaDTO>> importarParticipacoesPorSerie(
            @PathVariable Integer serieExternoId) {
        try {
            List<ParticipacaoRespostaDTO> participacoesImportadas =
                    tvMazeParticipacaoIntegracaoService.importarParticipacoesPorSerieExterna(serieExternoId);
            return new ResponseEntity<>(participacoesImportadas, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Logar o erro completo para depuração no servidor
            System.err.println("Erro ao importar participações para a série externa " + serieExternoId + ": " + e.getMessage());
            // Retornar uma mensagem de erro mais amigável ao cliente
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ou HttpStatus.NOT_FOUND se for erro de série não encontrada
        }
    }

    /**
     * Endpoint para importar as participações (cast) de TODAS as séries
     * que já estão salvas no banco de dados e possuem um ID externo do TVMaze.
     *
     * Exemplo de uso: POST /integracao/tvmaze/participacoes/importar/todas
     * Este pode ser um processo demorado, então considere rodá-lo em segundo plano
     * ou com limites de requisição/tempo.
     */
    @PostMapping("/importar/todas")
    public ResponseEntity<String> importarParticipacoesDeTodasAsSeries() {
        try {
            // Este método não retorna diretamente a lista de DTOs, apenas logs no console.
            // Poderíamos adicionar um retorno mais detalhado se o serviço fosse modificado para isso.
            tvMazeParticipacaoIntegracaoService.importarParticipacoesDeTodasAsSeries();
            return new ResponseEntity<>("Iniciada importação de participações para todas as séries.", HttpStatus.OK);
        } catch (RuntimeException e) {
            System.err.println("Erro ao importar participações para todas as séries: " + e.getMessage());
            return new ResponseEntity<>("Erro ao iniciar importação de participações: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Você pode adicionar outros endpoints aqui se houver outras funcionalidades
    // de integração de participações (ex: sincronizar, verificar status, etc.)
}