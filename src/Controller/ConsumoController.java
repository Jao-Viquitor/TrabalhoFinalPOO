package Controller;

import Model.CamarotePista;
import Model.Cliente;
import Model.Consumo;
import Model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ConsumoController extends GeneralController {

    @FXML private TextField buscaRG, nomeCliente, pesquisarProduto, nomeProduto, valor, quantidade;
    @FXML private CheckBox continuar;
    @FXML private Label total, credito, creditoRestante;
    @FXML private ListView<String> listConsumo;
    float valor_produto;


    @FXML void procuraConsumoRG(){
        buscaRG();
        if(total == null) total = new Label();
        try {
            ResultSet cliente = Consumo.consumidosCliente(buscaRG.getText());
            nomeCliente.setText(cliente.getString("nome"));
            total.setText("R$ " + cliente.getString("total"));
            credito.setText("R$ " + cliente.getString("credito"));
            creditoRestante.setText("R$ " + (cliente.getFloat("credito") - cliente.getFloat("total")));

        } catch (SQLException e) {
            nomeCliente.setText("RG não encontrado");
            total.setText("R$ 0.0");
            credito.setText("R$ 0.0");
            creditoRestante.setText("R$ 0.0");
        }
    }
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
                buscaRG.getText(),
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

    void mostraTabela(){
        if(listConsumo == null) listConsumo = new ListView<>();
        listConsumo.getItems().clear();
        try {
            ResultSet clientes = Cliente.read();
            while (clientes.next()){
                listConsumo.getItems().add(
                        clientes.getString("rg") + " - " +
                                clientes.getString("nome") + " - (" +
                                clientes.getString("tipo_entrada") + ")"
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML void maskQuantidade() {
        quantidade.setText(quantidade.getText().replaceAll("[^0-9]", ""));
        quantidade.positionCaret(quantidade.getLength());
        calculaValor();
    }

    @FXML void maskPreco(){
        valor.setText(valor.getText().replaceAll(",", "."));
        valor.setText(valor.getText().replaceAll("[^0-9.]", ""));
        valor.positionCaret(valor.getLength());
    }

    @FXML void confirmarPgConsumo(){

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
