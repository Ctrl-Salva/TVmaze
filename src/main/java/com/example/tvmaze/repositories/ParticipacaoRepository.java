package com.example.tvmaze.repositories;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tvmaze.models.Participacao;
import com.example.tvmaze.models.Personagem;
import com.example.tvmaze.models.Pessoa;
import com.example.tvmaze.models.Serie;

@Repository
public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer>, 
                                                JpaSpecificationExecutor<Participacao> {
    
    List<Participacao> findAllByOrderByParticipacaoIdDesc();
    
    List<Participacao> findBySerie_SerieId(Integer serieId);
    
    List<Participacao> findByPessoa_PessoaId(Integer pessoaId);
    
    Optional<Participacao> findByPessoaAndSerieAndPersonagem(Pessoa pessoa, Serie serie, Personagem personagem);
    
    Optional<Participacao> findByPessoaAndSerieAndPersonagem_NomePersonagem(Pessoa pessoa, Serie serie, String nomePersonagem);
    
    boolean existsByPessoaAndSerieAndPersonagem(Pessoa pessoa, Serie serie, Personagem personagem);
    
    void deleteBySerie_SerieId(Integer serieId);
    
    @Query("SELECT DISTINCT s.serieId, s.nome FROM Participacao p JOIN p.serie s ORDER BY s.nome")
    List<Object[]> findAllSeriesComParticipacoes();
    
    @Query("SELECT DISTINCT p.pessoaId, p.nome FROM Participacao part JOIN part.pessoa p ORDER BY p.nome")
    List<Object[]> findAllPessoasComParticipacoes();
}