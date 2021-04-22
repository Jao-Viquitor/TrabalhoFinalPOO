package Model;

import java.sql.SQLException;

public class CamarotePista extends Cliente{
    public static void create(
        int rg,
        String nome,
        TipoCliente entrada,
        float credito
    ) throws IllegalArgumentException, SQLException {
        if (entrada.equals(TipoCliente.VIP))
            throw new IllegalArgumentException("Esse usuário é VIP");

        try {
            Cliente.create(rg, nome, entrada);
            execute(
                "INSERT INTO `camarote_pista`(" +
                    "`rg`, " +
                    "`credito` " +
                ") VALUES (" +
                    "'" + rg + "', " +
                    "'" + credito + "' " +
                ");"
            );
        } catch (SQLException e){
            throw new SQLException("Poxa, parece que houve um erro ao cadastrar esse Cliente, confira os dados e tente novamente!");
        }
    }

    public static void adicionarCreditos(int rg, float credito) throws SQLException {
        try {
            execute(
                "UPDATE `camarote_pista` SET " +
                "`credito` = `credito` + " + credito +" "+
                "WHERE rg = '" + rg + "'"
            );
        }catch (SQLException e){
            throw new SQLException("RG não encontrado");
        }
    }
}
