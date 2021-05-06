package Controller;

import Model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProdutoController extends GeneralController{
    @FXML private Label idProduto;
    @FXML private TextField descricao, quantidade, precoCusto, precoVenda;

    @FXML
    void initialize() throws SQLException {
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("MenuProdutos")) mostraTabela();
        });
        if(modalScreen){
            if(idProduto == null) idProduto = new Label();
            idProduto.setText(Integer.toString(Produto.nextId()));
            modalScreen = false;
        }
    }

    void mostraTabela(){
        try {
            ResultSet resultSet = Produto.read();

            while (resultSet.next()){

                System.out.println(
                        resultSet.getInt("id") + "\n" +
                        resultSet.getString("titulo") + "\n" +
                        resultSet.getFloat("valor_custo") + "\n" +
                        resultSet.getFloat("valor_venda") + "\n"
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML void pesquisar() {}
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
            cancelar();
            atualizarTabela();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void atualizarTabela(){

    }

}
