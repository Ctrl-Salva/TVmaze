package com.example.tvmaze.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.tvmaze.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>, JpaSpecificationExecutor<Pessoa> {
    
    Optional<Pessoa> findByExternoId(Integer externoId);
    Optional<Pessoa> findByNomeIgnoreCase(String nome);
    
    // Buscar todas as décadas de nascimento distintas
    @Query("SELECT DISTINCT YEAR(p.dataNascimento) / 10 * 10 FROM Pessoa p WHERE p.dataNascimento IS NOT NULL ORDER BY YEAR(p.dataNascimento) / 10 * 10 DESC")
    List<Integer> findAllDecadas();
    
    // Buscar pessoas ordenadas por ID descendente (mais recentes)
    List<Pessoa> findAllByOrderByPessoaIdDesc();

  
}