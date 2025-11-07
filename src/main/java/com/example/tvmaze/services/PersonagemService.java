package com.example.tvmaze.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.personagem.PersonagemRequestDTO;
import com.example.tvmaze.dtos.personagem.PersonagemRespostaDTO;
import com.example.tvmaze.mappers.PersonagemMapper;
import com.example.tvmaze.models.Personagem;
import com.example.tvmaze.repositories.PersonagemRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonagemService {

    private final PersonagemRepository repository;
    private final PersonagemMapper mapper;

    public PersonagemService(PersonagemRepository repository, PersonagemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public List<PersonagemRespostaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonagemRespostaDTO buscarPorId(Integer id) {
        Personagem personagem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + id));
                
        return mapper.toRespostaDTO(personagem);
    }

    @Transactional
    public PersonagemRespostaDTO buscarPorExternoId(Integer externoId) {
        Personagem personagem = repository.findByExternoId(externoId)
                .orElseThrow(() -> new RuntimeException(
                        "Personagem não encontrado com ID externo: " + externoId));

        return mapper.toRespostaDTO(personagem);
    }

    @Transactional
    public PersonagemRespostaDTO criar(PersonagemRequestDTO dto) {

        Personagem personagem = mapper.toEntity(dto);
        Personagem salvo = repository.save(personagem);
        return mapper.toRespostaDTO(salvo);
    }

    @Transactional
    public PersonagemRespostaDTO atualizar(Integer id, PersonagemRequestDTO dto) {
        Personagem personagem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + id));


        personagem.setNomePersonagem(dto.getNomePersonagem());
        personagem.setImagem(dto.getImagem());

        Personagem atualizado = repository.save(personagem);
        return mapper.toRespostaDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Personagem não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}
