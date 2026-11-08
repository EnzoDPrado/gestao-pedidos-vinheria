package br.com.vinheria.pedidos.controller;

import br.com.vinheria.pedidos.dto.CriarPedidoInputDTO;
import br.com.vinheria.pedidos.service.PedidoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;

@Path("pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController() throws SQLException {
        this.pedidoService = new PedidoService();
    }

    @GET
    public Response listarTodos() throws SQLException {
        var pedidos = pedidoService.listarTodos();
        return Response.ok(pedidos).build();
    }

    @POST
    public Response criar(CriarPedidoInputDTO input, @Context UriInfo uriInfo) throws SQLException {
        var pedido = pedidoService.criar(input);

        UriBuilder uri = uriInfo.getAbsolutePathBuilder();
        uri.path(String.valueOf(pedido.getId()));
        return Response.created(uri.build()).entity(pedido).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) throws SQLException {
        pedidoService.deletar(id);
        return Response.noContent().build();
    }
}
