package Controller;

import Model.Camarote;
import Model.CamarotePista;
import Model.Pista;
import Model.Vip;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ClienteController extends GeneralController {
    @FXML private TextField RG, nomeCliente, valorCredito;
    @FXML private ChoiceBox tipoCliente;

    @FXML void cadastrar() {
        openModal("AddCliente.fxml");
    }

    @FXML void confirmarCadastro() {
        try {
            switch (tipoCliente.getSelectionModel().getSelectedItem().toString()){
                case "VIP": {
                    System.out.println("vip");
                    Vip.create(
                        Integer.parseInt(RG.getText()),
                        nomeCliente.getText()
                    );
                } break;
                case "Camarote": {
                    Camarote.create(
                        Integer.parseInt(RG.getText()),
                        nomeCliente.getText(),
                        0F
                    );
                } break;
                default: case "Pista": {
                    Pista.create(
                        Integer.parseInt(RG.getText()),
                        nomeCliente.getText(),
                        0F
                    );
                }
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            System.out.println("Preencha o nome do cliente!");
        }
    }
    @FXML void confirmarAddCredito() {
        try {
            CamarotePista.adicionarCredito(
                Integer.parseInt(RG.getText()),
                Float.parseFloat(valorCredito.getText())
            );
            cancelar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void maskRG(){
        RG.setText(RG.getText().replaceAll("[^0-9]", ""));
        RG.positionCaret(RG.getLength());
    }
    @FXML void filtraRG(){
        maskRG();
    }
    @FXML void buscaRG(){
        try {
            maskRG();
            nomeCliente.setText(
                CamarotePista.read(
                    Integer.parseInt(RG.getText())
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
