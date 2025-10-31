package com.example.tvmaze.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
    
}
