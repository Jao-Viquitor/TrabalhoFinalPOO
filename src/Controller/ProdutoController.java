
package Controller;

import Model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class ProdutoController extends GeneralController{
    @FXML private Label idProduto;
    private static boolean openScreen;
    @FXML
    void initialize() throws SQLException {
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("MenuProdutos")) mostraTabela();
        });
        if(openScreen){
            if(idProduto == null) idProduto = new Label();
            idProduto.setText(Integer.toString(Produto.nextId()));
            openScreen = false;
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
        openScreen = true;
        try {
            modal = new Stage();
            modal.setTitle("Adicionar Produto");
            modal.setScene(
                new Scene(
                    FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("../View/AddProduto.fxml"))
                    )
                )
            );
            modal.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML void confirmar() {}

}
