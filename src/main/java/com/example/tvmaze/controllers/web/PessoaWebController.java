package com.example.tvmaze.controllers.web;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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

import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.services.ParticipacaoService;
import com.example.tvmaze.services.PessoaService;

@Controller
@RequestMapping("/pessoas")
public class PessoaWebController {
    
    @Autowired
    PessoaService pessoaService;

    @Autowired
    ParticipacaoService participacaoService;

    @GetMapping
    public String pessoas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer decadaNascimento,
            @RequestParam(defaultValue = "nome") String ordenar,
            @RequestParam(defaultValue = "asc") String direcao,
            Model model) {
        
        // Criar objeto de paginação com ordenação
        Sort.Direction direction = direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, ordenar));
        
        // Buscar pessoas com filtros aplicados
        Page<PessoaRespostaDTO> pessoasPage = pessoaService.listarPessoasComFiltros(
            nome, decadaNascimento, ordenar, direcao, pageable);
        
        // Buscar décadas disponíveis para o filtro
        List<Integer> decadasDisponiveis = pessoaService.listarTodasDecadas();
        
        // Adicionar dados ao modelo
        model.addAttribute("pessoasPage", pessoasPage);
        model.addAttribute("decadasDisponiveis", decadasDisponiveis);
        
        // Manter os filtros selecionados
        model.addAttribute("nomeSelecionado", nome);
        model.addAttribute("decadaSelecionada", decadaNascimento);
        model.addAttribute("ordenarSelecionado", ordenar);
        model.addAttribute("direcaoSelecionada", direcao);
        
        return "pessoas/pessoas";
    }

    @GetMapping("/{id}")
    public String detalhePessoa(@PathVariable Integer id, Model model) {
        try {
            // Buscar dados da pessoa
            PessoaRespostaDTO pessoa = pessoaService.listarPorId(id);
            
            // Buscar participações da pessoa
            List<ParticipacaoRespostaDTO> participacoes = participacaoService.listarParticipacoesPorPessoaId(id);
            
            // Calcular idade se houver data de nascimento
            Integer idade = null;
            if (pessoa.getDataNascimento() != null) {
                idade = Period.between(pessoa.getDataNascimento(), LocalDate.now()).getYears();
            }
            
            // Adicionar ao modelo
            model.addAttribute("pessoa", pessoa);
            model.addAttribute("participacoes", participacoes);
            model.addAttribute("idade", idade);
            
            return "pessoas/detalhe";
            
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Pessoa não encontrada");
            return "error/404";
        }
    }
}