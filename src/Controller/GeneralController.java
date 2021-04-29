package Controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class GeneralController {
    protected static Stage modal;

    @FXML
    void voltar(){
        MainController.changeScreen();
    }
    @FXML void cancelar() {
        modal.close();
        modal = null;
    }
}
