package br.edu.fiap.banco.controller;

import br.edu.fiap.banco.dto.TipoContaRequest;
import br.edu.fiap.banco.dto.TipoContaResponse;
import br.edu.fiap.banco.service.TipoContaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Resource da camada HTTP para o CRUD de tipos de conta.
 * Ele converte protocolo em chamadas do service e não executa SQL.
 */
@Path("/api/tipos-conta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tipos de Conta", description = "Operações para gerenciamento de tipos de conta bancária")
@SecurityRequirement(name = "bearerAuth")
public class TipoContaResource {
    private final TipoContaService service;

    /** Injeta a camada que contém as regras e a coordenação do CRUD. */
    @Inject
    public TipoContaResource(TipoContaService service) {
        this.service = service;
    }

    /** Cadastra e devolve HTTP 201 com a localização do novo recurso. */
    @POST
    public Response cadastrar(@NotNull @Valid TipoContaRequest request, @Context UriInfo uriInfo) {
        TipoContaResponse resposta = service.cadastrar(request);
        // Constrói a URI para o header Location (/api/tipos-conta/{id})
        URI location = uriInfo.getAbsolutePathBuilder().path(resposta.id().toString()).build();
        return Response.created(location).entity(resposta).build();
    }

    /** Lista todos os tipos cadastrados. */
    @GET
    public Response listar() {
        List<TipoContaResponse> responses = service.listar();
        return Response.ok(responses).build();
    }

    /** Busca um tipo pelo ID informado no caminho da URL. */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        TipoContaResponse responseDTO = service.buscar(id);
        return Response.ok(responseDTO).build();
    }

    /** Substitui o nome do tipo existente após validar o corpo. */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @NotNull @Valid TipoContaRequest request) {
        TipoContaResponse responseDTO = service.atualizar(id, request);
        return Response.ok(responseDTO).build();
    }

    /** Exclui o tipo quando nenhuma conta possui essa chave estrangeira. */
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.excluir(id);
        return Response.noContent().build();
    }
}