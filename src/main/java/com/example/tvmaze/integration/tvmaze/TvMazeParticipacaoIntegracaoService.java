package com.example.tvmaze.integration.tvmaze; // Ajuste o pacote conforme sua estrutura

import com.example.tvmaze.repositories.ParticipacaoRepository;
import com.example.tvmaze.repositories.PessoaRepository;
import com.example.tvmaze.repositories.SerieRepository;
import com.example.tvmaze.mappers.ParticipacaoMapper;
import com.example.tvmaze.models.Participacao;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.dtos.participacao.ParticipacaoApiDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TvMazeParticipacaoIntegracaoService {

    @Autowired
    private TvMazeClient tvMazeClient;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    @Autowired
    private ParticipacaoMapper participacaoMapper;

    @Autowired
    private PessoaRepository pessoaRepository;


    /**
     * Importa as participações (cast) de uma série específica da API TVMaze.
     *
     * @param serieExternoId O ID externo (TVMaze ID) da série.
     * @return Uma lista de ParticipacaoRespostaDTO das participações importadas/atualizadas.
     */
    public List<ParticipacaoRespostaDTO> importarParticipacoesPorSerieExterna(Integer serieExternoId) {
        try {
            Serie serie = serieRepository.findByExternoId(serieExternoId)
                    .orElseThrow(() -> new RuntimeException(
                        "Série com ID externo " + serieExternoId + " não encontrada. Importe a série primeiro."));

            ParticipacaoApiDTO[] castApi = tvMazeClient.getList(
                    "/shows/" + serieExternoId + "/cast",
                    ParticipacaoApiDTO[].class);

            if (castApi == null || castApi.length == 0) {
                System.out.println("Nenhuma participação (cast) encontrada na API para a série externa " + serieExternoId);
                return List.of();
            }

            List<ParticipacaoRespostaDTO> participacoesImportadas = new ArrayList<>();
            int novos = 0;
            int atualizados = 0;

            for (ParticipacaoApiDTO participacaoApi : castApi) {
                try {
                    // Usa a lógica de verificação de pessoa e participação existente
                    boolean isNew = !participacaoMapper.exists(participacaoApi, serie); // Verifica se a participação como um todo é nova

                    ParticipacaoRespostaDTO resultado = processarParticipacao(participacaoApi, serie);
                    participacoesImportadas.add(resultado);

                    if (isNew) {
                        novos++;
                    } else {
                        atualizados++;
                    }

                } catch (Exception e) {
                    String pessoaNome = (participacaoApi != null && participacaoApi.getPessoa() != null) ? participacaoApi.getPessoa().getNome() : "Desconhecida";
                    String personagemNome = (participacaoApi != null && participacaoApi.getPersonagem() != null) ? participacaoApi.getPersonagem().getNomePersonagem() : "Desconhecido";
                    System.err.println(
                            "⚠️ Erro ao processar participação de '" + pessoaNome + "' como '" + personagemNome + "': " + e.getMessage());
                }
            }

            System.out.println("✅ Importação de participações concluída para a série " + serie.getNome() + ": " + novos + " novas, " + atualizados + " atualizadas.");
            return participacoesImportadas;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao importar participações para a série externa " + serieExternoId + ": " + e.getMessage(), e);
        }
    }

    private ParticipacaoRespostaDTO processarParticipacao(ParticipacaoApiDTO participacaoApi, Serie serie) {
        // Encontra ou cria a pessoa (o mapper já cuida disso)
        // Isso é importante porque o findByPessoaAndSerieAndPersonagem precisa de um objeto Pessoa válido
        participacaoMapper.findOrCreatePessoa(participacaoApi.getPessoa()); // Garante que a pessoa existe e está atualizada

        // Tenta encontrar uma participação existente (Pessoa, Série e Personagem devem ser únicos para uma participação)
        Optional<Participacao> existingParticipacaoOpt = participacaoRepository.findByPessoaAndSerieAndPersonagem_NomePersonagem(
            pessoaRepository.findByExternoId(participacaoApi.getPessoa().getExternoId()).get(), // Busca a pessoa recém-garantida
            serie,
            participacaoApi.getPersonagem().getNomePersonagem()
        );

        Participacao participacao;
        if (existingParticipacaoOpt.isPresent()) {
            participacao = existingParticipacaoOpt.get();
            participacaoMapper.updateEntity(participacaoApi, participacao); // Atualiza a entidade existente
        } else {
            participacao = participacaoMapper.toEntity(participacaoApi, serie); // Cria uma nova entidade
        }

        Participacao participacaoSalva = participacaoRepository.save(participacao);
        return participacaoMapper.toRespostaDTO(participacaoSalva);
    }

    public void importarParticipacoesDeTodasAsSeries() {
        List<Serie> series = serieRepository.findAll();

        if (series.isEmpty()) {
            System.out.println("⚠️ Nenhuma série encontrada no banco. Importe as séries primeiro.");
            return;
        }

        int totalSeriesProcessadas = 0;
        for (Serie serie : series) {
            try {
                if (serie.getExternoId() == null) {
                    System.out.println("⚠️ Série '" + serie.getNome() + "' (ID: " + serie.getSerieId() + ") sem ID externo — ignorando.");
                    continue;
                }

                System.out.println("🎭 Importando participações da série: " + serie.getNome() + " (ID Externo: " + serie.getExternoId() + ")");
                importarParticipacoesPorSerieExterna(serie.getExternoId());
                totalSeriesProcessadas++;

            } catch (Exception e) {
                System.err.println("❌ Erro ao importar participações da série '" + serie.getNome() + "': " + e.getMessage());
            }
        }

        System.out.println("✅ Importação de participações concluída para " + totalSeriesProcessadas + " séries.");
    }
}