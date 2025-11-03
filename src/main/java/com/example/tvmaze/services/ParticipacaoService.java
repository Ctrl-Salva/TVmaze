package com.example.tvmaze.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.mappers.ParticipacaoMapper;
import com.example.tvmaze.models.Participacao;
import com.example.tvmaze.repositories.ParticipacaoRepository;

@Service
public class ParticipacaoService {
    
    @Autowired
    private ParticipacaoRepository participacaoRepository;
    
    @Autowired
    private ParticipacaoMapper participacaoMapper; 

    public ParticipacaoRespostaDTO criarParticipacao(ParticipacaoCriacaoDTO dtoCriacao) {
        Participacao participacao = participacaoMapper.toEntity(dtoCriacao);
        Participacao participacaoSalva = participacaoRepository.save(participacao);
        return participacaoMapper.toRespostaDTO(participacaoSalva);
    }

    public List<ParticipacaoRespostaDTO> listarTodasParticipacoes() {
        return participacaoRepository.findAll().stream()
                .map(participacaoMapper::toRespostaDTO) 
                .collect(Collectors.toList());
    }
    
    public List<ParticipacaoRespostaDTO> listarParticipacoesRecentes() {
        return participacaoRepository.findAllByOrderByParticipacaoIdDesc()
                .stream()
                .map(participacaoMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public List<ParticipacaoRespostaDTO> listarParticipacoesPorSerieId(Integer serieId) {
        return participacaoRepository.findBySerie_SerieId(serieId)
                .stream()
                .map(participacaoMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }
    
    public List<ParticipacaoRespostaDTO> listarParticipacoesPorPessoaId(Integer pessoaId) {
        return participacaoRepository.findByPessoa_PessoaId(pessoaId)
                .stream()
                .map(participacaoMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public Optional<ParticipacaoRespostaDTO> buscarParticipacaoPorId(Integer id) {
        return participacaoRepository.findById(id)
                .map(participacaoMapper::toRespostaDTO); 
    }

    public ParticipacaoRespostaDTO atualizarParticipacao(Integer id, ParticipacaoCriacaoDTO dtoAtualizacao) {
        Participacao participacaoExistente = participacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada com ID: " + id));
        
        participacaoMapper.toEntity(dtoAtualizacao, participacaoExistente);
        Participacao participacaoAtualizada = participacaoRepository.save(participacaoExistente);
        return participacaoMapper.toRespostaDTO(participacaoAtualizada);
    }

    public void deletarParticipacao(Integer id) {
        if (!participacaoRepository.existsById(id)) {
            throw new RuntimeException("Participação não encontrada com ID: " + id);
        }
        participacaoRepository.deleteById(id);
    }

    // ========== NOVOS MÉTODOS PARA PAGINAÇÃO E FILTROS ==========
    
    public Page<ParticipacaoRespostaDTO> listarParticipacoesComFiltros(
            Integer serieId, Integer pessoaId, String nomePersonagem, Pageable pageable) {
        
        Specification<Participacao> spec = Specification.where(null);
        
        // Filtro por série
        if (serieId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("serie").get("serieId"), serieId));
        }
        
        // Filtro por pessoa
        if (pessoaId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("pessoa").get("pessoaId"), pessoaId));
        }
        
        // Filtro por nome do personagem (busca parcial)
        if (nomePersonagem != null && !nomePersonagem.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("personagem").get("nomePersonagem")), 
                        "%" + nomePersonagem.toLowerCase() + "%"));
        }
        
        Page<Participacao> participacoesPage = participacaoRepository.findAll(spec, pageable);
        return participacoesPage.map(participacaoMapper::toRespostaDTO);
    }
    
    public Map<Integer, String> listarSeriesComParticipacoes() {
        List<Object[]> resultado = participacaoRepository.findAllSeriesComParticipacoes();
        Map<Integer, String> series = new HashMap<>();
        for (Object[] obj : resultado) {
            series.put((Integer) obj[0], (String) obj[1]);
        }
        return series;
    }
    
    public Map<Integer, String> listarPessoasComParticipacoes() {
        List<Object[]> resultado = participacaoRepository.findAllPessoasComParticipacoes();
        Map<Integer, String> pessoas = new HashMap<>();
        for (Object[] obj : resultado) {
            pessoas.put((Integer) obj[0], (String) obj[1]);
        }
        return pessoas;
    }
}