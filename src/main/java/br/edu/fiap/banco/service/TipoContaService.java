package br.edu.fiap.banco.service;

import br.edu.fiap.banco.dto.TipoContaRequest;
import br.edu.fiap.banco.dto.TipoContaResponse;
import br.edu.fiap.banco.entity.TipoConta;
import br.edu.fiap.banco.exception.TipoContaJaCadastradoException;
import br.edu.fiap.banco.exception.TipoContaNaoEncontradoException;
import br.edu.fiap.banco.repository.TipoContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Serviço de aplicação que coordena o CRUD de tipos de conta.
 * O resource traduz HTTP; o repository traduz JDBC; esta classe decide regras
 * como unicidade e existência do registro.
 */
@ApplicationScoped
public class TipoContaService {
    private final TipoContaRepository repository;

    /** Injeção por construtor facilita substituir a persistência em testes. */
    @Inject
    public TipoContaService(TipoContaRepository repository) {
        this.repository = repository;
    }

    /** Impede nome repetido antes de construir e persistir a entidade. */
    public TipoContaResponse cadastrar(TipoContaRequest request) {
        if (repository.existePorNome(request.nome())) {
            throw new TipoContaJaCadastradoException();
        }
        return TipoContaResponse.de(repository.salvar(TipoConta.novo(request.nome())));
    }

    /** Converte a coleção de entidades para o contrato de saída da API. */
    public List<TipoContaResponse> listar() {
        return repository.listar().stream().map(TipoContaResponse::de).toList();
    }

    /** Transforma Optional vazio em exceção de negócio 404. */
    public TipoContaResponse buscar(Long id) {
        return TipoContaResponse.de(repository.buscarPorId(id).orElseThrow(TipoContaNaoEncontradoException::new));
    }

    /** Atualiza o nome sem permitir que ele colida com outro registro. */
    public TipoContaResponse atualizar(Long id, TipoContaRequest request) {
        TipoConta atual = repository.buscarPorId(id).orElseThrow(TipoContaNaoEncontradoException::new);
        if (!atual.nome().equalsIgnoreCase(request.nome()) && repository.existePorNome(request.nome())) {
            throw new TipoContaJaCadastradoException();
        }
        return TipoContaResponse.de(repository.atualizar(atual.atualizarNome(request.nome())));
    }

    /** Exclui e diferencia ID ausente de sucesso. A FK pode produzir conflito. */
    public void excluir(Long id) {
        if (!repository.excluir(id)) {
            throw new TipoContaNaoEncontradoException();
        }
    }
}
