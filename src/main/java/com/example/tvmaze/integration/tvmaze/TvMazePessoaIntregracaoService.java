package com.example.tvmaze.integration.tvmaze;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.example.tvmaze.mappers.PessoaMapper;
import com.example.tvmaze.models.Pessoa;
import com.example.tvmaze.repositories.PessoaRepository;

@Service
public class TvMazePessoaIntregracaoService {
    
    
    @Autowired
    private TvMazeClient tvMazeClient;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    public Pessoa importarPessoaPorNome(String nome) {
        try {
            
            String endpoint = "/search/people?q=" + URLEncoder.encode(nome, StandardCharsets.UTF_8);

            PessoaApiDTO[] resultados = tvMazeClient.get(endpoint, PessoaApiDTO[].class);

            if (resultados != null && resultados.length > 0) {
                PessoaApiDTO pessoaApiDTO = resultados[0];

                // Verifica se a pessoa já existe no banco de dados pelo ID externo
                Optional<Pessoa> pessoaExistente = pessoaRepository.findByExternoId(pessoaApiDTO.getExternoId());
                if (pessoaExistente.isPresent()) {
                    return pessoaExistente.get(); // Retorna a pessoa existente
                }

                // Mapeia e salva a nova pessoa
                Pessoa novaPessoa = pessoaMapper.toEntity(pessoaApiDTO);
                return pessoaRepository.save(novaPessoa);
            } else {
                throw new RuntimeException("Pessoa com o nome " + nome + " não encontrada no TVMaze.");
            }
        } catch (Exception e) {
            // Logar o erro ou lançar uma exceção mais específica
            throw new RuntimeException("Erro ao importar pessoa do TVMaze: " + e.getMessage(), e);
        }
    }

    // Se você quiser um método para importar uma pessoa diretamente por um ID externo (do TVMaze)
    public Pessoa importarPessoaPorIdExterno(Integer externoId) {
        try {
            String endpoint = "/people/" + externoId;
            PessoaApiDTO pessoaApiDTO = tvMazeClient.get(endpoint, PessoaApiDTO.class);

            // Verifica se a pessoa já existe no banco de dados pelo ID externo
            Optional<Pessoa> pessoaExistente = pessoaRepository.findByExternoId(pessoaApiDTO.getExternoId());
            if (pessoaExistente.isPresent()) {
                return pessoaExistente.get();
            }

            Pessoa novaPessoa = pessoaMapper.toEntity(pessoaApiDTO);
            return pessoaRepository.save(novaPessoa);
        } catch (RuntimeException e) {
             throw new RuntimeException("Pessoa com o ID externo " + externoId + " não encontrada no TVMaze.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar pessoa do TVMaze por ID externo: " + e.getMessage(), e);
        }
    }
}

