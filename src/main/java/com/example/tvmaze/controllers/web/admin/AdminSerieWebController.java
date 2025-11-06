package com.example.tvmaze.controllers.web.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tvmaze.dtos.genero.GeneroRespostaDTO;
import com.example.tvmaze.dtos.serie.SerieCriacaoDTO;
import com.example.tvmaze.dtos.serie.SerieRespostaDTO;
import com.example.tvmaze.models.Serie;
import com.example.tvmaze.services.GeneroService;
import com.example.tvmaze.services.SerieService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/admin/series")
public class AdminSerieWebController {

    @Autowired
    private SerieService serieService;

    @Autowired
    private GeneroService generoService;

    @GetMapping("/criar")
    public String criarSerie(Model model) {
        model.addAttribute("serie", new SerieCriacaoDTO());

        // Buscar gêneros e linguagens disponíveis
        List<GeneroRespostaDTO> generos = generoService.listarGeneros();
        List<String> linguagens = serieService.listarTodasLinguagens();

        model.addAttribute("generos", generos);
        model.addAttribute("linguagens", linguagens);

        return "admin/series/form";
    }

    @PostMapping("/salvar")
    public String salvarSerie(
            @ModelAttribute SerieCriacaoDTO dto,
            @RequestParam(required = false) List<String> generos,
            RedirectAttributes redirectAttributes) {
        try {
            // Inicializar o Set de gêneros se for null
            if (dto.getGeneros() == null) {
                dto.setGeneros(new HashSet<>());
            }

            // Processar gêneros recebidos do formulário
            if (generos != null && !generos.isEmpty()) {
                // Limpar gêneros vazios e trimmar
                Set<String> generosProcessados = new HashSet<>();
                for (String genero : generos) {
                    if (genero != null && !genero.trim().isEmpty()) {
                        generosProcessados.add(genero.trim());
                    }
                }
                dto.setGeneros(generosProcessados);
            }

            // Salvar a série (o mapper e service vão criar os gêneros se necessário)
            serieService.salvarSerie(dto);

            redirectAttributes.addFlashAttribute("mensagem", "Série salva com sucesso!");
            return "redirect:/admin/series";

        } catch (Exception e) {
            e.printStackTrace(); // Para debug
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar série: " + e.getMessage());
            return "redirect:/admin/series/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarSerie(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Buscar série existente
            SerieRespostaDTO serieExistente = serieService.buscarPorId(id);

            // Converter para DTO de criação
            SerieCriacaoDTO serie = new SerieCriacaoDTO();
            serie.setNome(serieExistente.getNome());
            serie.setLinguagem(serieExistente.getLinguagem());
            serie.setSinopse(serieExistente.getSinopse());
            serie.setNota(serieExistente.getNota());
            serie.setDataEstreia(serieExistente.getDataEstreia());
            serie.setDataTermino(serieExistente.getDataTermino());
            serie.setGeneros(serieExistente.getGeneros());
            serie.setImagem(serieExistente.getImagem());

            model.addAttribute("serie", serie);
            model.addAttribute("serieId", id);

            // Buscar gêneros e linguagens disponíveis
            List<GeneroRespostaDTO> generos = generoService.listarGeneros();
            List<String> linguagens = serieService.listarTodasLinguagens();

            model.addAttribute("generos", generos);
            model.addAttribute("linguagens", linguagens);

            // Gêneros já selecionados (para pré-preencher as tags)
            if (serieExistente.getGeneros() != null) {
                model.addAttribute("generosSelecionados",
                        String.join(",", serieExistente.getGeneros()));
            } else {
                model.addAttribute("generosSelecionados", "");
            }

            return "admin/series/form";

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Série não encontrada");
            return "redirect:/admin/series";
        }
    }

    // NOVO: Atualizar série
    @PostMapping("/atualizar/{id}")
    public String atualizarSerie(
            @PathVariable Integer id,
            @ModelAttribute SerieCriacaoDTO dto,
            @RequestParam(required = false) List<String> generos,
            RedirectAttributes redirectAttributes) {
        try {
            // Inicializar o Set de gêneros se for null
            if (dto.getGeneros() == null) {
                dto.setGeneros(new HashSet<>());
            }

            // Processar gêneros recebidos do formulário
            if (generos != null && !generos.isEmpty()) {
                Set<String> generosProcessados = new HashSet<>();
                for (String genero : generos) {
                    if (genero != null && !genero.trim().isEmpty()) {
                        generosProcessados.add(genero.trim());
                    }
                }
                dto.setGeneros(generosProcessados);
            }

            // Atualizar a série
            serieService.atualizarSerie(id, dto);

            redirectAttributes.addFlashAttribute("mensagem", "Série atualizada com sucesso!");
            return "redirect:/admin/series";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar série: " + e.getMessage());
            return "redirect:/admin/series/form/" + id;
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluirSerie(@PathVariable Integer id) {
        serieService.deletarSerie(id);
        return "redirect:/admin/series";
    }

    @GetMapping
    public String listarSeries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(required = false) Integer generoId,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String linguagem,
            @RequestParam(required = false) Double notaMinima,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String ordenar,
            @RequestParam(defaultValue = "desc") String direcao,
            Model model) {

        // 👉 Se o usuário não passou "ordenar", usamos o ID da série como padrão
        String ordenarCampo = (ordenar == null || ordenar.isBlank()) ? "serieId" : ordenar;

        // 👉 Ordenação ascendente ou descendente
        Sort.Direction direction = direcao.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, ordenarCampo));

        // 👉 Chama o serviço com os filtros e ordenação
        Page<SerieRespostaDTO> seriesPage = serieService.listarSeriesComFiltros(
                nome, generoId, linguagem, notaMinima, ano, pageable);

        // Dados auxiliares para filtros
        List<GeneroRespostaDTO> generosDisponiveis = generoService.listarGeneros();
        List<String> linguagensDisponiveis = serieService.listarTodasLinguagens();
        List<Integer> anosDisponiveis = serieService.listarTodosAnos();

        // Adiciona ao modelo
        model.addAttribute("seriesPage", seriesPage);
        model.addAttribute("series", seriesPage.getContent());
        model.addAttribute("generosDisponiveis", generosDisponiveis);
        model.addAttribute("linguagensDisponiveis", linguagensDisponiveis);
        model.addAttribute("anosDisponiveis", anosDisponiveis);

        model.addAttribute("nomeSelecionado", nome);
        model.addAttribute("generoIdSelecionado", generoId);
        model.addAttribute("linguagemSelecionada", linguagem);
        model.addAttribute("notaMinimaSelecionada", notaMinima);
        model.addAttribute("anoSelecionado", ano);
        model.addAttribute("ordenarSelecionado", ordenarCampo);
        model.addAttribute("direcaoSelecionada", direcao);

        return "admin/series/listar";
    }
}
