package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GeneralController {
    protected static Stage modal;
    protected static boolean modalScreen;

    @FXML
    void voltar(){
        MainController.changeScreen();
    }

    @FXML void cancelar() {
        modal.close();
        modal = null;
    }

    protected void openModal(String nameFXML){
        modalScreen = true;
        try {
            modal = new Stage();
            modal.setTitle("Adicionar Produto");
            modal.setScene(
                new Scene(
                    FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("../View/" + nameFXML))
                    )
                )
            );
            modal.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
