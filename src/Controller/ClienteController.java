package Controller;

import Model.CamarotePista;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ClienteController extends GeneralController {
    @FXML private TextField pesquisarRG, nomeCliente;

    @FXML void pesquisarCliente() {
        try {
            nomeCliente.setText(
                CamarotePista.read(
                    Integer.parseInt(pesquisarRG.getText())
                ).getString("nome")
            );

        } catch (SQLException | IllegalArgumentException e) {
            nomeCliente.setText("RG inválido para esta operação");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML void pesquisar() {}
    @FXML void cadastrar() {
        openModal("AddCliente.fxml");
    }

    @FXML void confirmar() {}
    @FXML void tipoCliente() {}
}
