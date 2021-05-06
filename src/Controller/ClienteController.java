package Controller;

import Model.CamarotePista;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ClienteController extends GeneralController {
    @FXML private TextField buscarRG, nomeCliente, valorCredito;

    @FXML void cadastrar() {
        openModal("AddCliente.fxml");
    }

    @FXML void confirmar() {}
    @FXML void confirmarAddCredito() {
        try {
            CamarotePista.adicionarCredito(
                Integer.parseInt(buscarRG.getText()),
                Float.parseFloat(valorCredito.getText())
            );
            cancelar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML void buscaRG(){
        try {
            buscarRG.setText(buscarRG.getText().replaceAll("[^0-9]", ""));
            buscarRG.positionCaret(buscarRG.getLength());
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
    @FXML void maskPreco(){
        valorCredito.setText(valorCredito.getText().replaceAll(",", "."));
        valorCredito.setText(valorCredito.getText().replaceAll("[^0-9.]", ""));
        valorCredito.positionCaret(valorCredito.getLength());
    }
}
