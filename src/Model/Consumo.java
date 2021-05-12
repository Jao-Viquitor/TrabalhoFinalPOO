package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Consumo extends Conexao {
    public static void create(
        String rg,
        int idProduto,
        int quantidadeProduto
    ) throws IllegalArgumentException, SQLException {
        ResultSet informacoesProduto = Produto.read(idProduto);

        if(quantidadeProduto > informacoesProduto.getInt("quantidade"))
            throw new IllegalArgumentException("Não há estoque o suficiente para esta demanda.");

        if(!Cliente.read(rg).getString("tipo_entrada").equals(TipoCliente.VIP.toString())){
            float valorConsumo = informacoesProduto.getFloat("valor_venda") * quantidadeProduto;

            if(valorConsumo > CamarotePista.readCredito(rg))
                throw new IllegalArgumentException("O cliente não possui créditos suficientes para esta demanda");
//            CamarotePista.diminuirCredito(rg, valorConsumo);
        }

        Produto.diminuiEstoque(idProduto, quantidadeProduto);
        try {
            execute(
                "INSERT INTO `consumo`(" +
                    "`cliente_rg`, " +
                    "`produto_id`, " +
                    "`quantidade`, " +
                    "`pago` " +
                ") VALUES (" +
                    "'" + rg + "', " +
                    "'" + idProduto + "', " +
                    "'" + quantidadeProduto + "', " +
                    "0" +
                ");"
            );
        } catch (SQLException e){
            throw new SQLException("Poxa, parece que houve um erro ao cadastrar esse consumo, tente novamente. ");
        }
    }

    public static ResultSet consumidosCliente(String rg) throws SQLException {
        ResultSet result = con.prepareStatement(
            "SELECT `cliente`.`nome` AS `nome`, `camarote_pista`.`credito`, SUM(`produto`.`valor_venda` * `consumo`.`quantidade`) AS `total` " +
            "FROM `consumo` " +
                "INNER JOIN `cliente` ON `cliente`.`rg` = `consumo`.`cliente_rg` " +
                "INNER JOIN `camarote_pista` ON `camarote_pista`.`rg` = `consumo`.`cliente_rg` " +
                "INNER JOIN `produto` ON `produto`.`id` = `consumo`.`produto_id` " +
            "WHERE `pago` = 0 AND `cliente_rg` = '" + rg + "' " +
            "GROUP BY `cliente_rg`"
        ).executeQuery();
        result.next();
        return result;
    }

    /**
     * Análise de lucro vs custos
     * @return ResultSet com duas colunas: titulo e quantidade
     * @throws SQLException
     */
    public static ResultSet consumidos() throws SQLException {
        ResultSet result = con.prepareStatement(
                "SELECT " +
                    "`titulo`," +
                    "SUM(`consumo`.`quantidade`) AS `quantidade`" +
                "FROM" +
                    "`produto` INNER JOIN `consumo` ON `produto`.`id` = `consumo`.`produto_id`" +
                "GROUP BY" +
                    "`produto`.`id`; "
        ).executeQuery();
        result.next();
        return result;
    }

    /**
     * Análise de lucro vs custos
     * @return ResultSet com duas colunas: custo e lucro
     * @throws SQLException
     */
    public static ResultSet analytics() throws SQLException {
        ResultSet result = con.prepareStatement(
            "SELECT " +
                "ROUND(SUM((`valor_venda` - `valor_custo`) * `consumo`.`quantidade`), 2) + (SELECT SUM(`valor_entrada`) FROM `cliente`)  AS `lucro`, " +
                "ROUND(SUM(`valor_custo`), 2) AS `custo` " +
            "FROM " +
                "`produto` INNER JOIN `consumo` ON `produto`.`id` = `consumo`.`produto_id`; "
        ).executeQuery();
        result.next();
        return result;
    }

    public static void pagarConsumo(String rg, Float total) throws SQLException {
        CamarotePista.diminuirCredito(rg, total);
        execute("UPDATE `consumo` SET pago = 1 WHERE `cliente_rg` = '" + rg + "'");
    }
}