package com.example.tvmaze.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.participacao.ParticipacaoApiDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.dtos.personagem.PersonagemApiDTO;
import com.example.tvmaze.dtos.personagem.PersonagemRespostaDTO;
import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.models.Participacao;
import com.example.tvmaze.models.Personagem;
import com.example.tvmaze.models.Pessoa;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.ParticipacaoRepository;
import com.example.tvmaze.repositories.PersonagemRepository;
import com.example.tvmaze.repositories.PessoaRepository;
import com.example.tvmaze.repositories.SerieRepository;

@Component
public class ParticipacaoMapper {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    @Autowired
    private PersonagemMapper personagemMapper;

    /**
     * Converte Participacao para ParticipacaoRespostaDTO
     */
    public ParticipacaoRespostaDTO toRespostaDTO(Participacao participacao) {
        ParticipacaoRespostaDTO dto = new ParticipacaoRespostaDTO();
        dto.setParticipacaoId(participacao.getParticipacaoId());
        dto.setSerieId(participacao.getSerie().getSerieId());
        dto.setSerieNome(participacao.getSerie().getNome());

        // Converte Pessoa usando PessoaMapper
        PessoaRespostaDTO pessoaDTO = pessoaMapper.toRespostaDTO(participacao.getPessoa());
        dto.setPessoa(pessoaDTO);

        // Converte Personagem usando PersonagemMapper
        PersonagemRespostaDTO personagemDTO = personagemMapper.toRespostaDTO(participacao.getPersonagem());
        dto.setPersonagem(personagemDTO);

        return dto;
    }

    /**
     * Converte ParticipacaoApiDTO para Participacao (vindo de API externa)
     */
    public Participacao toEntity(ParticipacaoApiDTO dto, Serie serie) {
        Participacao participacao = new Participacao();

        // 1. Gerenciar a Pessoa (busca ou cria)
        Pessoa pessoa = findOrCreatePessoa(dto.getPessoa());
        participacao.setPessoa(pessoa);

        // 2. Gerenciar o Personagem (busca ou cria)
        Personagem personagem = findOrCreatePersonagem(dto.getPersonagem());
        participacao.setPersonagem(personagem);

        // 3. Associar a Série
        participacao.setSerie(serie);

        return participacao;
    }

    /**
     * Converte ParticipacaoCriacaoDTO para Participacao (criação manual)
     */
    public Participacao toEntity(ParticipacaoCriacaoDTO dto) {
        Participacao participacao = new Participacao();

        // Busca Pessoa
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + dto.getPessoaId()));
        participacao.setPessoa(pessoa);

        // Busca Serie
        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + dto.getSerieId()));
        participacao.setSerie(serie);

        // Busca Personagem
        Personagem personagem = personagemRepository.findById(dto.getPersonagemId())
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + dto.getPersonagemId()));
        participacao.setPersonagem(personagem);

        return participacao;
    }

    /**
     * Atualiza uma Participacao existente com dados de ParticipacaoCriacaoDTO
     */
    public void updateEntity(ParticipacaoCriacaoDTO dto, Participacao participacao) {
        // Atualiza Pessoa
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + dto.getPessoaId()));
        participacao.setPessoa(pessoa);

        // Atualiza Serie
        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + dto.getSerieId()));
        participacao.setSerie(serie);

        // Atualiza Personagem
        Personagem personagem = personagemRepository.findById(dto.getPersonagemId())
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + dto.getPersonagemId()));
        participacao.setPersonagem(personagem);
    }

    /**
     * Atualiza uma Participacao existente com dados de ParticipacaoApiDTO
     */
    public void updateEntity(ParticipacaoApiDTO dto, Participacao participacao) {
        // Atualiza Pessoa
        Pessoa pessoa = findOrCreatePessoa(dto.getPessoa());
        participacao.setPessoa(pessoa);

        // Atualiza Personagem
        Personagem personagem = findOrCreatePersonagem(dto.getPersonagem());
        participacao.setPersonagem(personagem);
    }

    /**
     * Busca ou cria Pessoa a partir de PessoaApiDTO
     */
    public Pessoa findOrCreatePessoa(PessoaApiDTO pessoaApiDTO) {
        if (pessoaApiDTO == null || pessoaApiDTO.getExternoId() == null) {
            throw new IllegalArgumentException("Dados de pessoa da API inválidos");
        }

        return pessoaRepository.findByExternoId(pessoaApiDTO.getExternoId())
                .map(pessoaExistente -> {
                    // Atualiza dados da pessoa existente
                    pessoaExistente.setNome(pessoaApiDTO.getNome());
                    pessoaExistente.setDataNascimento(pessoaApiDTO.getDataNascimento());
                    pessoaExistente.setImagem(pessoaApiDTO.getImagem());
                    return pessoaRepository.save(pessoaExistente);
                })
                .orElseGet(() -> {
                    // Cria nova pessoa
                    Pessoa novaPessoa = new Pessoa();
                    novaPessoa.setExternoId(pessoaApiDTO.getExternoId());
                    novaPessoa.setNome(pessoaApiDTO.getNome());
                    novaPessoa.setDataNascimento(pessoaApiDTO.getDataNascimento());
                    novaPessoa.setImagem(pessoaApiDTO.getImagem());
                    return pessoaRepository.save(novaPessoa);
                });
    }

    /**
     * Busca ou cria Personagem a partir de PersonagemApiDTO
     */
    public Personagem findOrCreatePersonagem(PersonagemApiDTO personagemApiDTO) {
        if (personagemApiDTO == null || personagemApiDTO.getExternoId() == null) {
            throw new IllegalArgumentException("Dados de personagem da API inválidos");
        }

        return personagemRepository.findByExternoId(personagemApiDTO.getExternoId())
                .map(personagemExistente -> {
                    // Atualiza dados do personagem existente
                    personagemExistente.setNomePersonagem(personagemApiDTO.getNomePersonagem());
                    personagemExistente.setImagem(personagemApiDTO.getImagem());
                    return personagemRepository.save(personagemExistente);
                })
                .orElseGet(() -> {
                    // Cria novo personagem
                    Personagem novoPersonagem = new Personagem();
                    novoPersonagem.setExternoId(personagemApiDTO.getExternoId());
                    novoPersonagem.setNomePersonagem(personagemApiDTO.getNomePersonagem());
                    novoPersonagem.setImagem(personagemApiDTO.getImagem());
                    return personagemRepository.save(novoPersonagem);
                });
    }

    /**
     * Verifica se já existe uma Participacao com mesma Pessoa, Serie e Personagem
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

        return participacaoRepository.findByPessoaAndSerieAndPersonagem_NomePersonagem(
                pessoaOpt.get(),
                serie,
                dto.getPersonagem().getNomePersonagem()).isPresent();
    }

    /**
     * Busca uma Participacao existente por Pessoa, Serie e nome do Personagem
     */
    public Optional<Participacao> findExisting(ParticipacaoApiDTO dto, Serie serie) {
        if (dto.getPessoa() == null || dto.getPessoa().getExternoId() == null ||
                dto.getPersonagem() == null || dto.getPersonagem().getNomePersonagem() == null) {
            return Optional.empty();
        }

        return pessoaRepository.findByExternoId(dto.getPessoa().getExternoId())
                .flatMap(pessoa -> participacaoRepository.findByPessoaAndSerieAndPersonagem_NomePersonagem(
                        pessoa, serie, dto.getPersonagem().getNomePersonagem()));
    }

}
