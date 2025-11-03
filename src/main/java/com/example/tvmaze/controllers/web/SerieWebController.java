package com.example.tvmaze.controllers.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.services.SerieService;

@Controller
@RequestMapping("/series")
public class SerieWebController {
    
    @Autowired
    SerieService serieService;

    @GetMapping
    public String series(Model model) {
        List<SerieRespostaDTO> seriesRecentes = serieService.listarSeriesRecentes();
       
        model.addAttribute("seriesRecentes", seriesRecentes);
        return "series/series"; // templates/series/lista.html
    }
}
