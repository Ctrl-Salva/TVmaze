package com.example.tvmaze.controllers.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.services.SerieService;

@Controller
public class HomeController {

  @Autowired
  private SerieService serieService;

  @GetMapping("/")
  public String home(Model model) {
    List<SerieRespostaDTO> seriesPopulares = serieService.listarSeriesOrdenadas();
    List<SerieRespostaDTO> seriesRecentes = serieService.listarSeriesRecentes();

    if (seriesPopulares.size() > 5) {
      seriesPopulares = seriesPopulares.subList(0, 5);
    }

    model.addAttribute("seriesPopulares", seriesPopulares);
    model.addAttribute("seriesRecentes", seriesRecentes);
    return "home/index";
  }
}
