package com.example.tvmaze.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.models.Participacao;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer> {
}
