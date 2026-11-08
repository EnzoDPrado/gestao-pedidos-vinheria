package br.com.vinheria.pedidos.dto;

import br.com.vinheria.pedidos.entities.Pedido;

import java.sql.Timestamp;

public record CriarPedidoInputDTO(
        Long idVinho,
        Long idCliente,
        String canalVenda,
        String regiaoEntrega,
        Integer quantidade,
        Double precoUnitario,
        Integer descontoAplicado,
        Timestamp dataVenda,
        String temporada,
        Integer avaliacaoCliente,
        Boolean vendaSucesso
) {
    public Pedido toPedido() {
        return new Pedido(
                this.idVinho,
                this.idCliente,
                this.canalVenda,
                this.regiaoEntrega,
                this.quantidade,
                this.precoUnitario,
                this.descontoAplicado,
                this.dataVenda,
                this.temporada,
                this.avaliacaoCliente,
                this.vendaSucesso
        );
    }
}
