package Controller;

import javafx.fxml.FXML;

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
    @FXML void adicionarCredito() {
        openModal("AddCredito.fxml");
    }
    @FXML void consultarConsumo(){}
    @FXML void pagar(){ MainController.changeScreen("PagarConsumo") }
    @FXML void novoConsumo(){ openModal("NovoConsumo.fxml"); }
}
