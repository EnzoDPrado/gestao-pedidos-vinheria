package br.com.vinheria.pedidos.service;

import br.com.vinheria.pedidos.dto.CriarClienteInputDTO;
import br.com.vinheria.pedidos.entities.Cliente;
import br.com.vinheria.pedidos.repository.ClienteRepository;
import br.com.vinheria.pedidos.usecases.CriarClienteUseCase;
import br.com.vinheria.pedidos.usecases.ListarClientesUseCase;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService() throws SQLException {
        this.clienteRepository = new ClienteRepository();
    }

    public List<Cliente> listarTodos() throws SQLException {
        ListarClientesUseCase useCase = new ListarClientesUseCase(clienteRepository);
        List<Cliente> lista = useCase.execute();
        clienteRepository.closeConnection();
        return lista;
    }

    public Cliente criar(CriarClienteInputDTO input) throws SQLException {
        CriarClienteUseCase useCase = new CriarClienteUseCase(clienteRepository);
        Cliente cliente = useCase.execute(input);
        clienteRepository.closeConnection();
        return cliente;
    }
}
