package com.example.tvmaze.controllers.rest;

import java.util.List;

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

import com.example.tvmaze.dtos.personagem.PersonagemRequestDTO;
import com.example.tvmaze.dtos.personagem.PersonagemRespostaDTO;
import com.example.tvmaze.services.PersonagemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemRestController {
    
    private final PersonagemService service;

    public PersonagemRestController(PersonagemService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<PersonagemRespostaDTO>> listarTodos() {
        List<PersonagemRespostaDTO> personagens = service.listarTodos();
        return ResponseEntity.ok(personagens);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonagemRespostaDTO> buscarPorId(@PathVariable Integer id) {
        PersonagemRespostaDTO personagem = service.buscarPorId(id);
        return ResponseEntity.ok(personagem);
    }
    
    @GetMapping("/externo/{externoId}")
    public ResponseEntity<PersonagemRespostaDTO> buscarPorExternoId(@PathVariable Integer externoId) {
        PersonagemRespostaDTO personagem = service.buscarPorExternoId(externoId);
        return ResponseEntity.ok(personagem);
    }
    
    @PostMapping
    public ResponseEntity<PersonagemRespostaDTO> criar(@Valid @RequestBody PersonagemRequestDTO dto) {
        PersonagemRespostaDTO personagem = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(personagem);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PersonagemRespostaDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PersonagemRequestDTO dto) {
        PersonagemRespostaDTO personagem = service.atualizar(id, dto);
        return ResponseEntity.ok(personagem);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
