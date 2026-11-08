package br.com.vinheria.pedidos.usecases;

import br.com.vinheria.pedidos.entities.Pedido;
import br.com.vinheria.pedidos.repository.PedidoRepository;

import java.sql.SQLException;
import java.util.List;

public class ListarPedidosUseCase {

    private final PedidoRepository pedidoRepository;

    public ListarPedidosUseCase(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> execute() throws SQLException {
        return pedidoRepository.findAll();
    }
}
