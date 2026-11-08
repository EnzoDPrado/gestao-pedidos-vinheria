package br.com.vinheria.pedidos.controller;

import br.com.vinheria.pedidos.dto.CriarClienteInputDTO;
import br.com.vinheria.pedidos.service.ClienteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;

@Path("clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() throws SQLException {
        this.clienteService = new ClienteService();
    }

    @GET
    public Response listarTodos() throws SQLException {
        var clientes = clienteService.listarTodos();
        return Response.ok(clientes).build();
    }

    @POST
    public Response criar(CriarClienteInputDTO input, @Context UriInfo uriInfo) throws SQLException {
        var cliente = clienteService.criar(input);

        UriBuilder uri = uriInfo.getAbsolutePathBuilder();
        uri.path(String.valueOf(cliente.getId()));
        return Response.created(uri.build()).entity(cliente).build();
    }
}
