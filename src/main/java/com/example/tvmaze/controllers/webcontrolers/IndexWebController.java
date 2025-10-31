package com.example.tvmaze.controllers.webcontrolers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tvmaze.dto.resposta.SerieRespostaDTO;
import com.example.tvmaze.services.SerieService;

@Controller
public class IndexWebController {

    @Autowired
    private SerieService serieService;

    @GetMapping("/")
    public String index(Model model) {
        List<SerieRespostaDTO> series = serieService.listarSeries();
        /*
         * 
         if (series.size() > 5) {
             series = series.subList(0, 5);
         }
         * 
         */

        model.addAttribute("series", series);
        return "index";
    }
}
