package Model;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
    protected static java.sql.Connection con;

    static {
        String host = "jdbc:mysql://localhost:3306/trabalhoFinalPOO";
        String root = "root";
        String pswd = "Linux21.";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(host, root, pswd);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executa algum comando sql sem retorno
     * @param sql query para a base de dados
     */
    protected static void execute(String sql) throws SQLException {
        con.prepareStatement(sql).execute();
    }

    /**
     * Read de todas os registros de determinada tabela
     * @param table nome da tabela que eu quero pesquisar
     * @return retorna um ResultSet do que foi encontrado
     */
    protected static ResultSet read(String table, String orderBy) throws SQLException {
        return read(table, "1", orderBy);
    }


    protected static ResultSet read(String table, String where, String orderBy) throws SQLException {
        ResultSet result = con.prepareStatement(
                "SELECT * FROM " + table + " WHERE " + where + " ORDER BY " + orderBy
        ).executeQuery();
        result.next();
        return result;
    }

    /**
     * Read de um único registro de determinada tabela
     * @param table nome da tabela que eu quero pesquisar
     * @param id identificador do objeto que eu quero buscar
     * @return retorna um ResultSet de um único objeto que foi encontrado
     */
    protected static ResultSet read(String table, int id) throws SQLException {
        PreparedStatement prepare = con.prepareStatement("SELECT * FROM " + table + " WHERE id = " + id);
        ResultSet result = prepare.executeQuery();
        result.next();
        return result;
    }

    protected static void delete(String table, int id) throws SQLException {
        execute("DELETE FROM " + table + " WHERE id = " + id);
    }
}
