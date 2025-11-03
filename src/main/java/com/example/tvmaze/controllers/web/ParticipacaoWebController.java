package com.example.tvmaze.controllers.web;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.services.ParticipacaoService;

@Controller
@RequestMapping("/participacoes")
public class ParticipacaoWebController {
    
    @Autowired
    ParticipacaoService participacaoService;

    @GetMapping
    public String participacoes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "24") int size,
            @RequestParam(required = false) Integer serieId,
            @RequestParam(required = false) Integer pessoaId,
            @RequestParam(required = false) String nomePersonagem,
            @RequestParam(defaultValue = "participacaoId") String ordenar,
            @RequestParam(defaultValue = "desc") String direcao,
            Model model) {
        
        // Criar objeto de paginação com ordenação
        Sort.Direction direction = direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        // Ajustar o campo de ordenação para relacionamentos
        String campoOrdenacao = ordenar;
        if ("serie".equals(ordenar)) {
            campoOrdenacao = "serie.nome";
        } else if ("pessoa".equals(ordenar)) {
            campoOrdenacao = "pessoa.nome";
        } else if ("personagem".equals(ordenar)) {
            campoOrdenacao = "personagem.nomePersonagem";
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, campoOrdenacao));
        
        // Buscar participações com filtros aplicados
        Page<ParticipacaoRespostaDTO> participacoesPage = participacaoService.listarParticipacoesComFiltros(
            serieId, pessoaId, nomePersonagem, pageable);
        
        // Buscar dados para os filtros (dropdowns)
        Map<Integer, String> seriesDisponiveis = participacaoService.listarSeriesComParticipacoes();
        Map<Integer, String> pessoasDisponiveis = participacaoService.listarPessoasComParticipacoes();
        
        // Adicionar dados ao modelo
        model.addAttribute("participacoesPage", participacoesPage);
        model.addAttribute("seriesDisponiveis", seriesDisponiveis);
        model.addAttribute("pessoasDisponiveis", pessoasDisponiveis);
        
        // Manter os filtros selecionados
        model.addAttribute("serieIdSelecionada", serieId);
        model.addAttribute("pessoaIdSelecionada", pessoaId);
        model.addAttribute("nomePersonagemSelecionado", nomePersonagem);
        model.addAttribute("ordenarSelecionado", ordenar);
        model.addAttribute("direcaoSelecionada", direcao);
        
        return "participacoes/participacoes";
    }
}
