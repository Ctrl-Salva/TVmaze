package com.example.tvmaze.controllers.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tvmaze.dtos.episodio.EpisodioRespostaDTO;
import com.example.tvmaze.dtos.genero.GeneroRespostaDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.services.EpisodioService;
import com.example.tvmaze.services.GeneroService;
import com.example.tvmaze.services.ParticipacaoService;
import com.example.tvmaze.services.SerieService;

@Controller
@RequestMapping("/series")
public class SerieWebController {
    
    @Autowired
    SerieService serieService;
    
    @Autowired
    GeneroService generoService;
    
    @Autowired
    ParticipacaoService participacaoService;
    
    @Autowired
    EpisodioService episodioService;

    @GetMapping
    public String series(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) Integer generoId,
            @RequestParam(required = false) String linguagem,
            @RequestParam(required = false) Double notaMinima,
            @RequestParam(required = false) Integer ano,
            @RequestParam(defaultValue = "dataEstreia") String ordenar,
            @RequestParam(defaultValue = "desc") String direcao,
            Model model) {
        
        Sort.Direction direction = direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, ordenar));
        
        Page<SerieRespostaDTO> seriesPage = serieService.listarSeriesComFiltros(
            generoId, linguagem, notaMinima, ano, pageable);
        
        List<GeneroRespostaDTO> generosDisponiveis = generoService.listarGeneros();
        List<String> linguagensDisponiveis = serieService.listarTodasLinguagens();
        List<Integer> anosDisponiveis = serieService.listarTodosAnos();
        
        model.addAttribute("seriesPage", seriesPage);
        model.addAttribute("generosDisponiveis", generosDisponiveis);
        model.addAttribute("linguagensDisponiveis", linguagensDisponiveis);
        model.addAttribute("anosDisponiveis", anosDisponiveis);
        
        model.addAttribute("generoIdSelecionado", generoId);
        model.addAttribute("linguagemSelecionada", linguagem);
        model.addAttribute("notaMinimaSelecionada", notaMinima);
        model.addAttribute("anoSelecionado", ano);
        model.addAttribute("ordenarSelecionado", ordenar);
        model.addAttribute("direcaoSelecionada", direcao);
        
        return "series/series";
    }
    
    @GetMapping("/{id}")
    public String detalheSerie(@PathVariable Integer id, Model model) {
        try {
            // Buscar dados da série
            SerieRespostaDTO serie = serieService.buscarPorId(id);
            
            // Buscar elenco da série
            List<ParticipacaoRespostaDTO> elenco = participacaoService.listarParticipacoesPorSerieId(id);
            
            // Buscar temporadas e episódios
            List<Integer> temporadas = episodioService.listarTemporadas(id);
            List<EpisodioRespostaDTO> todosEpisodios = episodioService.listarEpisodiosPorSerie(id);
            
            // Agrupar episódios por temporada
            Map<Integer, List<EpisodioRespostaDTO>> episodiosPorTemporada = todosEpisodios.stream()
                .collect(Collectors.groupingBy(EpisodioRespostaDTO::getTemporada));
            
            // Contar total de episódios
            long totalEpisodios = episodioService.contarEpisodiosPorSerie(id);
            
            // Adicionar ao modelo
            model.addAttribute("serie", serie);
            model.addAttribute("elenco", elenco);
            model.addAttribute("temporadas", temporadas);
            model.addAttribute("episodiosPorTemporada", episodiosPorTemporada);
            model.addAttribute("totalEpisodios", totalEpisodios);
            
            return "series/detalhe";
            
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Série não encontrada");
            return "error/404";
        }
    }
}