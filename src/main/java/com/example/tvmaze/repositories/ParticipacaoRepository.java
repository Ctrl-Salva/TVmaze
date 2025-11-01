package com.example.tvmaze.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tvmaze.entities.Participacao;
import com.example.tvmaze.entities.Pessoa;
import com.example.tvmaze.entities.Serie;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer> {

    Optional<Participacao> findByPessoaAndSerieAndPersonagem(Pessoa pessoa, Serie serie, String personagem);

   List<Participacao> findBySerie_SerieId(Integer serieId);
}
