package br.com.vinheria.pedidos.entities;

import java.sql.Timestamp;

// Representa uma venda de vinho realizada
public class Pedido {

    private Long id;
    private Long idVinho;
    private Long idCliente;         // pode ser null (cliente anônimo)
    private String canalVenda;      // Loja Fisica, E-commerce, WhatsApp, Telefone, Parceiro
    private String regiaoEntrega;   // Norte, Sul, Sudeste, Nordeste, Centro-Oeste
    private Integer quantidade;
    private Double precoUnitario;
    private Integer descontoAplicado; // percentual, pode ser null
    private Timestamp dataVenda;
    private String temporada;       // Natal, Inverno, Dia das Maes, etc. Pode ser null
    private Integer avaliacaoCliente; // 1 a 5, pode ser null
    private Boolean vendaSucesso;

    public Pedido() {}

    public Pedido(Long id, Long idVinho, Long idCliente, String canalVenda, String regiaoEntrega,
                  Integer quantidade, Double precoUnitario, Integer descontoAplicado,
                  Timestamp dataVenda, String temporada, Integer avaliacaoCliente, Boolean vendaSucesso) {
        this.id = id;
        this.idVinho = idVinho;
        this.idCliente = idCliente;
        this.canalVenda = canalVenda;
        this.regiaoEntrega = regiaoEntrega;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.descontoAplicado = descontoAplicado;
        this.dataVenda = dataVenda;
        this.temporada = temporada;
        this.avaliacaoCliente = avaliacaoCliente;
        this.vendaSucesso = vendaSucesso;
    }

    public Pedido(Long idVinho, Long idCliente, String canalVenda, String regiaoEntrega,
                  Integer quantidade, Double precoUnitario, Integer descontoAplicado,
                  Timestamp dataVenda, String temporada, Integer avaliacaoCliente, Boolean vendaSucesso) {
        this.idVinho = idVinho;
        this.idCliente = idCliente;
        this.canalVenda = canalVenda;
        this.regiaoEntrega = regiaoEntrega;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.descontoAplicado = descontoAplicado;
        this.dataVenda = dataVenda;
        this.temporada = temporada;
        this.avaliacaoCliente = avaliacaoCliente;
        this.vendaSucesso = vendaSucesso;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdVinho() { return idVinho; }
    public void setIdVinho(Long idVinho) { this.idVinho = idVinho; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public String getCanalVenda() { return canalVenda; }
    public void setCanalVenda(String canalVenda) { this.canalVenda = canalVenda; }

    public String getRegiaoEntrega() { return regiaoEntrega; }
    public void setRegiaoEntrega(String regiaoEntrega) { this.regiaoEntrega = regiaoEntrega; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }

    public Integer getDescontoAplicado() { return descontoAplicado; }
    public void setDescontoAplicado(Integer descontoAplicado) { this.descontoAplicado = descontoAplicado; }

    public Timestamp getDataVenda() { return dataVenda; }
    public void setDataVenda(Timestamp dataVenda) { this.dataVenda = dataVenda; }

    public String getTemporada() { return temporada; }
    public void setTemporada(String temporada) { this.temporada = temporada; }

    public Integer getAvaliacaoCliente() { return avaliacaoCliente; }
    public void setAvaliacaoCliente(Integer avaliacaoCliente) { this.avaliacaoCliente = avaliacaoCliente; }

    public Boolean getVendaSucesso() { return vendaSucesso; }
    public void setVendaSucesso(Boolean vendaSucesso) { this.vendaSucesso = vendaSucesso; }
}
