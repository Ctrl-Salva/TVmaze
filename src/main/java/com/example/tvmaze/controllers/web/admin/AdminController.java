package com.example.tvmaze.controllers.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tvmaze.models.Serie;
import com.example.tvmaze.repositories.EpisodioRepository;
import com.example.tvmaze.repositories.PersonagemRepository;
import com.example.tvmaze.repositories.PessoaRepository;
import com.example.tvmaze.repositories.SerieRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private EpisodioRepository episodioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PersonagemRepository personagemRepository;

    @GetMapping
public String index(Model model) {
    // Contadores principais
    long totalSeries = serieRepository.count();
    long totalEpisodios = episodioRepository.count();
    long totalPessoas = pessoaRepository.count();
    long totalPersonagens = personagemRepository.count();
    
    // Últimas séries adicionadas (top 5)
    List<Serie> ultimasSeries = serieRepository.findTop5ByOrderBySerieIdDesc();
    
    model.addAttribute("totalSeries", totalSeries);
    model.addAttribute("totalEpisodios", totalEpisodios);
    model.addAttribute("totalPessoas", totalPessoas);
    model.addAttribute("totalPersonagens", totalPersonagens);
    model.addAttribute("ultimasSeries", ultimasSeries);
    
    return "admin/index";
}

}
