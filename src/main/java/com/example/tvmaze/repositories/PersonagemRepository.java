package com.example.tvmaze.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tvmaze.models.Personagem;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Integer>{
 
    Optional<Personagem> findByExternoId(Integer externoId);
    
    boolean existsByExternoId(Integer externoId);
}
