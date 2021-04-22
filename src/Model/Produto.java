package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Produto extends Conexao {

    public static void create(
        String titulo,
        int quantidade,
        float valorCusto,
        float valorVenda
    ) throws SQLException {
        execute(
            "INSERT INTO `produto`(" +
                "`titulo`, " +
                "`quantidade`, " +
                "`valor_custo`, " +
                "`valor_venda`" +
            ") VALUES (" +
                "'" + titulo + "', " +
                "'" + quantidade + "', " +
                "'" + valorCusto + "', " +
                "'" + valorVenda + "' " +
            ");"
        );
    }

    /**
     * Read sem parametros
     * @return todos os registros de produto na base de dados
     * @throws SQLException
     */
    public static ResultSet read() throws SQLException {
        return Conexao.read("produto");
    }

    /**
     * Read com id
     * @param id = identificador do produto
     * @return apenas os campos referentes ao produto vinculado ao id
     * @throws SQLException
     */
    public static ResultSet read(int id) throws SQLException {
        return Conexao.read("produto", id);
    }

    public static void update(
        String titulo,
        int quantidade,
        float valorCusto,
        float valorVenda,
        int id
    ) throws SQLException {
        execute(
            "UPDATE `produto` SET " +
            "`titulo` = '" + titulo +"', " +
            "`quantidade` = '" + quantidade +"', " +
            "`valor_custo` = '" + valorCusto +"', " +
            "`valor_venda` = '" + valorVenda +"'" +
            "WHERE id = " + id
        );
    }

    public static void delete(int id) throws SQLException {
        Conexao.delete("produto", id);
    }
}
