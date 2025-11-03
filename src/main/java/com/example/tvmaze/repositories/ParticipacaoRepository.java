package com.example.tvmaze.repositories;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.tvmaze.models.Participacao;
import com.example.tvmaze.models.Pessoa;
import com.example.tvmaze.models.Serie;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer>, JpaSpecificationExecutor<Participacao> {
    
    List<Participacao> findBySerie_SerieId(Integer serieId);
    List<Participacao> findByPessoa_PessoaId(Integer pessoaId);
    
    // Buscar todas as séries que têm participações
    @Query("SELECT DISTINCT p.serie.serieId, p.serie.nome FROM Participacao p ORDER BY p.serie.nome")
    List<Object[]> findAllSeriesComParticipacoes();
    
    // Buscar todas as pessoas que têm participações
    @Query("SELECT DISTINCT p.pessoa.pessoaId, p.pessoa.nome FROM Participacao p ORDER BY p.pessoa.nome")
    List<Object[]> findAllPessoasComParticipacoes();
    
    // Buscar participações ordenadas por ID descendente (mais recentes)
    List<Participacao> findAllByOrderByParticipacaoIdDesc();
    
    Optional<Participacao> findByPessoaAndSerieAndPersonagem_NomePersonagem(
        Pessoa pessoa,
        Serie serie,
        String nomePersonagem
    );

}