package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteController extends GeneralController {
    @FXML private TextField RG, nomeCliente, valorCredito;
    @FXML private ChoiceBox<String> tipoCliente;
    @FXML private ListView<String> listClientes;

    @FXML
    void initialize(){
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("MenuClientes")){
                mostraTabela();
            }
        });
    }
    void mostraTabela(){
        if(listClientes == null) listClientes = new ListView<>();
        listClientes.getItems().clear();
        try {
            ResultSet clientes = Cliente.read();
            while (clientes.next()){
                listClientes.getItems().add(
                    clientes.getString("rg") + " - " +
                    clientes.getString("nome") + " - (" +
                    clientes.getString("tipo_entrada") + ")"
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML void cadastrar() {
        openModal("AddCliente.fxml");
    }

    @FXML void confirmarCadastro() {
        try {
            if(tipoCliente == null) tipoCliente = new ChoiceBox<>();
            String rg = RG.getText();
            String nome = nomeCliente.getText();
            switch (tipoCliente.getSelectionModel().getSelectedItem()){
                case "VIP": Vip.create(rg, nome); break;
                case "Camarote": Camarote.create(rg, nome, 0F); break;
                case "Pista": Pista.create(rg, nome, 0F); break;
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Preencha o nome do cliente!");
        }
        mostraTabela();
        cancelar();
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
                    RG.getText()
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
