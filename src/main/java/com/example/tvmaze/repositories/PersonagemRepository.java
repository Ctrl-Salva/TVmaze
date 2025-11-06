package com.example.tvmaze.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.models.Personagem;

public interface PersonagemRepository extends JpaRepository<Personagem, Integer>{
 
}
