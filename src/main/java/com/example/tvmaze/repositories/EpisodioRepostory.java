package com.example.tvmaze.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.models.Episodio;

public interface EpisodioRepostory extends JpaRepository<Episodio, Integer>{
    
}
