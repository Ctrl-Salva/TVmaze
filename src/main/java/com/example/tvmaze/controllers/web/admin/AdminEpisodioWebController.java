package com.example.tvmaze.controllers.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.tvmaze.dtos.episodio.EpisodioCriacaoDTO;
import com.example.tvmaze.dtos.episodio.EpisodioRespostaDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.services.EpisodioService;
import com.example.tvmaze.services.SerieService;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/episodios")
public class AdminEpisodioWebController {

    @Autowired
    private EpisodioService episodioService;

    @Autowired
    private SerieService serieService;

    @GetMapping
    public String listarEpisodios(@RequestParam(required = false) Integer serieId,
            @RequestParam(required = false) Integer temporada,
            @RequestParam(required = false) String busca,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(required = false) String nomeSerieBusca,
            Model model) {
        List<SerieRespostaDTO> series = serieService.listarSeries();
        model.addAttribute("series", series);

        if (serieId != null) {
            List<EpisodioRespostaDTO> episodios = episodioService.listarEpisodiosPorSerie(serieId);

            // Filtrar por temporada se o parâmetro foi fornecido
            if (temporada != null) {
                episodios = episodios.stream()
                        .filter(ep -> ep.getTemporada().equals(temporada))
                        .toList();
            }

            // Filtrar por busca se o parâmetro foi fornecido
            if (busca != null && !busca.trim().isEmpty()) {
                String buscaLower = busca.toLowerCase().trim();
                episodios = episodios.stream()
                        .filter(ep -> ep.getNome().toLowerCase().contains(buscaLower))
                        .toList();
            }

            // Ordenar por temporada e número do episódio
            episodios = episodios.stream()
                    .sorted(Comparator.comparing(EpisodioRespostaDTO::getTemporada)
                            .thenComparing(EpisodioRespostaDTO::getNumero))
                    .toList();

            // Calcular paginação
            int totalEpisodios = episodios.size();
            int totalPages = (int) Math.ceil((double) totalEpisodios / size);

            // Garantir que a página não exceda o total
            if (page >= totalPages && totalPages > 0) {
                page = totalPages - 1;
            }
            if (page < 0) {
                page = 0;
            }

            // Paginar a lista
            int start = page * size;
            int end = Math.min(start + size, totalEpisodios);
            List<EpisodioRespostaDTO> episodiosPaginados = episodios.subList(start, end);

            model.addAttribute("episodios", episodiosPaginados);
            model.addAttribute("serieSelecionada", serieId);
            model.addAttribute("temporada", temporada);
            model.addAttribute("busca", busca);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalEpisodios", totalEpisodios);
            model.addAttribute("pageSize", size);
            model.addAttribute("nomeSerieBusca", nomeSerieBusca);
        }

        return "admin/episodios/listar";
    }

    @GetMapping("/novo")
    public String novoEpisodio(@RequestParam(required = false) Integer serieId, Model model) {
        EpisodioCriacaoDTO episodio = new EpisodioCriacaoDTO();
        model.addAttribute("episodio", episodio);
        model.addAttribute("series", serieService.listarSeries());
        model.addAttribute("serieSelecionada", serieId);
        return "admin/episodios/form";
    }

    @PostMapping("/salvar")
    public String salvarEpisodio(@ModelAttribute EpisodioCriacaoDTO dto,
            @RequestParam("serieId") Integer serieId) {
        episodioService.criarEpisodio(serieId, dto);
        return "redirect:/admin/episodios?serieId=" + serieId;
    }

    @GetMapping("/editar/{id}")
    public String editarEpisodio(@PathVariable Integer id, Model model) {
        EpisodioRespostaDTO episodioResposta = episodioService.buscarPorId(id);

        // Converter EpisodioRespostaDTO para EpisodioCriacaoDTO para edição
        EpisodioCriacaoDTO episodio = new EpisodioCriacaoDTO();
        episodio.setNome(episodioResposta.getNome());
        episodio.setTemporada(episodioResposta.getTemporada());
        episodio.setNumero(episodioResposta.getNumero());
        episodio.setDataExibicao(episodioResposta.getDataExibicao());
        episodio.setDuracao(episodioResposta.getDuracao());
        
        // ✅ COPIAR AS IMAGENS - ESSENCIAL PARA O PREVIEW
        if (episodioResposta.getImagem() != null) {
            episodio.setImagem(episodioResposta.getImagem());
        }

        model.addAttribute("episodio", episodio);
        model.addAttribute("episodioId", episodioResposta.getEpisodioId());
        model.addAttribute("series", serieService.listarSeries());
        model.addAttribute("serieSelecionada", episodioResposta.getSerieId());

        return "admin/episodios/form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarEpisodio(@PathVariable Integer id,
            @ModelAttribute EpisodioCriacaoDTO dto,
            @RequestParam("serieId") Integer serieId) {
        episodioService.atualizarEpisodio(id, dto);
        return "redirect:/admin/episodios?serieId=" + serieId;
    }

    @GetMapping("/excluir/{id}")
    public String excluirEpisodio(@PathVariable Integer id,
            @RequestParam(required = false) Integer serieId) {
        episodioService.deletarEpisodio(id);
        return serieId != null
                ? "redirect:/admin/episodios?serieId=" + serieId
                : "redirect:/admin/episodios";
    }
}