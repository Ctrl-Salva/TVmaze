package com.example.tvmaze.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.SerieRepository;

@Service
public class SerieService {
    
    @Autowired
    private SerieRepository serieRepository;

    public List<Serie> listarSeries(){
        return serieRepository.findAll();
    }

    public Serie buscarPorId(Integer id){
        return serieRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));
    }

    public Serie salvarSerie(Serie serie){
        return serieRepository.save(serie);
    }

    public Serie atualizarSerie(Integer id, Serie serieAtualizada){
        Serie serie = buscarPorId(id);
        serie.setNome(serieAtualizada.getNome());
        serie.setLinguagem(serieAtualizada.getLinguagem());
        serie.setSinopse(serieAtualizada.getSinopse());
        serie.setNota(serieAtualizada.getNota());
        serie.setDataEstreia(serieAtualizada.getDataEstreia());
        serie.setDataTermino(serieAtualizada.getDataTermino());

        return serieRepository.save(serie);
    }

    public void deletarSerie(Integer id){
        serieRepository.deleteById(id);
    }
}
