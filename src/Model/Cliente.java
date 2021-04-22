package Model;

import java.sql.SQLException;

public class Cliente extends Conexao {

    protected static void create(
        int rg,
        String nome,
        TipoCliente entrada
    ) throws IllegalArgumentException, SQLException {
        String validRg = Integer.toString(rg);
        if(validRg.length() != 10) throw new IllegalArgumentException("RG inválido");
        float valorEntrada = entrada.getValorEntrada();

        try {
            execute(
                "INSERT INTO `cliente`(" +
                    "`rg`, " +
                    "`nome`, " +
                    "`valor_entrada` " +
                ") VALUES (" +
                    "'" + rg + "', " +
                    "'" + nome + "', " +
                    "'" + valorEntrada + "' " +
                ");"
            );
        } catch (SQLException e){
            throw new SQLException("Poxa, parece que houve um erro ao cadastrar esse Cliente, confira os dados e tente novamente!");
        }
    }

    protected static void delete(int rg) throws SQLException {
        try {
            execute("DELETE FROM cliente WHERE rg = '" + rg + "'");
        } catch (SQLException e){
            throw new SQLException("Poxa, houve um erro ao deletar este cliente.");
        }
    }

}
