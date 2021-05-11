package Controller;

import Model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProdutoController extends GeneralController{
    @FXML private Label idProduto;
    @FXML private TextField descricao, quantidade, precoCusto, precoVenda;
    @FXML private ListView<String> listProdutos;

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
        listProdutos.getItems().clear();
        try {
            ResultSet produtos = Produto.read();
            while (produtos.next()){
                listProdutos.getItems().add( "#" +
                    produtos.getString("id") + " - " +
                    produtos.getString("titulo") + " - (qtd.: " +
                    produtos.getString("quantidade") + ")"
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        listProdutos.refresh();
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

}
