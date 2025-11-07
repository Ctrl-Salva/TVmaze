package com.example.tvmaze.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.pessoa.PessoaCriacaoDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.mappers.PessoaMapper;
import com.example.tvmaze.models.Pessoa;
import com.example.tvmaze.repositories.PessoaRepository;

@Service
public class PessoaService {
    
    @Autowired
    PessoaRepository pessoaRepository;
    
    @Autowired
    PessoaMapper pessoaMapper;

    public List<PessoaRespostaDTO> listarPessoas() {
        return pessoaRepository.findAll().stream()
                .map(pessoaMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public List<PessoaRespostaDTO> listarPessoasRecentes() {
        return pessoaRepository.findAllByOrderByPessoaIdDesc()
            .stream()
            .map(pessoaMapper::toRespostaDTO)
            .collect(Collectors.toList());
    }

    public PessoaRespostaDTO listarPorId(Integer id){
        Pessoa pessoa = pessoaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
        return pessoaMapper.toRespostaDTO(pessoa);
    }

    public Pessoa criarPessoa(PessoaCriacaoDTO dto){
        Pessoa novaPessoa = new Pessoa();
        pessoaMapper.toEntity(dto, novaPessoa);
        return pessoaRepository.save(novaPessoa);
    }

    public Pessoa atualizarPessoa(Integer id, PessoaCriacaoDTO dto) {
        Pessoa existente = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
        pessoaMapper.toEntity(dto, existente);
        return pessoaRepository.save(existente);
    }

    public void deletarPessoa(Integer id) {
        if (!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Pessoa não encontrada com ID: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    // ========== NOVOS MÉTODOS PARA PAGINAÇÃO E FILTROS ==========
    
    public Page<PessoaRespostaDTO> listarPessoasComFiltros(
            String nome, Integer decadaNascimento, String ordenar, String direcao, Pageable pageable) {
        
        Specification<Pessoa> spec = Specification.where(null);
        
        // Filtro por nome (busca parcial, case insensitive)
        if (nome != null && !nome.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        
        // Filtro por década de nascimento
        if (decadaNascimento != null) {
            int anoInicio = decadaNascimento;
            int anoFim = decadaNascimento + 9;
            spec = spec.and((root, query, cb) -> 
                cb.between(
                    cb.function("YEAR", Integer.class, root.get("dataNascimento")),
                    anoInicio,
                    anoFim
                ));
        }
        
        Page<Pessoa> pessoasPage = pessoaRepository.findAll(spec, pageable);
        return pessoasPage.map(pessoaMapper::toRespostaDTO);
    }
    
    public List<Integer> listarTodasDecadas() {
        return pessoaRepository.findAllDecadas();
    }
}