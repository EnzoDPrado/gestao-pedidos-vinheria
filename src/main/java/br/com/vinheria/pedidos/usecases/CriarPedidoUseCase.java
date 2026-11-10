package br.com.vinheria.pedidos.usecases;

import br.com.vinheria.pedidos.dto.cliente.CriarPedidoInputDTO;
import br.com.vinheria.pedidos.entities.Pedido;
import br.com.vinheria.pedidos.infra.ApiError;
import br.com.vinheria.pedidos.repository.PedidoRepository;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

public class CriarPedidoUseCase {

    // Canais de venda aceitos
    private static final List<String> CANAIS_VALIDOS = List.of(
            "LOJA FISICA", "E-COMMERCE", "WHATSAPP", "TELEFONE", "PARCEIRO"
    );

    // Regiões de entrega aceitas
    private static final List<String> REGIOES_VALIDAS = List.of(
            "NORTE", "SUL", "SUDESTE", "NORDESTE", "CENTRO-OESTE"
    );

    private final PedidoRepository pedidoRepository;

    public CriarPedidoUseCase(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(CriarPedidoInputDTO input) throws SQLException {
        validar(input);

        Pedido pedido = input.toPedido();
        pedidoRepository.create(pedido);
        return pedido;
    }

    private void validar(CriarPedidoInputDTO input) {
        if (input.idVinho() == null)
            throw badRequest("ID do vinho e obrigatorio");

        if (input.canalVenda() == null || input.canalVenda().isBlank())
            throw badRequest("Canal de venda e obrigatorio");

        if (!CANAIS_VALIDOS.contains(input.canalVenda().toUpperCase()))
            throw badRequest("Canal de venda invalido. Use: Loja Fisica, E-commerce, WhatsApp, Telefone ou Parceiro");

        if (input.regiaoEntrega() == null || input.regiaoEntrega().isBlank())
            throw badRequest("Regiao de entrega e obrigatoria");

        if (!REGIOES_VALIDAS.contains(input.regiaoEntrega().toUpperCase()))
            throw badRequest("Regiao invalida. Use: Norte, Sul, Sudeste, Nordeste ou Centro-Oeste");

        if (input.quantidade() == null || input.quantidade() <= 0)
            throw badRequest("Quantidade deve ser maior que zero");

        if (input.precoUnitario() == null || input.precoUnitario() <= 0)
            throw badRequest("Preco unitario deve ser maior que zero");

        if (input.dataVenda() == null)
            throw badRequest("Data da venda e obrigatoria");

        // Desconto deve ser entre 0 e 100 se informado
        if (input.descontoAplicado() != null && (input.descontoAplicado() < 0 || input.descontoAplicado() > 100))
            throw badRequest("Desconto deve ser entre 0 e 100");

        // Avaliação deve ser entre 1 e 5 se informada
        if (input.avaliacaoCliente() != null && (input.avaliacaoCliente() < 1 || input.avaliacaoCliente() > 5))
            throw badRequest("Avaliacao do cliente deve ser entre 1 e 5");
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
