package Model;

import java.sql.SQLException;

public class Vip extends Cliente{
    public static void create(
        int rg,
        String nome
    ) throws IllegalArgumentException, SQLException {
        Cliente.create(rg, nome, TipoCliente.VIP);
    }
    public static void delete(int rg) throws SQLException {
        Cliente.delete(rg);
    }
}
