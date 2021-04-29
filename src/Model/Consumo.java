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
}