package com.example.tvmaze.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tvmaze.models.Episodio;

public interface EpisodioRepository extends JpaRepository<Episodio, Integer>{
    /**
     * Busca todos os episódios de uma série
     */
    List<Episodio> findBySerieSerieId(Integer serieId);

    Optional<Episodio> findByExternoId(Integer externoId);

    /**
     * Busca episódios de uma temporada específica
     */
    List<Episodio> findBySerieSerieIdAndTemporada(Integer serieId, Integer temporada);

    /**
     * Busca um episódio específico (série, temporada, número)
     */
    @Query("SELECT e FROM Episodio e WHERE e.serie.serieId = :serieId " +
           "AND e.temporada = :temporada AND e.numero = :numero")
    Episodio findBySerieTemporadaNumero(@Param("serieId") Integer serieId,
                                        @Param("temporada") Integer temporada,
                                        @Param("numero") Integer numero);

    /**
     * Conta episódios de uma série
     */
    long countBySerieSerieId(Integer serieId);

    /**
     * Busca todas as temporadas de uma série (distintas)
     */
    @Query("SELECT DISTINCT e.temporada FROM Episodio e WHERE e.serie.serieId = :serieId ORDER BY e.temporada")
    List<Integer> findTemporadasBySerieId(@Param("serieId") Integer serieId);
}

