package br.edu.fiap.banco.controller;

import br.edu.fiap.banco.dto.TipoContaRequest;
import br.edu.fiap.banco.dto.TipoContaResponse;
import br.edu.fiap.banco.service.TipoContaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/api/tipos-conta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tipos de Conta", description = "Operações para gerenciamento de tipos de conta bancária")
@SecurityRequirement(name = "bearerAuth")
public class TipoContaResource {

    private final TipoContaService tipoContaService;

    @Inject
    public TipoContaResource(TipoContaService tipoContaService) {
        this.tipoContaService = tipoContaService;
    }

    @POST
    public Response cadastrar(@Valid TipoContaRequest request, @Context UriInfo uriInfo) {
        TipoContaResponse responseDTO = tipoContaService.cadastrar(request);

        // Constrói a URI para o header Location (/api/tipos-conta/{id})
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(responseDTO.id()))
                .build();

        return Response.created(location).entity(responseDTO).build();
    }

    @GET
    public Response listarTodos() {
        List<TipoContaResponse> responses = tipoContaService.listarTodos();
        return Response.ok(responses).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        TipoContaResponse responseDTO = tipoContaService.buscarPorId(id);
        return Response.ok(responseDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid TipoContaRequest request) {
        TipoContaResponse responseDTO = tipoContaService.atualizar(id, request);
        return Response.ok(responseDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        tipoContaService.excluir(id);
        return Response.noContent().build();
    }
}