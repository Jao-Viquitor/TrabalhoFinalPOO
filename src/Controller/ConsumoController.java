package Controller;

import Model.Consumo;
import Model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;


public class ConsumoController extends GeneralController {

    @FXML private TextField buscaRG, pesquisarProduto, nomeProduto, valor, quantidade;
    @FXML private CheckBox continuar;
    float valor_produto;

    @FXML
    void buscaRG() {
        try {
            buscaRG.setText(buscaRG.getText().replaceAll("[^0-9]", ""));
            buscaRG.positionCaret(buscaRG.getLength());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML void confirmar() {
        try {
            Consumo.create(
                Integer.parseInt(buscaRG.getText()),
                Integer.parseInt(pesquisarProduto.getText()),
                Integer.parseInt(quantidade.getText())
            );
            if(continuar.isSelected()){
                buscaRG.setText("");
                pesquisarProduto.setText("");
                nomeProduto.setText("");
                quantidade.setText("");
                valor.setText("");
            }else {
                cancelar();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML void maskQuantidade() {
        quantidade.setText(quantidade.getText().replaceAll("[^0-9]", ""));
        quantidade.positionCaret(quantidade.getLength());
        calculaValor();
    }

    @FXML void buscaIdProduto() {
        pesquisarProduto.setText(pesquisarProduto.getText().replaceAll("[^0-9]", ""));
        pesquisarProduto.positionCaret(pesquisarProduto.getLength());
        try {
            ResultSet produto = Produto.read(
                Integer.parseInt(
                    pesquisarProduto.getText()
                )
            );
            nomeProduto.setText(
                produto.getString("Titulo")
            );
            valor_produto = produto.getFloat("valor_venda");
            calculaValor();

        } catch (Exception e) {
            valor_produto = 0;
            calculaValor();
            if(!nomeProduto.getText().isBlank()){
                nomeProduto.setText("");
                return;
            }
            nomeProduto.setText("Código não encontrado");

        }
    }
    void calculaValor(){
        int qtd = 1;
        if(quantidade.getText().isEmpty()){ quantidade.setText("1"); qtd = 1; }

        valor.setText("R$" + valor_produto * qtd);
    }

}
