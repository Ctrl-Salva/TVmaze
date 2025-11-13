package com.example.tvmaze.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tvmaze.dtos.pessoa.PessoaCriacaoDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.models.Pessoa;
import com.example.tvmaze.services.PessoaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/pessoa")
public class PessoaRestController {
    
    @Autowired
    PessoaService pessoaService;

    @GetMapping
    public List<PessoaRespostaDTO> listar(){
        return pessoaService.listarPessoas();
    }

    @GetMapping("/{id}")
    public PessoaRespostaDTO buscarPorId(@PathVariable Integer id){
        return pessoaService.listarPorId(id);
    }

    @PostMapping
    public Pessoa salvar(@RequestBody PessoaCriacaoDTO dto){
        return pessoaService.criarPessoa(dto);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Integer id, @RequestBody PessoaCriacaoDTO dto){
        return pessoaService.atualizarPessoa(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
         pessoaService.deletarPessoa(id);
    }
}
