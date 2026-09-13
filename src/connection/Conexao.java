package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "jdbc:h2:./lynn_db;AUTO_SERVER=TRUE";
    private static final String USUARIO = "sa";
    private static final String SENHA = "sa";
    private static final String DRIVER = "org.h2.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do H2 não encontrado: " + e.getMessage());
        }
    }

    // Abre e retorna a conexão com o banco de dados
    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar no banco de dados: " + e.getMessage());
            return null;
        }
    }

    // Fecha a conexão aberta
    public static void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Cria a tabela caso não exista para facilitar a execução dos testes
    public static void inicializarBanco() {
        String sql = "CREATE TABLE IF NOT EXISTS reuniao (" +
                     "  id INT AUTO_INCREMENT PRIMARY KEY," +
                     "  cliente_nome VARCHAR(255) NOT NULL," +
                     "  vendedor_nome VARCHAR(255) NOT NULL," +
                     "  data_reuniao VARCHAR(10) NOT NULL," +
                     "  duracao_minutos INT NOT NULL," +
                     "  transcricao CLOB," +
                     "  sentimento VARCHAR(20) NOT NULL," +
                     "  tempo_fala_vendedor INT NOT NULL," +
                     "  tempo_fala_cliente INT NOT NULL," +
                     "  produto_relacionado VARCHAR(50) NOT NULL" +
                     ");";

        try (Connection conn = getConexao()) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar tabela no banco: " + e.getMessage());
        }
    }
}
