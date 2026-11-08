package br.com.vinheria.pedidos.dto;

import br.com.vinheria.pedidos.entities.Cliente;

import java.sql.Timestamp;

public record CriarClienteInputDTO(
        String nome,
        String email,
        String assunto,
        String mensagem,
        Timestamp dataEnvio
) {
    public Cliente toCliente() {
        return new Cliente(
                this.nome,
                this.email,
                this.assunto,
                this.mensagem,
                this.dataEnvio
        );
    }
}
