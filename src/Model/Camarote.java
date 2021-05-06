package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Camarote extends CamarotePista {
    public static void create(
        String rg,
        String nome,
        float credito
    ) throws IllegalArgumentException, SQLException {
        CamarotePista.create(rg, nome, TipoCliente.CAMAROTE, credito);
    }

    /**
     * Read com ORDER BY
     * @param orderBy tipo de Ordenação
     * @return todos os registros ordenados
     */
    public static ResultSet read(String orderBy) throws SQLException {
        return Conexao.read(
            "cliente INNER JOIN `camarote_pista` ON cliente.rg = camarote_pista.rg",
            "`valor_entrada` = " + TipoCliente.CAMAROTE.getValorEntrada(),
            orderBy
        );
    }
    public static ResultSet read() throws SQLException {
        return read("nome ASC");
    }
}
