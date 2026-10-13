package br.edu.fiap.banco.exception;

import jakarta.ws.rs.core.Response;

/** Falha 404 usada quando o id do tipo de conta não existe. */
public class TipoContaNaoEncontradoException extends ErroNegocioException {
    public TipoContaNaoEncontradoException() {
        super(Response.Status.NOT_FOUND, "Tipo de conta não encontrado.");
    }
}
