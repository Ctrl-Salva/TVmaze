package com.example.tvmaze.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.serie.SerieCriacaoDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.mappers.SerieMapper;
import com.example.tvmaze.models.Genero;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.EpisodioRepository;
import com.example.tvmaze.repositories.ParticipacaoRepository;
import com.example.tvmaze.repositories.SerieRepository;
import com.example.tvmaze.utils.Quicksort;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;

@Service
public class SerieService {
    
    @Autowired
    SerieRepository serieRepository;
    
    @Autowired
    SerieMapper serieMapper;
    
    @Autowired
    GeneroService generoService;

    @Autowired
    EpisodioRepository episodioRepository;

    @Autowired
    ParticipacaoRepository participacaoRepository;
   
    public List<SerieRespostaDTO> listarSeries() {
        return serieRepository.findAll().stream()
                .map(serieMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public List<SerieRespostaDTO> listarSeriesOrdenadas() {
        List<Serie> series = serieRepository.findAll();
        Quicksort.quickSort(series, 0, series.size() - 1);
        
        return series.stream()
                .map(serieMapper::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public List<SerieRespostaDTO> listarSeriesRecentes() {
        return serieRepository.findAllByOrderBySerieIdDesc()
            .stream()
            .map(serieMapper::toRespostaDTO)
            .collect(Collectors.toList());
    }

    public SerieRespostaDTO buscarPorId(Integer id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));
                
        return serieMapper.toRespostaDTO(serie);
    }

    public Serie salvarSerie(SerieCriacaoDTO dto) {
        Serie novaSerie = new Serie();
        serieMapper.toEntity(dto, novaSerie);
        return serieRepository.save(novaSerie);
    }

    public Serie atualizarSerie(Integer id, SerieCriacaoDTO dto) {
        Serie existente = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));
        serieMapper.toEntity(dto, existente);
        return serieRepository.save(existente);
    }

    @Transactional 
    public void deletarSerie(Integer id) {
        if (!serieRepository.existsById(id)) {
            throw new RuntimeException("Série não encontrada com ID: " + id);
        }
        participacaoRepository.deleteBySerie_SerieId(id);
        episodioRepository.deleteBySerie_SerieId(id);

        serieRepository.deleteById(id);
    }

    // ========== NOVOS MÉTODOS PARA PAGINAÇÃO E FILTROS ==========
    
    public Page<SerieRespostaDTO> listarSeriesComFiltros(
           String nome, Integer generoId, String linguagem, Double notaMinima, Integer ano, Pageable pageable) {
        
        Specification<Serie> spec = Specification.where(null);


         // Filtro por nome (busca parcial, case insensitive)
        if (nome != null && !nome.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        
        // Filtro por gênero (usando o ID do gênero)
        if (generoId != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Serie, Genero> generoJoin = root.join("generos", JoinType.INNER);
                return cb.equal(generoJoin.get("generoId"), generoId);
            });
        }
        
        // Filtro por linguagem
        if (linguagem != null && !linguagem.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("linguagem"), linguagem));
        }
        
        // Filtro por nota mínima
        if (notaMinima != null) {
            spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("nota"), notaMinima));
        }
        
        // Filtro por ano de estreia
        if (ano != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(cb.function("YEAR", Integer.class, root.get("dataEstreia")), ano));
        }
        
        Page<Serie> seriesPage = serieRepository.findAll(spec, pageable);
        return seriesPage.map(serieMapper::toRespostaDTO);
    }
    
    public List<String> listarTodasLinguagens() {
        return serieRepository.findAllLinguagens();
    }
    
    public List<Integer> listarTodosAnos() {
        return serieRepository.findAllAnos();
    }
}