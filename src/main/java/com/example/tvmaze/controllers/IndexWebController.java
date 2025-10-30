package com.example.tvmaze.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tvmaze.services.SerieService;


@Controller
public class IndexWebController {
    
    @Autowired
    private SerieService serieService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("series", serieService.listarSeries());
        return "index";
    }
}
