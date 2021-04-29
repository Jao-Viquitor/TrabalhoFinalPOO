package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Consumo extends Conexao {
    public static void create(
        int rg,
        int idProduto,
        int quantidadeProduto
    ) throws IllegalArgumentException, SQLException {
        Cliente.validarRg(rg);
        ResultSet informacoesProduto = Produto.read(idProduto);

        if(quantidadeProduto > informacoesProduto.getInt("quantidade"))
            throw new IllegalArgumentException("Não há estoque o suficiente para esta demanda.");

        if(!Cliente.read(rg).getString("tipo_entrada").equals(TipoCliente.VIP.toString())){
            float valorConsumo = informacoesProduto.getFloat("valor_venda") * quantidadeProduto;

            if(valorConsumo > CamarotePista.readCredito(rg))
                throw new IllegalArgumentException("O cliente não possui créditos suficientes para esta demanda");
            CamarotePista.diminuirCredito(rg, valorConsumo);
        }

        Produto.diminuiEstoque(idProduto, quantidadeProduto);
        try {
            execute(
                "INSERT INTO `consumo`(" +
                    "`cliente_rg`, " +
                    "`produto_id`, " +
                    "`quantidade` " +
                ") VALUES (" +
                    "'" + rg + "', " +
                    "'" + idProduto + "', " +
                    "'" + quantidadeProduto + "' " +
                ");"
            );
        } catch (SQLException e){
            throw new SQLException("Poxa, parece que houve um erro ao cadastrar esse consumo, tente novamente. ");
        }
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
                "SUM((`valor_venda` - `valor_custo`) * `consumo`.`quantidade`) AS `lucro`, " +
                "SUM(`valor_custo`) AS `custo` " +
            "FROM " +
                "`produto` INNER JOIN `consumo` ON `produto`.`id` = `consumo`.`produto_id`; "
        ).executeQuery();
        result.next();
        return result;
    }
}