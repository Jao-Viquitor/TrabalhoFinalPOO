package Controller;

import Model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProdutoController extends GeneralController{
    @FXML private Label idProduto;
    @FXML private TextField descricao, quantidade, precoCusto, precoVenda;
    @FXML private ListView<String> listProdutos;
    @FXML private ListView<String> listTitulo;
    @FXML private ListView<String> listQuantidade;
    @FXML private ListView<String> listCusto;
    @FXML private ListView<String> listVenda;

    @FXML
    void initialize() throws SQLException {
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("MenuProdutos")) {
                mostraTabela();
            }
        });
        if(modalScreen){
            if(idProduto == null) idProduto = new Label();
            idProduto.setText(Integer.toString(Produto.nextId()));
            modalScreen = false;
        }
    }

    void mostraTabela(){
        if(listProdutos == null) listProdutos = new ListView<>();
            allClear();
        try {
            ResultSet produtos = Produto.read();
            while (produtos.next()){
                listProdutos.getItems().add( "#" +
                    produtos.getString("id")
                );
                listTitulo.getItems().add(produtos.getString("titulo"));
                listQuantidade.getItems().add(produtos.getString("quantidade"));
                listCusto.getItems().add("R$" + produtos.getString("valor_custo"));
                listVenda.getItems().add("R$" + produtos.getString("valor_venda"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        allRefresh();
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
            System.out.println(e.getMessage());
        }
        mostraTabela();
        cancelar();
    }

    void allClear(){
        listProdutos.getItems().clear();
        listTitulo.getItems().clear();
        listQuantidade.getItems().clear();
        listCusto.getItems().clear();
        listVenda.getItems().clear();
    }

    void allRefresh(){
        listProdutos.refresh();
        listTitulo.refresh();
        listQuantidade.refresh();
        listCusto.refresh();
        listVenda.refresh();
    }

}
