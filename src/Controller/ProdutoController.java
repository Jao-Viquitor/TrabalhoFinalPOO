package Controller;

import Model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProdutoController extends GeneralController{
    @FXML private Label idProduto;
    @FXML private TextField descricao, quantidade, precoCusto, precoVenda, idProdutoFiltro;
    @FXML private ListView<String> listProdutos;
    private static int idProdutoRead = 0;

    @FXML
    void initialize() throws SQLException {
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("MenuProdutos")) {
                mostraTabela();
            }
        });
        if(modalScreen){
            if(idProdutoRead > 0){
                insertIntoRead();
            } else {
                if(idProduto == null) idProduto = new Label();
                idProduto.setText(Integer.toString(Produto.nextId()));
            }
            modalScreen = false;
        }
    }

    private void insertIntoRead() {
        try {
            ResultSet produto = Produto.read(idProdutoRead);
            idProduto.setText(produto.getString("id"));
            descricao.setText(produto.getString("titulo"));
            quantidade.setText(produto.getString("quantidade"));
            precoCusto.setText(produto.getString("valor_custo"));
            precoVenda.setText(produto.getString("valor_venda"));
        } catch (SQLException e) {
            alerta();
        }
    }

    void mostraTabela(){
        if(listProdutos == null) listProdutos = new ListView<>();
        listProdutos.getItems().clear();
        try {
            ResultSet produtos = Produto.read();
            while (produtos.next()){
                listProdutos.getItems().add(
                    "#" + produtos.getString("id") + " - " +
                    produtos.getString("titulo") + " - qtd.: " +
                    produtos.getString("quantidade") + " - " +
                    "Custo: R$" + produtos.getString("valor_custo") + " / " +
                    "Venda: R$" + produtos.getString("valor_venda")
                );
            }
        } catch (SQLException e) {
            alerta();
        }
    }

    @FXML
    void cadastrar(){
        openModal("AddProduto.fxml");
    }

    @FXML void confirmar() {
        try {
            Produto.create(
                descricao.getText(),
                Integer.parseInt(quantidade.getText()),
                Float.parseFloat(precoCusto.getText()),
                Float.parseFloat(precoVenda.getText())
            );
        } catch (Exception e) {
            alerta();
        }
        MainController.changeScreen("MenuProdutos");
        cancelar();
    }

    @FXML void maskIdProduto(){
        idProdutoFiltro.setText(idProdutoFiltro.getText().replaceAll("[^0-9]", ""));
        idProdutoFiltro.positionCaret(idProdutoFiltro.getLength());
        if (idProdutoFiltro.getText().isEmpty()) {
            mostraTabela(); return;
        }
        try {
            ResultSet produto = Produto.read(Integer.parseInt(idProdutoFiltro.getText()));
            listProdutos.getItems().clear();
            listProdutos.getItems().add(
                    "#" + produto.getString("id") + " - " +
                    produto.getString("titulo") + " - qtd.: " +
                    produto.getString("quantidade") + " - " +
                    "Custo: R$" + produto.getString("valor_custo") + " / " +
                    "Venda: R$" + produto.getString("valor_venda")
            );
        } catch (SQLException e) { /* suprime o erro caso nao haja o id*/}
    }

    @FXML void readProduto(){
        String[] explode = listProdutos.getSelectionModel().getSelectedItem().split(" - ");
        explode = explode[0].split("#");
        idProdutoRead = Integer.parseInt(explode[1]);
        openModal("ReadProduto.fxml");
    }

    @FXML void maskQuantidade(){
        quantidade.setText(quantidade.getText().replaceAll("[^0-9]", ""));
        quantidade.positionCaret(quantidade.getLength());
    }
    @FXML void alterarQuantidade(){
        try {
            Produto.aumentaEstoque(idProdutoRead, Integer.parseInt(quantidade.getText()));
            MainController.changeScreen("MenuProdutos");
            cancelar();
        } catch (SQLException e) {
            alerta();
        }
    }
}
