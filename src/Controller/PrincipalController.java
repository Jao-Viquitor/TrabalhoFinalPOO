package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class PrincipalController extends GeneralController{
    @FXML
    void initialize(){
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("Principal")) {
                insereTabela();
            }
        });
    }

    void insereTabela(){}
    @FXML void gerenciarClientes(){
        MainController.changeScreen("MenuClientes");
    }
    @FXML void gerenciarProdutos(){
        MainController.changeScreen("MenuProdutos");
    }
    @FXML void adicionarCredito(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Adicionar Crédito");
            stage.setScene(
                new Scene(
                    FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("../View/AddCredito.fxml"))
                    )
                )
            );
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
