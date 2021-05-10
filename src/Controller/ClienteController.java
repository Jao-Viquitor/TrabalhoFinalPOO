package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteController extends GeneralController {
    @FXML private TextField RG, nomeCliente, valorCredito;
    @FXML private ChoiceBox tipoCliente;
    @FXML private TableView<Cliente> clientes;
    @FXML private TableColumn<Cliente, String> registro;
    @FXML private TableColumn<Cliente, String> nome;
    @FXML private TableColumn<Cliente, String> categoria;
    @FXML private TableColumn<Cliente, String> credito;

    @FXML
    void initialize(){
        MainController.setListener((newScreen, userData) -> {
            if (newScreen.equals("MenuClientes")){
                configuraColunas();
                atualizarTabela();
            }
        });
    }

    void atualizarTabela(){
        clientes.getItems().clear();
        try{
            ResultSet arrayClientes = Cliente.read();
            while (arrayClientes.next()){

                clientes.getItems().add(
                    new Cliente(
                        arrayClientes.getString("rg"),
                        arrayClientes.getString("nome"),
                        arrayClientes.getString("tipo_entrada")
                    )
                );

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void configuraColunas(){
        if(credito.getCellFactory() == null){
            registro.setCellValueFactory(new PropertyValueFactory<>("rg"));
            nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            credito.setCellValueFactory(new PropertyValueFactory<>("credito"));
            categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        }
//        clientes.getColumns().add(registro);
//        clientes.getColumns().add(nome);
//        clientes.getColumns().add(categoria);
    }

    @FXML void cadastrar() {
        openModal("AddCliente.fxml");
    }

    @FXML void confirmarCadastro() {
        try {
            switch (tipoCliente.getSelectionModel().getSelectedItem().toString()){
                case "VIP": {
                    Vip.create(
                        RG.getText(),
                        nomeCliente.getText()
                    );
                } break;
                case "Camarote": {
                    Camarote.create(
                        RG.getText(),
                        nomeCliente.getText(),
                        0F
                    );
                } break;
                default: case "Pista": {
                    Pista.create(
                        RG.getText(),
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
