package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente extends Conexao {

    protected static void create(
        int rg,
        String nome,
        TipoCliente entrada
    ) throws IllegalArgumentException, SQLException {
        validarRg(rg);
        float valorEntrada = entrada.getValorEntrada();
        try {
            execute(
                "INSERT INTO `cliente`(" +
                    "`rg`, " +
                    "`nome`, " +
                    "`tipo_entrada`, " +
                    "`valor_entrada` " +
                ") VALUES (" +
                    "'" + rg + "', " +
                    "'" + nome + "', " +
                    "'" + entrada + "', " +
                    "'" + valorEntrada + "' " +
                ");"
            );
        } catch (SQLException e){
            throw new SQLException("Poxa, parece que houve um erro ao cadastrar esse Cliente, confira os dados e tente novamente!");
        }
    }

    /**
     * Read sem parametros
     * @return todos os registros de produto na base de dados
     */
    public static ResultSet read() throws SQLException {
        return read("`nome` ASC");
    }

    /**
     * Read com ORDER BY
     * @param orderBy tipo de Ordenação
     * @return todos os registros ordenados
     */
    public static ResultSet read(String orderBy) throws SQLException {
        return Conexao.read("cliente", orderBy);
    }

    /**
     * Read com id
     * @param rg = rg do cliente
     * @return apenas os campos referentes ao produto vinculado ao id
     */

    public static ResultSet read(int rg) throws SQLException {
        PreparedStatement prepare = con.prepareStatement("SELECT * FROM `cliente` WHERE rg = " + rg);
        ResultSet result = prepare.executeQuery();
        result.next();
        return result;
    }

    public static void delete(int rg) throws SQLException {
        try {
            execute("DELETE FROM cliente WHERE rg = '" + rg + "'");
        } catch (SQLException e){
            throw new SQLException("Poxa, houve um erro ao deletar este cliente.");
        }
    }

    public static void validarRg(int rg) throws IllegalArgumentException{
        String validRg = Integer.toString(rg);
        if(validRg.length() != 10) throw new IllegalArgumentException("RG inválido");
    }

}
