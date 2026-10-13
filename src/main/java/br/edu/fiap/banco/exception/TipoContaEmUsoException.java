package br.edu.fiap.banco.exception;

import jakarta.ws.rs.core.Response;

/** Falha 409 quando a FK impede excluir um tipo utilizado por uma conta. */
public class TipoContaEmUsoException extends ErroNegocioException {
    public TipoContaEmUsoException() {
        super(Response.Status.CONFLICT, "Tipo de conta está associado a uma conta bancária.");
    }
}
