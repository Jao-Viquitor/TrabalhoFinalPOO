package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CamarotePista extends Cliente {
    protected static void create(
        String rg,
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


    public static ResultSet read(String rg) throws SQLException {
        try {
            PreparedStatement prepare = con.prepareStatement("SELECT * FROM `cliente` INNER JOIN `camarote_pista` ON `cliente`.`rg` = `camarote_pista`.`rg` WHERE `cliente`.`rg` = " + rg);
            ResultSet result = prepare.executeQuery();
            result.next();
            return result;
        } catch (SQLException e){
            throw new SQLException("Cliente não é VIP");
        }
    }
    public static float readCredito(String rg) throws SQLException {
        PreparedStatement prepare = con.prepareStatement("SELECT `credito` FROM `cliente` INNER JOIN `camarote_pista` ON `cliente`.`rg` = `camarote_pista`.`rg` WHERE `cliente`.`rg` = " + rg);
        ResultSet result = prepare.executeQuery();
        result.next();
        return result.getFloat("credito");
    }

    public static void diminuirCredito(String rg, float credito) throws SQLException {
        try {
            execute(
                "UPDATE `camarote_pista` SET " +
                    "`credito` = `credito` - " + credito +" "+
                    "WHERE rg = '" + rg + "'"
            );
        }catch (SQLException e){
            throw new SQLException("RG não encontrado");
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
