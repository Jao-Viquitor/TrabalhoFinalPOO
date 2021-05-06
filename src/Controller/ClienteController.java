package Controller;

import Model.CamarotePista;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ClienteController extends GeneralController {
    @FXML private TextField buscarRG, nomeCliente;

    @FXML void pesquisarCliente() {

    }
    @FXML void pesquisar() {}
    @FXML void cadastrar() {
        openModal("AddCliente.fxml");
    }

    @FXML void confirmar() {}
    @FXML void tipoCliente() {}
    @FXML void buscaRG(){
        try {
            nomeCliente.setText(
                    CamarotePista.read(
                            Integer.parseInt(buscarRG.getText())
                    ).getString("nome")
            );

        } catch (SQLException | IllegalArgumentException e) {
            nomeCliente.setText("RG inválido para esta operação");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
