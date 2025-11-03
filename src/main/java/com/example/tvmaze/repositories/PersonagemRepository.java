package com.example.tvmaze.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.models.Personagem;

public interface PersonagemRepository extends JpaRepository<Personagem, Integer>{
    
}
