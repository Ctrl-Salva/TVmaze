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
    public String listar(Model model) {
        List<SerieRespostaDTO> series = serieService.listarSeriesOrdenadas(); 
       
        model.addAttribute("series", series);
        return "series/lista"; // templates/series/lista.html
    }
}
