package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            modal = new Stage();
            modal.setTitle("Adicionar Crédito");
            modal.setScene(
                new Scene(
                    FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("../View/AddCredito.fxml"))
                    )
                )
            );
            modal.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML void consultarConsumo(){}
    @FXML void novoConsumo(){}
}
