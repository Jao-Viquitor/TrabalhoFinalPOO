
package Controller;

import Model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;


public class ProdutoController {
    @FXML private Label idProduto = new Label();
    @FXML
    void initialize(){
        MainController.setListener((newScreen, userData) -> {
//            if (newScreen.equals("MenuProdutos"))
        });
    }

    @FXML void pesquisar() {}
    @FXML
    void cadastrar(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Adicionar Produto");
            stage.setScene(
                new Scene(
                    FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("../View/AddProduto.fxml"))
                    )
                )
            );


            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML void confirmar() {}
    @FXML void cancelar() {}
}
