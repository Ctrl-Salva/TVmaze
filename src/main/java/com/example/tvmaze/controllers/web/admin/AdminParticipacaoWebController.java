package com.example.tvmaze.controllers.web.admin;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.services.ParticipacaoService;
import com.example.tvmaze.services.PersonagemService;
import com.example.tvmaze.services.PessoaService;
import com.example.tvmaze.services.SerieService;

@Controller
@RequestMapping("/admin/participacao")
public class AdminParticipacaoWebController {

    @Autowired
    private ParticipacaoService participacaoService;

    @Autowired
    private SerieService serieService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PersonagemService personagemService;

    @GetMapping
    public String listarParticipacoes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String serieNome,
            @RequestParam(required = false) String pessoaNome,
            @RequestParam(required = false) String personagemNome,
            @RequestParam(required = false) String ordenar,
            @RequestParam(defaultValue = "desc") String direcao,
            Model model) {

        String ordenarCampo = (ordenar == null || ordenar.isBlank()) ? "participacaoId" : ordenar;
        
        Sort.Direction direction = direcao.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, ordenarCampo));

        Page<ParticipacaoRespostaDTO> participacaoPage = participacaoService.listarComFiltros(
                serieNome, pessoaNome, personagemNome, ordenarCampo, direcao, pageable);

        model.addAttribute("participacaoPage", participacaoPage);
        model.addAttribute("participacoes", participacaoPage.getContent());
        model.addAttribute("serieNomeSelecionado", serieNome);
        model.addAttribute("pessoaNomeSelecionado", pessoaNome);
        model.addAttribute("personagemNomeSelecionado", personagemNome);
        model.addAttribute("ordenarSelecionado", ordenarCampo);
        model.addAttribute("direcaoSelecionada", direcao);

        return "admin/participacao/listar";
    }

    @GetMapping("/criar")
    public String criarParticipacao(Model model) {
        model.addAttribute("participacao", new ParticipacaoCriacaoDTO());
        model.addAttribute("series", serieService.listarSeries());
        model.addAttribute("pessoas", pessoaService.listarPessoas());
        model.addAttribute("personagens", personagemService.listarTodos());
        return "admin/participacao/form";
    }

    @PostMapping("/salvar")
    public String salvarParticipacao(
            @ModelAttribute ParticipacaoCriacaoDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            participacaoService.criarParticipacao(dto);
            redirectAttributes.addFlashAttribute("mensagem", "Participação salva com sucesso!");
            return "redirect:/admin/participacao";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar participação: " + e.getMessage());
            return "redirect:/admin/participacao/criar";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarParticipacao(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ParticipacaoRespostaDTO participacaoExistente = participacaoService.buscarParticipacaoPorId(id)
                    .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
            
            ParticipacaoCriacaoDTO participacao = new ParticipacaoCriacaoDTO();
            participacao.setSerieId(participacaoExistente.getSerieId());
            participacao.setPessoaId(participacaoExistente.getPessoa().getPessoaId());
            participacao.setPersonagemId(participacaoExistente.getPersonagem().getPersonagemId());
            
            model.addAttribute("participacao", participacao);
            model.addAttribute("participacaoId", id);
            model.addAttribute("series", serieService.listarSeries());
            model.addAttribute("pessoas", pessoaService.listarPessoas());
            model.addAttribute("personagens", personagemService.listarTodos());
            
            return "admin/participacao/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Participação não encontrada");
            return "redirect:/admin/participacao";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarParticipacao(
            @PathVariable Integer id,
            @ModelAttribute ParticipacaoCriacaoDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            participacaoService.atualizarParticipacao(id, dto);
            redirectAttributes.addFlashAttribute("mensagem", "Participação atualizada com sucesso!");
            return "redirect:/admin/participacao";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar participação: " + e.getMessage());
            return "redirect:/admin/participacao/editar/" + id;
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluirParticipacao(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            participacaoService.deletarParticipacao(id);;
            redirectAttributes.addFlashAttribute("mensagem", "Participação excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir participação: " + e.getMessage());
        }
        return "redirect:/admin/participacao";
    }
}