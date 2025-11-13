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

import com.example.tvmaze.dtos.personagem.PersonagemRequestDTO;
import com.example.tvmaze.dtos.personagem.PersonagemRespostaDTO;
import com.example.tvmaze.dtos.pessoa.PessoaCriacaoDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.services.PersonagemService;

@Controller
@RequestMapping("/admin/personagem")
public class AdminPersonagemWebController {

    @Autowired
    PersonagemService personagemService;

    @GetMapping("/criar")
    public String criarPersonagem(Model model) {
        model.addAttribute("pessoa", new PersonagemRequestDTO());
        return "admin/personagem/form";
    }

    @PostMapping("/salvar")
    public String salvarPersonagem(
            @ModelAttribute PersonagemRequestDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            personagemService.criar(dto);
            redirectAttributes.addFlashAttribute("mensagem", "Personagem salvo com sucesso!");
            return "redirect:/admin/personagem";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar pessoa: " + e.getMessage());
            return "redirect:/admin/personagem/criar";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarPersonagem(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            PersonagemRespostaDTO personagemExistente = personagemService.buscarPorId(id);

            PersonagemRequestDTO personagem = new PersonagemRequestDTO();
            personagem.setNomePersonagem(personagemExistente.getNomePersonagem());
            personagem.setImagem(personagemExistente.getImagem());

            model.addAttribute("pessoa", personagem);
            model.addAttribute("pessoaId", id);

            return "admin/personagem/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Personagem não encontrada");
            return "redirect:/admin/personagem";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarPersonagem(
            @PathVariable Integer id,
            @ModelAttribute PersonagemRequestDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            personagemService.atualizar(id, dto);
            redirectAttributes.addFlashAttribute("mensagem", "Personagem atualizado com sucesso!");
            return "redirect:/admin/personagem";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar pessoa: " + e.getMessage());
            return "redirect:/admin/personagem/editar/" + id;
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluirPersonagem(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            personagemService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Personagem excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir pessoa: " + e.getMessage());
        }
        return "redirect:/admin/personagem";
    }

    @GetMapping
    public String listarPessoas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nomePersonagem,
            @RequestParam(required = false) String ordenar,
            @RequestParam(defaultValue = "desc") String direcao,
            Model model) {

        String ordenarCampo = (ordenar == null || ordenar.isBlank()) ? "personagemId" : ordenar;

        Sort.Direction direction = direcao.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, ordenarCampo));

        Page<PersonagemRespostaDTO> personagemPage = personagemService.listarPessoasComFiltros(
                nomePersonagem, ordenarCampo, direcao, pageable);

        model.addAttribute("personagemPage", personagemPage);
        model.addAttribute("personagem", personagemPage.getContent());
        model.addAttribute("nomeSelecionado", nomePersonagem);
        model.addAttribute("ordenarSelecionado", ordenarCampo);
        model.addAttribute("direcaoSelecionada", direcao);

        return "admin/personagem/listar";
    }
}
