package br.com.vinheria.pedidos.repository;

import br.com.vinheria.pedidos.entities.Cliente;
import br.com.vinheria.pedidos.infra.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {

    private final Connection conexao;

    public ClienteRepository() throws SQLException {
        this.conexao = DatabaseConnection.getConnection();
    }

    private Cliente parseCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getLong("id"));
        c.setNome(rs.getString("nome"));
        c.setEmail(rs.getString("email"));
        c.setAssunto(rs.getString("assunto"));
        c.setMensagem(rs.getString("mensagem"));
        c.setDataEnvio(rs.getTimestamp("data_envio"));
        return c;
    }

    public List<Cliente> findAll() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM tb_clientes ORDER BY data_envio DESC"
        );
        ResultSet rs = stm.executeQuery();

        List<Cliente> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(parseCliente(rs));
        }
        return lista;
    }

    public Optional<Cliente> findById(Long id) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM tb_clientes WHERE id = ?"
        );
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            return Optional.of(parseCliente(rs));
        }
        return Optional.empty();
    }

    public void create(Cliente cliente) throws SQLException {
        String sql =
                "BEGIN " +
                "   INSERT INTO tb_clientes (id, nome, email, assunto, mensagem, data_envio) " +
                "   VALUES (seq_tb_clientes.nextval, ?, ?, ?, ?, ?) " +
                "   RETURNING id INTO ?; " +
                "END;";

        CallableStatement cs = conexao.prepareCall(sql);
        cs.setString(1, cliente.getNome());
        cs.setString(2, cliente.getEmail());
        cs.setString(3, cliente.getAssunto());
        cs.setString(4, cliente.getMensagem());
        cs.setTimestamp(5, cliente.getDataEnvio());
        cs.registerOutParameter(6, Types.NUMERIC);

        cs.execute();
        cliente.setId(cs.getLong(6));
    }

    public void closeConnection() throws SQLException {
        conexao.close();
    }
}
