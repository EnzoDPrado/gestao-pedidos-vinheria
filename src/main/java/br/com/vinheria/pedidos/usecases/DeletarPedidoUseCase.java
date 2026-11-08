package br.com.vinheria.pedidos.usecases;

import br.com.vinheria.pedidos.infra.ApiError;
import br.com.vinheria.pedidos.repository.PedidoRepository;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

public class DeletarPedidoUseCase {

    private final PedidoRepository pedidoRepository;

    public DeletarPedidoUseCase(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void execute(Long id) throws SQLException {
        // verifica se existe antes de deletar
        pedidoRepository.findById(id).orElseThrow(() ->
                new WebApplicationException(
                        Response.status(Response.Status.NOT_FOUND)
                                .entity(new ApiError("Pedido nao encontrado"))
                                .type(MediaType.APPLICATION_JSON)
                                .build()
                ));

        pedidoRepository.delete(id);
    }
}
