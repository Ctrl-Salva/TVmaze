package com.example.tvmaze.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.services.ParticipacaoService;

@RestController
@RequestMapping("api/participacoes") // Define a URL base para este controller
public class ParticipacaoRestController {

    @Autowired
    private ParticipacaoService participacaoService; // Injeta o serviço de Participacao

    /**
     * Endpoint para criar uma nova participação.
     * Recebe um ParticipacaoCriacaoDTO no corpo da requisição.
     * Retorna a ParticipacaoRespostaDTO da participação criada.
     */
    @PostMapping
    public ResponseEntity<ParticipacaoRespostaDTO> criarParticipacao(@RequestBody ParticipacaoCriacaoDTO dtoCriacao) {
        try {
            ParticipacaoRespostaDTO novaParticipacao = participacaoService.criarParticipacao(dtoCriacao);
            return new ResponseEntity<>(novaParticipacao, HttpStatus.CREATED); // Retorna 201 Created
        } catch (RuntimeException e) {
            // Pode ser mais específico aqui, ex: Pessoa/Serie não encontrada
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request em caso de erro
        }
    }

    /**
     * Endpoint para listar todas as participações.
     * Retorna uma lista de ParticipacaoRespostaDTO.
     */
    @GetMapping
    public ResponseEntity<List<ParticipacaoRespostaDTO>> listarTodasParticipacoes() {
        List<ParticipacaoRespostaDTO> participacoes = participacaoService.listarTodasParticipacoes();
        return new ResponseEntity<>(participacoes, HttpStatus.OK); // Retorna 200 OK
    }

    /**
     * Endpoint para buscar uma participação por ID.
     * O ID é passado como parte da URL (path variable).
     * Retorna a ParticipacaoRespostaDTO se encontrada, ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipacaoRespostaDTO> buscarParticipacaoPorId(@PathVariable Integer id) {
        return participacaoService.buscarParticipacaoPorId(id)
                .map(participacao -> new ResponseEntity<>(participacao, HttpStatus.OK)) // Retorna 200 OK se encontrada
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Retorna 404 Not Found se não encontrada
    }

    @GetMapping("/serie/{serieId}") // Exemplo de URL: /api/participacoes/serie/1
    public ResponseEntity<List<ParticipacaoRespostaDTO>> listarParticipantesPorSerie(@PathVariable Integer serieId) {
        List<ParticipacaoRespostaDTO> participantes = participacaoService.listarParticipacoesPorSerieId(serieId);
        if (participantes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 se não houver participantes para a série
        }
        return new ResponseEntity<>(participantes, HttpStatus.OK); // Retorna 200 OK
    }

    /**
     * Endpoint para atualizar uma participação existente.
     * O ID da participação a ser atualizada é passado na URL.
     * Recebe um ParticipacaoCriacaoDTO com os novos dados no corpo da requisição.
     * Retorna a ParticipacaoRespostaDTO da participação atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParticipacaoRespostaDTO> atualizarParticipacao(@PathVariable Integer id, @RequestBody ParticipacaoCriacaoDTO dtoAtualizacao) {
        try {
            ParticipacaoRespostaDTO participacaoAtualizada = participacaoService.atualizarParticipacao(id, dtoAtualizacao);
            return new ResponseEntity<>(participacaoAtualizada, HttpStatus.OK); // Retorna 200 OK
        } catch (RuntimeException e) {
            // Se a participação não for encontrada, ou Pessoa/Serie não existirem
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        }
    }

    /**
     * Endpoint para deletar uma participação por ID.
     * Retorna 204 No Content se a deleção for bem-sucedida, ou 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarParticipacao(@PathVariable Integer id) {
        try {
            participacaoService.deletarParticipacao(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
        } catch (RuntimeException e) {
            // Se a participação não for encontrada
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        }
    }
}
