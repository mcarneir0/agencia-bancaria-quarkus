package br.edu.fiap.banco.exception;

import jakarta.ws.rs.core.Response;

/** Falha 409 que traduz a regra e a restrição UNIQUE de nome. */
public class TipoContaJaCadastradoException extends ErroNegocioException {
    public TipoContaJaCadastradoException() {
        super(Response.Status.CONFLICT, "Já existe um tipo de conta com esse nome.");
    }
}
