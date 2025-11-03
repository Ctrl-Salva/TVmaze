package com.example.tvmaze.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.models.Serie;

public interface SerieRepository extends JpaRepository<Serie, Integer> {
    Optional<Serie> findByExternoId(Integer externoId);

    // Busca por nome (ignorando maiúsculas/minúsculas)
    Optional<Serie> findByNomeIgnoreCase(String nome);

    List<Serie> findAllByOrderBySerieIdDesc();
}