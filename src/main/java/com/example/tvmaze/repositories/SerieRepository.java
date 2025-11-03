package com.example.tvmaze.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.example.tvmaze.models.Serie;

public interface SerieRepository extends JpaRepository<Serie, Integer>, JpaSpecificationExecutor<Serie> {
    Optional<Serie> findByExternoId(Integer externoId);
    Optional<Serie> findByNomeIgnoreCase(String nome);
    List<Serie> findAllByOrderBySerieIdDesc();
    
    // Buscar todas as linguagens distintas
    @Query("SELECT DISTINCT s.linguagem FROM Serie s WHERE s.linguagem IS NOT NULL ORDER BY s.linguagem")
    List<String> findAllLinguagens();
    
    // Buscar todos os anos de estreia distintos
    @Query("SELECT DISTINCT YEAR(s.dataEstreia) FROM Serie s WHERE s.dataEstreia IS NOT NULL ORDER BY YEAR(s.dataEstreia) DESC")
    List<Integer> findAllAnos();
}