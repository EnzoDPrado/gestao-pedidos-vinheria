package br.com.vinheria.pedidos.service;

import br.com.vinheria.pedidos.dto.CriarPedidoInputDTO;
import br.com.vinheria.pedidos.entities.Pedido;
import br.com.vinheria.pedidos.repository.PedidoRepository;
import br.com.vinheria.pedidos.usecases.CriarPedidoUseCase;
import br.com.vinheria.pedidos.usecases.DeletarPedidoUseCase;
import br.com.vinheria.pedidos.usecases.ListarPedidosUseCase;

import java.sql.SQLException;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService() throws SQLException {
        this.pedidoRepository = new PedidoRepository();
    }

    public List<Pedido> listarTodos() throws SQLException {
        ListarPedidosUseCase useCase = new ListarPedidosUseCase(pedidoRepository);
        List<Pedido> lista = useCase.execute();
        pedidoRepository.closeConnection();
        return lista;
    }

    public Pedido criar(CriarPedidoInputDTO input) throws SQLException {
        CriarPedidoUseCase useCase = new CriarPedidoUseCase(pedidoRepository);
        Pedido pedido = useCase.execute(input);
        pedidoRepository.closeConnection();
        return pedido;
    }

    public void deletar(Long id) throws SQLException {
        DeletarPedidoUseCase useCase = new DeletarPedidoUseCase(pedidoRepository);
        useCase.execute(id);
        pedidoRepository.closeConnection();
    }
}
