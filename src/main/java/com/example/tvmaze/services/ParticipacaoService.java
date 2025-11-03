package com.example.tvmaze.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.mappers.ParticipacaoMapper;
import com.example.tvmaze.models.Participacao;
import com.example.tvmaze.repositories.ParticipacaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

      public List<ParticipacaoRespostaDTO> listarParticipacoesPorSerieId(Integer serieId) {
        // Supondo que você tenha um método no seu repositório para buscar por serie.id
        // Ou que você precise carregar a entidade Serie e depois filtrar
        return participacaoRepository.findBySerie_SerieId(serieId) // Você precisará criar este método no seu repositório
                .stream()
                .map(participacaoMapper::toRespostaDTO) // Converte a entidade para DTO
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
}