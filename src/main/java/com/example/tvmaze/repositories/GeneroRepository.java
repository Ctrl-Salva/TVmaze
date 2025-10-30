package com.example.tvmaze.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.models.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Integer>{
    Optional<Genero> findByNome(String nome);
}
