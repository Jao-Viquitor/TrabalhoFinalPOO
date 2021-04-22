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

    public static ResultSet read() throws SQLException {
        return Conexao.read("produto");
    }

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
