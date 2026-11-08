package br.com.vinheria.pedidos.entities;

import java.sql.Timestamp;

// Representa um cliente que entra em contato com a vinheria
public class Cliente {

    private Long id;
    private String nome;
    private String email;
    private String assunto;
    private String mensagem;
    private Timestamp dataEnvio;

    public Cliente() {}

    public Cliente(Long id, String nome, String email, String assunto, String mensagem, Timestamp dataEnvio) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
    }

    public Cliente(String nome, String email, String assunto, String mensagem, Timestamp dataEnvio) {
        this.nome = nome;
        this.email = email;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public Timestamp getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(Timestamp dataEnvio) { this.dataEnvio = dataEnvio; }
}
