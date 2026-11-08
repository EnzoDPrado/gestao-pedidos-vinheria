package br.com.vinheria.pedidos.usecases;

import br.com.vinheria.pedidos.entities.Cliente;
import br.com.vinheria.pedidos.repository.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class ListarClientesUseCase {

    private final ClienteRepository clienteRepository;

    public ListarClientesUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> execute() throws SQLException {
        return clienteRepository.findAll();
    }
}
