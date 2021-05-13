package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class GeneralController {
    protected static Stage modal;
    protected static boolean modalScreen;

    @FXML Label msgErro;

    @FXML
    void voltar(){
        MainController.changeScreen();
    }

    @FXML void cancelar() {
        modal.close();
        modal = null;
    }

    @FXML void alerta(String alert){
        msgErro.setText(alert);
        openModal("Alerta.fxml");
    }


    protected void openModal(String nameFXML){
        modalScreen = true;
        try {
            modal = new Stage();
            modal.setTitle("Modal");
            modal.setScene(
                new Scene(
                    FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("../View/" + nameFXML))
                    )
                )
            );
            modal.show();
        } catch (Exception e) {
            alerta(e.getMessage());
        }
    }
}
