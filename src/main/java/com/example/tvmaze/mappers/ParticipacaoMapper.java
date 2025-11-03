package com.example.tvmaze.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.tvmaze.dtos.participacao.ParticipacaoApiDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoCriacaoDTO;
import com.example.tvmaze.dtos.participacao.ParticipacaoRespostaDTO;
import com.example.tvmaze.dtos.participacao.PersonagemApiDTO;
import com.example.tvmaze.dtos.pessoa.PessoaApiDTO;
import com.example.tvmaze.dtos.pessoa.PessoaRespostaDTO;
import com.example.tvmaze.entities.Participacao;
import com.example.tvmaze.entities.Pessoa;
import com.example.tvmaze.entities.Serie;
import com.example.tvmaze.entities.vo.Personagem;
import com.example.tvmaze.repositories.ParticipacaoRepository;
import com.example.tvmaze.repositories.PessoaRepository;
import com.example.tvmaze.repositories.SerieRepository;

@Component
public class ParticipacaoMapper {

    @Autowired
    private PessoaRepository pessoaRepository; // Para buscar a Pessoa
    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    // Para buscar a Série

    /**
     * Converte uma entidade Participacao para ParticipacaoRespostaDTO.
     */
    public ParticipacaoRespostaDTO toRespostaDTO(Participacao participacao) {
        ParticipacaoRespostaDTO dto = new ParticipacaoRespostaDTO();
        dto.setParticipacao_id(participacao.getParticipacaoId());
        dto.setSerie_id(participacao.getSerie().getSerieId());
        dto.setSerie_nome(participacao.getSerie().getNome());

        PessoaRespostaDTO pessoaDTO = new PessoaRespostaDTO();

        // Preenche o DTO da Pessoa com os dados da entidade participacao.getPessoa()
        Pessoa pessoa = participacao.getPessoa();
        pessoaDTO.setPessoaId(pessoa.getPessoaId());
        pessoaDTO.setExternoId(pessoa.getExternoId());
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setImagem(pessoa.getImagem());
        pessoaDTO.setDataNascimento(pessoa.getDataNascimento());
        dto.setPessoa(pessoaDTO);

        // O PersonagemApiDTO não é ideal para resposta, mas vamos usá-lo se não houver
        // um DTO de PersonagemResposta
        PersonagemApiDTO personagemDTO = new PersonagemApiDTO();
        if (participacao.getPersonagem() != null) {
            // A Entidade Participacao tem um VO Personagem, que armazena o nome e o id
            personagemDTO.setExternoId(participacao.getPersonagem().getExternoId());
            personagemDTO.setNomePersonagem(participacao.getPersonagem().getNomePersonagem());
            personagemDTO.setImagem(participacao.getPersonagem().getImagem());
        }
        dto.setPersonagem(personagemDTO);
        return dto;
    }

    public Participacao toEntity(ParticipacaoApiDTO dto, Serie serie) {
    Participacao participacao = new Participacao();

    // 1. Gerenciar a Pessoa
    Pessoa pessoa = findOrCreatePessoa(dto.getPessoa());
    participacao.setPessoa(pessoa);

    // 2. Mapear o personagem (A CORREÇÃO DE TIPO ESTÁ AQUI)
    if (dto.getPersonagem() != null) {
        
        // DTOs de integração (ApiDTO) geralmente usam o nome do personagem
        String nomeDoPersonagem = dto.getPersonagem().getNomePersonagem(); 
        Integer idExternoPersonagem = dto.getPersonagem().getExternoId();
        
        // Cria uma nova instância do Value Object Personagem
        Personagem personagemVO = new Personagem(); 
        
        // Preenche o VO Personagem com os dados
        personagemVO.setNomePersonagem(nomeDoPersonagem);
        personagemVO.setExternoId(idExternoPersonagem);
        personagemVO.setImagem(dto.getPersonagem().getImagem());
        
        // Define o objeto Personagem na entidade Participacao (CORRIGIDO)
        participacao.setPersonagem(personagemVO);
        
        // Define o ID externo da Participação (usando o ID do Personagem da API)
   
    }

    // 3. Associar a Série
    participacao.setSerie(serie);

    return participacao;
}
    public Pessoa findOrCreatePessoa(PessoaApiDTO pessoaApiDTO) { // AGORA RECEBE SEU PessoaApiDTO
        if (pessoaApiDTO == null || pessoaApiDTO.getExternoId() == null) {
            throw new IllegalArgumentException("Dados de pessoa da API inválidos para mapeamento.");
        }

        return pessoaRepository.findByExternoId(pessoaApiDTO.getExternoId())
                .map(existingPessoa -> {
                    // Se a pessoa já existe, atualiza seus dados (nome, dataNascimento)
                    existingPessoa.setNome(pessoaApiDTO.getNome());
                    existingPessoa.setDataNascimento(pessoaApiDTO.getDataNascimento());
                    existingPessoa.setImagem(pessoaApiDTO.getImagem());
                    return pessoaRepository.save(existingPessoa); // Salva as atualizações
                })
                .orElseGet(() -> {
                    // Se a pessoa não existe, cria uma nova
                    Pessoa novaPessoa = new Pessoa();
                    novaPessoa.setExternoId(pessoaApiDTO.getExternoId());
                    novaPessoa.setNome(pessoaApiDTO.getNome());
                    novaPessoa.setDataNascimento(pessoaApiDTO.getDataNascimento());
                    novaPessoa.setImagem(pessoaApiDTO.getImagem()); // Define dataNascimento
                    return pessoaRepository.save(novaPessoa); // Salva a nova pessoa
                });
    }

    public void updateEntity(ParticipacaoApiDTO dto, Participacao participacao) {
        // Atualiza a Pessoa (mesmo que seja só para garantir que exista e esteja
        // atualizada)
        Pessoa pessoa = findOrCreatePessoa(dto.getPessoa());
        participacao.setPessoa(pessoa);

        // Atualiza o personagem
        if (dto.getPersonagem() != null) {

            // 1. Cria ou obtém o Value Object Personagem existente
            Personagem personagemVO = participacao.getPersonagem();

            // Se a entidade ainda não tem um VO Personagem, cria um novo.
            if (personagemVO == null) {
                personagemVO = new Personagem();
            }

            // 2. Atualiza os campos do VO Personagem com os dados do DTO
            personagemVO.setNomePersonagem(dto.getPersonagem().getNomePersonagem());
            personagemVO.setExternoId(dto.getPersonagem().getExternoId());

            // 3. Define o objeto Personagem (corrigido) na entidade Participacao
            participacao.setPersonagem(personagemVO);

            // 4. Atualiza o ID externo da Participação (se ele for o ID do personagem)
            
        }
    }

    /**
     * Converte um ParticipacaoCriacaoDTO para uma nova entidade Participacao.
     * Lança RuntimeException se Pessoa ou Serie não forem encontradas.
     */
    public Participacao toEntity(ParticipacaoCriacaoDTO dto) {
    Participacao participacao = new Participacao();

    
    
    // CORREÇÃO: Criar o objeto Personagem (VO) a partir do dado do DTO
    if (dto.getPersonagem() != null) {
        Personagem personagemVO = new Personagem();
        
        // Assumindo que dto.getPersonagem() retorna o NOME do personagem (String)
        personagemVO.setNomePersonagem(dto.getPersonagem()); 
        
        // Se o DTO tem um campo para o ID Externo do Personagem, você deve buscá-lo aqui.
        // Como não temos esse campo no DTO, apenas definiremos o nome.
        
        participacao.setPersonagem(personagemVO); // Passa o objeto Personagem correto
    }

    // Busca e associa a Pessoa e Série... (Restante do código OK)

    return participacao;
}

    /**
     * Atualiza uma entidade Participacao existente com dados de
     * ParticipacaoCriacaoDTO.
     * Lança RuntimeException se Pessoa ou Serie não forem encontradas.
     */
    public void toEntity(ParticipacaoCriacaoDTO dto, Participacao participacao) {
    
    
    // --- CORREÇÃO DE TIPO AQUI ---
    if (dto.getPersonagem() != null) {
        
        // 1. Cria o objeto Personagem (VO/Embeddable)
        Personagem personagemVO = new Personagem(); 
        
        // 2. Preenche o objeto com a String retornada pelo DTO
        // Assumimos que dto.getPersonagem() retorna o NOME do personagem (String)
        personagemVO.setNomePersonagem(dto.getPersonagem()); 
        
        // Note: Como este DTO de Criação não tem o ID externo do Personagem,
        // o campo Personagem.externoId será null.
        
        // 3. Define o objeto Personagem (corrigido) na entidade Participacao
        participacao.setPersonagem(personagemVO);
    }

    // Busca e associa a Pessoa
    Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + dto.getPessoaId()));
    participacao.setPessoa(pessoa);

    // Busca e associa a Série
    Serie serie = serieRepository.findById(dto.getSerieId())
            .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + dto.getSerieId()));
    participacao.setSerie(serie);
}

    /**
     * Verifica se já existe uma Participacao com a mesma Pessoa e Serie.
     * ATUALIZADO para usar PessoaApiDTO.
     */
    public boolean exists(ParticipacaoApiDTO dto, Serie serie) {
        if (dto.getPessoa() == null || dto.getPessoa().getExternoId() == null ||
                dto.getPersonagem() == null || dto.getPersonagem().getNomePersonagem() == null) {
            return false; // Não é possível verificar se falta dados essenciais
        }

        Optional<Pessoa> pessoaOpt = pessoaRepository.findByExternoId(dto.getPessoa().getExternoId());
        if (pessoaOpt.isEmpty()) {
            return false; // Pessoa ainda não existe localmente, então a participação também não
        }

        return participacaoRepository.findByPessoaAndSerieAndPersonagem_NomePersonagem(
                pessoaOpt.get(),
                serie,
                dto.getPersonagem().getNomePersonagem()).isPresent();
    }
}
