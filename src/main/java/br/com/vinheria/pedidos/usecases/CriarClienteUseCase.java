package br.com.vinheria.pedidos.usecases;

import br.com.vinheria.pedidos.dto.cliente.CriarClienteInputDTO;
import br.com.vinheria.pedidos.entities.Cliente;
import br.com.vinheria.pedidos.infra.ApiError;
import br.com.vinheria.pedidos.repository.ClienteRepository;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

public class CriarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public CriarClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(CriarClienteInputDTO input) throws SQLException {
        validar(input);

        Cliente cliente = input.toCliente();
        clienteRepository.create(cliente);
        return cliente;
    }

    private void validar(CriarClienteInputDTO input) {
        if (input.nome() == null || input.nome().isBlank())
            throw badRequest("Nome e obrigatorio");

        if (input.email() == null || input.email().isBlank())
            throw badRequest("Email e obrigatorio");

        // validação simples de email
        if (!input.email().contains("@"))
            throw badRequest("Email invalido");

        if (input.mensagem() == null || input.mensagem().isBlank())
            throw badRequest("Mensagem e obrigatoria");
    }

    private WebApplicationException badRequest(String mensagem) {
        return new WebApplicationException(
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ApiError(mensagem))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
        );
    }
}
