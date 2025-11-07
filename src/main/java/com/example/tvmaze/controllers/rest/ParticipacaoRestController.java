package com.example.tvmaze.controllers.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.services.ParticipacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/participacoes") // Define a URL base para este controller
public class ParticipacaoRestController {

   @Autowired
    private ParticipacaoService participacaoService;
    
    @GetMapping
    public ResponseEntity<List<ParticipacaoRespostaDTO>> listarTodas() {
        List<ParticipacaoRespostaDTO> participacoes = participacaoService.listarTodasParticipacoes();
        return ResponseEntity.ok(participacoes);
    }
    
    @GetMapping("/recentes")
    public ResponseEntity<List<ParticipacaoRespostaDTO>> listarRecentes() {
        List<ParticipacaoRespostaDTO> participacoes = participacaoService.listarParticipacoesRecentes();
        return ResponseEntity.ok(participacoes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ParticipacaoRespostaDTO> buscarPorId(@PathVariable Integer id) {
        return participacaoService.buscarParticipacaoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/serie/{serieId}")
    public ResponseEntity<List<ParticipacaoRespostaDTO>> listarPorSerie(@PathVariable Integer serieId) {
        List<ParticipacaoRespostaDTO> participacoes = participacaoService.listarParticipacoesPorSerieId(serieId);
        return ResponseEntity.ok(participacoes);
    }
    
    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<ParticipacaoRespostaDTO>> listarPorPessoa(@PathVariable Integer pessoaId) {
        List<ParticipacaoRespostaDTO> participacoes = participacaoService.listarParticipacoesPorPessoaId(pessoaId);
        return ResponseEntity.ok(participacoes);
    }
    
    @GetMapping("/filtros")
    public ResponseEntity<Page<ParticipacaoRespostaDTO>> listarComFiltros(
            @RequestParam(required = false) Integer serieId,
            @RequestParam(required = false) Integer pessoaId,
            @RequestParam(required = false) String nomePersonagem,
            Pageable pageable) {
        Page<ParticipacaoRespostaDTO> participacoes = participacaoService.listarParticipacoesComFiltros(
                serieId, pessoaId, nomePersonagem, pageable);
        return ResponseEntity.ok(participacoes);
    }
    
    @GetMapping("/series")
    public ResponseEntity<Map<Integer, String>> listarSeries() {
        Map<Integer, String> series = participacaoService.listarSeriesComParticipacoes();
        return ResponseEntity.ok(series);
    }
    
    @GetMapping("/pessoas")
    public ResponseEntity<Map<Integer, String>> listarPessoas() {
        Map<Integer, String> pessoas = participacaoService.listarPessoasComParticipacoes();
        return ResponseEntity.ok(pessoas);
    }
    
    @PostMapping
    public ResponseEntity<ParticipacaoRespostaDTO> criar(@Valid @RequestBody ParticipacaoCriacaoDTO dto) {
        ParticipacaoRespostaDTO participacao = participacaoService.criarParticipacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(participacao);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ParticipacaoRespostaDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ParticipacaoCriacaoDTO dto) {
        ParticipacaoRespostaDTO participacao = participacaoService.atualizarParticipacao(id, dto);
        return ResponseEntity.ok(participacao);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        participacaoService.deletarParticipacao(id);
        return ResponseEntity.noContent().build();
    }
}
