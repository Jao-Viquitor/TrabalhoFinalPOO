package Controller;

import javafx.fxml.FXML;

public class PrincipalController {
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
}
