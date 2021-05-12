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
     */
    public static ResultSet read() throws SQLException {
        return read("`id` ASC");
    }

    /**
     * Read com ORDER BY
     * @param orderBy tipo de Ordenação
     * @return todos os registros ordenados
     */
    public static ResultSet read(String orderBy) throws SQLException {
        return Conexao.read("produto", orderBy);
    }

    /**
     * Read com id
     * @param id = identificador do produto
     * @return apenas os campos referentes ao produto vinculado ao id
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
        try {
            execute(
                "UPDATE `produto` SET " +
                    "`titulo` = '" + titulo +"', " +
                    "`quantidade` = '" + quantidade + "', " +
                    "`valor_custo` = '" + valorCusto + "', " +
                    "`valor_venda` = '" + valorVenda + "' " +
                    "WHERE id = " + id
            );
        } catch (SQLException e){
            throw new SQLException("Produto não encontrado. ");
        }
    }

    public static void delete(int id) throws SQLException {
        Conexao.delete("produto", id);
    }

    public static void aumentaEstoque(int id, int quantidade) throws IllegalArgumentException, SQLException {
        if(quantidade <= 0)
            throw new IllegalArgumentException("A quantidade precisa ser positiva! ");

        execute(
            "UPDATE `produto` SET " +
            "`quantidade` = `quantidade` + " + quantidade + " " +
            "WHERE id = " + id
        );
    }

    public static void diminuiEstoque(int id, int quantidade) throws IllegalArgumentException, SQLException {
        if(quantidade <= 0)
            throw new IllegalArgumentException("A quantidade precisa ser positiva! ");

        execute(
            "UPDATE `produto` SET " +
            "`quantidade` = `quantidade` - " + quantidade + " " +
            "WHERE id = " + id
        );
    }

    public static int nextId() throws SQLException {
        ResultSet resultSet =  con.prepareStatement(
                "SELECT MAX(`id`) + 1 AS next_id FROM `produto`"
        ).executeQuery();
        resultSet.next();
        return resultSet.getInt("next_id");
    }
}
