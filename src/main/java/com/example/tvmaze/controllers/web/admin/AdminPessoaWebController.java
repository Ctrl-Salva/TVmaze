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

import com.example.tvmaze.dtos.pessoa.PessoaCriacaoDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.services.PessoaService;

@Controller
@RequestMapping("/admin/pessoas")
public class AdminPessoaWebController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/criar")
    public String criarPessoa(Model model) {
        model.addAttribute("pessoa", new PessoaCriacaoDTO());
        return "admin/pessoas/form";
    }

    @PostMapping("/salvar")
    public String salvarPessoa(
            @ModelAttribute PessoaCriacaoDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            pessoaService.criarPessoa(dto);
            redirectAttributes.addFlashAttribute("mensagem", "Pessoa salva com sucesso!");
            return "redirect:/admin/pessoas";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar pessoa: " + e.getMessage());
            return "redirect:/admin/pessoas/criar";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarPessoa(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            PessoaRespostaDTO pessoaExistente = pessoaService.listarPorId(id);
            
            PessoaCriacaoDTO pessoa = new PessoaCriacaoDTO();
            pessoa.setNome(pessoaExistente.getNome());
            pessoa.setDataNascimento(pessoaExistente.getDataNascimento());
            pessoa.setImagem(pessoaExistente.getImagem());
            
            model.addAttribute("pessoa", pessoa);
            model.addAttribute("pessoaId", id);
            
            return "admin/pessoas/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Pessoa não encontrada");
            return "redirect:/admin/pessoas";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarPessoa(
            @PathVariable Integer id,
            @ModelAttribute PessoaCriacaoDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            pessoaService.atualizarPessoa(id, dto);
            redirectAttributes.addFlashAttribute("mensagem", "Pessoa atualizada com sucesso!");
            return "redirect:/admin/pessoas";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar pessoa: " + e.getMessage());
            return "redirect:/admin/pessoas/editar/" + id;
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluirPessoa(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.deletarPessoa(id);
            redirectAttributes.addFlashAttribute("mensagem", "Pessoa excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir pessoa: " + e.getMessage());
        }
        return "redirect:/admin/pessoas";
    }

    @GetMapping
    public String listarPessoas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer decadaNascimento,
            @RequestParam(required = false) String ordenar,
            @RequestParam(defaultValue = "desc") String direcao,
            Model model) {

        String ordenarCampo = (ordenar == null || ordenar.isBlank()) ? "pessoaId" : ordenar;
        
        Sort.Direction direction = direcao.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, ordenarCampo));

        Page<PessoaRespostaDTO> pessoasPage = pessoaService.listarPessoasComFiltros(
                nome, decadaNascimento, ordenarCampo, direcao, pageable);

        model.addAttribute("pessoasPage", pessoasPage);
        model.addAttribute("pessoas", pessoasPage.getContent());
        
        model.addAttribute("nomeSelecionado", nome);
        model.addAttribute("decadaSelecionada", decadaNascimento);
        model.addAttribute("ordenarSelecionado", ordenarCampo);
        model.addAttribute("direcaoSelecionada", direcao);

        return "admin/pessoas/listar";
    }
}
