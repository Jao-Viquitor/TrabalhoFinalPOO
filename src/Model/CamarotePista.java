package Model;

import java.sql.SQLException;

class CamarotePista extends Cliente {
    protected static void create(
        int rg,
        String nome,
        TipoCliente entrada,
        float credito
    ) throws IllegalArgumentException, SQLException {
        if (credito >= 0)
            throw new IllegalArgumentException("O valor de crédito não pode ser negativo");

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

    public static void adicionarCredito(int rg, float credito) throws SQLException {
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
