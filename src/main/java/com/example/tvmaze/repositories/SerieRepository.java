package com.example.tvmaze.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.models.Serie;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

}