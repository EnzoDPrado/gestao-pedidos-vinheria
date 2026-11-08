package br.com.vinheria.pedidos.repository;

import br.com.vinheria.pedidos.entities.Pedido;
import br.com.vinheria.pedidos.infra.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoRepository {

    private final Connection conexao;

    public PedidoRepository() throws SQLException {
        this.conexao = DatabaseConnection.getConnection();
    }

    private Pedido parsePedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setId(rs.getLong("id"));
        p.setIdVinho(rs.getLong("id_vinho"));

        long idCliente = rs.getLong("id_cliente");
        p.setIdCliente(rs.wasNull() ? null : idCliente);

        p.setCanalVenda(rs.getString("canal_venda"));
        p.setRegiaoEntrega(rs.getString("regiao_entrega"));
        p.setQuantidade(rs.getInt("quantidade"));
        p.setPrecoUnitario(rs.getDouble("preco_unitario"));

        int desconto = rs.getInt("desconto_aplicado");
        p.setDescontoAplicado(rs.wasNull() ? null : desconto);

        p.setDataVenda(rs.getTimestamp("data_venda"));
        p.setTemporada(rs.getString("temporada"));

        int avaliacao = rs.getInt("avaliacao_cliente");
        p.setAvaliacaoCliente(rs.wasNull() ? null : avaliacao);

        p.setVendaSucesso(rs.getInt("venda_sucesso") == 1);
        return p;
    }

    public List<Pedido> findAll() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM tb_pedidos ORDER BY data_venda DESC"
        );
        ResultSet rs = stm.executeQuery();

        List<Pedido> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(parsePedido(rs));
        }
        return lista;
    }

    public Optional<Pedido> findById(Long id) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM tb_pedidos WHERE id = ?"
        );
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            return Optional.of(parsePedido(rs));
        }
        return Optional.empty();
    }

    public void create(Pedido pedido) throws SQLException {
        String sql =
                "BEGIN " +
                "   INSERT INTO tb_pedidos (id, id_vinho, id_cliente, canal_venda, regiao_entrega, " +
                "       quantidade, preco_unitario, desconto_aplicado, data_venda, temporada, " +
                "       avaliacao_cliente, venda_sucesso) " +
                "   VALUES (seq_tb_pedidos.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "   RETURNING id INTO ?; " +
                "END;";

        CallableStatement cs = conexao.prepareCall(sql);
        cs.setLong(1, pedido.getIdVinho());

        if (pedido.getIdCliente() != null) {
            cs.setLong(2, pedido.getIdCliente());
        } else {
            cs.setNull(2, Types.NUMERIC);
        }

        cs.setString(3, pedido.getCanalVenda());
        cs.setString(4, pedido.getRegiaoEntrega());
        cs.setInt(5, pedido.getQuantidade());
        cs.setDouble(6, pedido.getPrecoUnitario());

        if (pedido.getDescontoAplicado() != null) {
            cs.setInt(7, pedido.getDescontoAplicado());
        } else {
            cs.setNull(7, Types.NUMERIC);
        }

        cs.setTimestamp(8, pedido.getDataVenda());
        cs.setString(9, pedido.getTemporada());

        if (pedido.getAvaliacaoCliente() != null) {
            cs.setInt(10, pedido.getAvaliacaoCliente());
        } else {
            cs.setNull(10, Types.NUMERIC);
        }

        cs.setInt(11, pedido.getVendaSucesso() ? 1 : 0);
        cs.registerOutParameter(12, Types.NUMERIC);

        cs.execute();
        pedido.setId(cs.getLong(12));
    }

    public void delete(Long id) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "DELETE FROM tb_pedidos WHERE id = ?"
        );
        stm.setLong(1, id);
        int rows = stm.executeUpdate();
        if (rows == 0) {
            throw new RuntimeException("Pedido nao encontrado para deletar");
        }
    }

    public void closeConnection() throws SQLException {
        conexao.close();
    }
}
