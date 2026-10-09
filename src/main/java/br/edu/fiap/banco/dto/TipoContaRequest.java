package br.edu.fiap.banco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record TipoContaRequest(
        @Schema(description = "Nome do tipo de conta", example = "CONTA UNIVERSITARIA", required = true)
        @NotBlank(message = "O nome não pode ser vazio ou formado apenas por espaços")
        @Size(min = 3, max = 40, message = "O nome deve ter entre 3 e 40 caracteres")
        String nome
) {
}