package dao;

import connection.Conexao;
import model.Reuniao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pelo acesso ao banco de dados (CRUD de Reuniao)
public class ReuniaoDAO {

    // Insere uma nova reunião no banco
    public boolean inserir(Reuniao r) {
        String sql = "INSERT INTO reuniao (cliente_nome, vendedor_nome, data_reuniao, " +
                     "duracao_minutos, transcricao, sentimento, tempo_fala_vendedor, " +
                     "tempo_fala_cliente, produto_relacionado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = Conexao.getConexao();
        if (conn == null) {
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, r.getClienteNome());
            stmt.setString(2, r.getVendedorNome());
            stmt.setString(3, r.getDataReuniao());
            stmt.setInt(4, r.getDuracaoMinutos());
            stmt.setString(5, r.getTranscricao());
            stmt.setString(6, r.getSentimento());
            stmt.setInt(7, r.getTempoFalaVendedor());
            stmt.setInt(8, r.getTempoFalaCliente());
            stmt.setString(9, r.getProdutoRelacionado());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        r.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir reunião: " + e.getMessage());
            return false;
        } finally {
            Conexao.fecharConexao(conn);
        }
    }

    // Busca uma reunião pelo ID
    public Reuniao buscarPorId(int id) {
        String sql = "SELECT id, cliente_nome, vendedor_nome, data_reuniao, duracao_minutos, " +
                     "transcricao, sentimento, tempo_fala_vendedor, tempo_fala_cliente, " +
                     "produto_relacionado FROM reuniao WHERE id = ?";

        Connection conn = Conexao.getConexao();
        if (conn == null) {
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar reunião por ID: " + e.getMessage());
            return null;
        } finally {
            Conexao.fecharConexao(conn);
        }
    }

    // Retorna todas as reuniões
    public List<Reuniao> listarTodas() {
        List<Reuniao> lista = new ArrayList<>();
        String sql = "SELECT id, cliente_nome, vendedor_nome, data_reuniao, duracao_minutos, " +
                     "transcricao, sentimento, tempo_fala_vendedor, tempo_fala_cliente, " +
                     "produto_relacionado FROM reuniao ORDER BY id ASC";

        Connection conn = Conexao.getConexao();
        if (conn == null) {
            return lista;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar reuniões: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(conn);
        }

        return lista;
    }

    // Atualiza os dados de uma reunião
    public boolean atualizar(Reuniao r) {
        String sql = "UPDATE reuniao SET cliente_nome = ?, vendedor_nome = ?, data_reuniao = ?, " +
                     "duracao_minutos = ?, transcricao = ?, sentimento = ?, tempo_fala_vendedor = ?, " +
                     "tempo_fala_cliente = ?, produto_relacionado = ? WHERE id = ?";

        Connection conn = Conexao.getConexao();
        if (conn == null) {
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getClienteNome());
            stmt.setString(2, r.getVendedorNome());
            stmt.setString(3, r.getDataReuniao());
            stmt.setInt(4, r.getDuracaoMinutos());
            stmt.setString(5, r.getTranscricao());
            stmt.setString(6, r.getSentimento());
            stmt.setInt(7, r.getTempoFalaVendedor());
            stmt.setInt(8, r.getTempoFalaCliente());
            stmt.setString(9, r.getProdutoRelacionado());
            stmt.setInt(10, r.getId());

            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar reunião: " + e.getMessage());
            return false;
        } finally {
            Conexao.fecharConexao(conn);
        }
    }

    // Exclui uma reunião pelo ID
    public boolean deletar(int id) {
        String sql = "DELETE FROM reuniao WHERE id = ?";

        Connection conn = Conexao.getConexao();
        if (conn == null) {
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar reunião: " + e.getMessage());
            return false;
        } finally {
            Conexao.fecharConexao(conn);
        }
    }

    // Método auxiliar para converter ResultSet em objeto Reuniao
    private Reuniao mapearResultSet(ResultSet rs) throws SQLException {
        return new Reuniao(
            rs.getInt("id"),
            rs.getString("cliente_nome"),
            rs.getString("vendedor_nome"),
            rs.getString("data_reuniao"),
            rs.getInt("duracao_minutos"),
            rs.getString("transcricao"),
            rs.getString("sentimento"),
            rs.getInt("tempo_fala_vendedor"),
            rs.getInt("tempo_fala_cliente"),
            rs.getString("produto_relacionado")
        );
    }
}
