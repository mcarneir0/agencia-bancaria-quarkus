package br.edu.fiap.banco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/** JSON aceito no cadastro e na atualização de um tipo de conta. */
@Schema(name = "TipoContaRequest", description = "Dados editáveis do tipo de conta")
public record TipoContaRequest(
        @Schema(description = "Nome do tipo de conta", example = "CONTA UNIVERSITARIA", required = true)
        @NotBlank(message = "Nome do tipo de conta é obrigatório.")
        @Size(min = 3, max = 40, message = "Nome deve ter entre 3 e 40 caracteres.")
        String nome) {
}