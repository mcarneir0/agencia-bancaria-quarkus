package br.edu.fiap.banco.dto;

import br.edu.fiap.banco.entity.TipoConta;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/** Contrato de saída que impede o resource de devolver diretamente a entidade. */
public record TipoContaResponse(
        @Schema(description = "Identificador único", example = "4")
        Long id,
        @Schema(description = "Nome do tipo de conta", example = "CONTA UNIVERSITARIA")
        String nome
) {

    public static TipoContaResponse de(TipoConta tipo) {
        return new TipoContaResponse(tipo.id(), tipo.nome());
    }
}