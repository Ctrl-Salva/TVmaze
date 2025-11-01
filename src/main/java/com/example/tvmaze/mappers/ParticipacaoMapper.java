package com.example.tvmaze.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.participacao.ParticipacaoApiDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.example.tvmaze.entities.Participacao;
import com.example.tvmaze.entities.Pessoa;
import com.example.tvmaze.entities.Serie;
import com.example.tvmaze.repositories.ParticipacaoRepository;
import com.example.tvmaze.repositories.PessoaRepository;
import com.example.tvmaze.repositories.SerieRepository;

@Component
public class ParticipacaoMapper {

    @Autowired
    private PessoaRepository pessoaRepository; // Para buscar a Pessoa
    @Autowired
    private SerieRepository serieRepository; 
    
    @Autowired
    private ParticipacaoRepository participacaoRepository;

    // Para buscar a Série

    /**
     * Converte uma entidade Participacao para ParticipacaoRespostaDTO.
     */
    public ParticipacaoRespostaDTO toRespostaDTO(Participacao participacao) {
        ParticipacaoRespostaDTO dto = new ParticipacaoRespostaDTO();
        dto.setParticipacaoId(participacao.getParticipacaoId());
        dto.setExternoId(participacao.getExternoId()); // Adicionado ExternoId
        dto.setPersonagem(participacao.getPersonagem());

        // Mapeia informações da Pessoa
        if (participacao.getPessoa() != null) {
            dto.setPessoaId(participacao.getPessoa().getPessoaId());
            dto.setPessoaNome(participacao.getPessoa().getNome());
        }

        // Mapeia informações da Série
        if (participacao.getSerie() != null) {
            dto.setSerieId(participacao.getSerie().getSerieId());
            dto.setSerieNome(participacao.getSerie().getNome()); // Assumindo 'titulo' em Serie
        }
        return dto;
    }

     public Participacao toEntity(ParticipacaoApiDTO dto, Serie serie) {
        Participacao participacao = new Participacao();

        // 1. Gerenciar a Pessoa (PessoaApiDTO -> Pessoa)
        Pessoa pessoa = findOrCreatePessoa(dto.getPessoa());
        participacao.setPessoa(pessoa);

        // 2. Mapear o personagem
        if (dto.getPersonagem() != null) {
            participacao.setPersonagem(dto.getPersonagem().getNomePersonagem());
        }

        // 3. Associar a Série
        participacao.setSerie(serie);

        return participacao;
    }

    public Pessoa findOrCreatePessoa(PessoaApiDTO pessoaApiDTO) { // AGORA RECEBE SEU PessoaApiDTO
        if (pessoaApiDTO == null || pessoaApiDTO.getExternoId() == null) {
            throw new IllegalArgumentException("Dados de pessoa da API inválidos para mapeamento.");
        }

        return pessoaRepository.findByExternoId(pessoaApiDTO.getExternoId())
                .map(existingPessoa -> {
                    // Se a pessoa já existe, atualiza seus dados (nome, dataNascimento)
                    existingPessoa.setNome(pessoaApiDTO.getNome());
                    existingPessoa.setDataNascimento(pessoaApiDTO.getDataNascimento());
                    return pessoaRepository.save(existingPessoa); // Salva as atualizações
                })
                .orElseGet(() -> {
                    // Se a pessoa não existe, cria uma nova
                    Pessoa novaPessoa = new Pessoa();
                    novaPessoa.setExternoId(pessoaApiDTO.getExternoId());
                    novaPessoa.setNome(pessoaApiDTO.getNome());
                    novaPessoa.setDataNascimento(pessoaApiDTO.getDataNascimento()); // Define dataNascimento
                    return pessoaRepository.save(novaPessoa); // Salva a nova pessoa
                });
    }

    public void updateEntity(ParticipacaoApiDTO dto, Participacao participacao) {
        // Atualiza a Pessoa (mesmo que seja só para garantir que exista e esteja atualizada)
        Pessoa pessoa = findOrCreatePessoa(dto.getPessoa()); // Isso também atualiza dados da pessoa se já existe
        participacao.setPessoa(pessoa);

        // Atualiza o personagem
        if (dto.getPersonagem() != null) {
            participacao.setPersonagem(dto.getPersonagem().getNomePersonagem());
        }
    }

    /**
     * Converte um ParticipacaoCriacaoDTO para uma nova entidade Participacao.
     * Lança RuntimeException se Pessoa ou Serie não forem encontradas.
     */
    public Participacao toEntity(ParticipacaoCriacaoDTO dto) {
        Participacao participacao = new Participacao();
        // O ID da Participacao (participacaoId) é gerado pelo banco, não pelo DTO de criação

        participacao.setExternoId(dto.getExternoId());
        participacao.setPersonagem(dto.getPersonagem());

        // Busca e associa a Pessoa
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + dto.getPessoaId()));
        participacao.setPessoa(pessoa);

        // Busca e associa a Série
        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + dto.getSerieId()));
        participacao.setSerie(serie);

        return participacao;
    }

    /**
     * Atualiza uma entidade Participacao existente com dados de ParticipacaoCriacaoDTO.
     * Lança RuntimeException se Pessoa ou Serie não forem encontradas.
     */
    public void toEntity(ParticipacaoCriacaoDTO dto, Participacao participacao) {
        participacao.setExternoId(dto.getExternoId());
        participacao.setPersonagem(dto.getPersonagem());

        // Busca e associa a Pessoa
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + dto.getPessoaId()));
        participacao.setPessoa(pessoa);

        // Busca e associa a Série
        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + dto.getSerieId()));
        participacao.setSerie(serie);
    }

    /**
     * Verifica se já existe uma Participacao com a mesma Pessoa e Serie.
     * ATUALIZADO para usar PessoaApiDTO.
     */
    public boolean exists(ParticipacaoApiDTO dto, Serie serie) {
        if (dto.getPessoa() == null || dto.getPessoa().getExternoId() == null ||
            dto.getPersonagem() == null || dto.getPersonagem().getNomePersonagem() == null) {
            return false; // Não é possível verificar se falta dados essenciais
        }

        Optional<Pessoa> pessoaOpt = pessoaRepository.findByExternoId(dto.getPessoa().getExternoId());
        if (pessoaOpt.isEmpty()) {
            return false; // Pessoa ainda não existe localmente, então a participação também não
        }

        return participacaoRepository.findByPessoaAndSerieAndPersonagem(
                pessoaOpt.get(),
                serie,
                dto.getPersonagem().getNomePersonagem()).isPresent();
    }
}
