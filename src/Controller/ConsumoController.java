package Controller;

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
    @FXML private Label total, credito, creditoRestante, nomeClienteFiltro, totalFiltro;
    @FXML private ListView<String> listConsumo;
    float valor_produto;

    @FXML
    void initialize() {
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("MenuConsumo")){
                mostraTabela();
            }
        });
    }

    void mostraTabela(){
        buscaRG.setText("");
        nomeClienteFiltro.setText("");
        if(listConsumo == null) listConsumo = new ListView<>();
        listConsumo.getItems().clear();
        try {
            ResultSet consumidos = Consumo.consumidos();
            while (consumidos.next())
                listConsumo.getItems().add(
                    "#" + consumidos.getString("id") + " - " +
                    consumidos.getString("titulo") + "(" + consumidos.getString("quantidade") + ")"
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            nomeCliente.setText(
                Cliente.read(
                    buscaRG.getText()
                ).getString("nome")
            );

        } catch (Exception e) {
            nomeCliente.setText("RG inválido para esta operação");
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
                MainController.changeScreen();
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

    @FXML void maskPreco(){
        valor.setText(valor.getText().replaceAll(",", "."));
        valor.setText(valor.getText().replaceAll("[^0-9.]", ""));
        valor.positionCaret(valor.getLength());
    }

    @FXML void confirmarPgConsumo(){
        try {
            String[] split = total.getText().split(" ");
            Consumo.pagarConsumo(buscaRG.getText(), Float.parseFloat(split[1]));
            MainController.changeScreen("MenuConsumo");
            cancelar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @FXML void filtroRG(){
        try {
            buscaRG.setText(buscaRG.getText().replaceAll("[^0-9]", ""));
            buscaRG.positionCaret(buscaRG.getLength());
            listConsumo.getItems().clear();
            if(buscaRG.getText().isEmpty()){
                nomeClienteFiltro.setText("");
                mostraTabela(); return;
            }
            ResultSet consumidos = Consumo.consumidos(buscaRG.getText());
            ResultSet cliente = Cliente.read(buscaRG.getText());
            nomeClienteFiltro.setText(
                cliente.getString("nome") +
                " [" +
                    cliente.getString("tipo_entrada") +
                    " - R$ " + cliente.getString("valor_entrada") +
                "]"
            );
            float total = 0;
            while (consumidos.next()) {
                listConsumo.getItems().add(
                    consumidos.getString("titulo") + " - qtd.: " + consumidos.getString("quantidade") + " - Valor: R$ " + consumidos.getString("total")
                );
                total += consumidos.getFloat("total");
            }

            total += cliente.getFloat("valor_entrada");
            if(!cliente.getString("tipo_entrada").equals("VIP")){
                totalFiltro.setText("Total: R$ " + total);
            }
        } catch (SQLException e) {
            totalFiltro.setText("");
            nomeClienteFiltro.setText("");
        }
    }
}
