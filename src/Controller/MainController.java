package Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

import static javafx.fxml.FXMLLoader.*;

public class MainController extends Application {
    private static Stage stage;
    private static Scene principal;
    private static Scene menuClientes, menuProdutos, menuConsumo;

    private static final ArrayList<OnChangeScreen> listeners = new ArrayList<>();

    /** Setando telas */
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Trabalho Prático 3 - Final");

        principal = new Scene(load(Objects.requireNonNull(getClass().getResource("../View/Principal.fxml"))));

        menuClientes = new Scene(load(Objects.requireNonNull(getClass().getResource("../View/MenuClientes.fxml"))));
        menuProdutos = new Scene(load(Objects.requireNonNull(getClass().getResource("../View/MenuProdutos.fxml"))));
        menuConsumo = new Scene(load(Objects.requireNonNull(getClass().getResource("../View/MenuConsumo.fxml"))));

        changeScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Controla as trocas de tela
     * @param screen = passa o "nome" da tela desejada
     * @param userData = parametro Opcional
     */
    public static void changeScreen(String screen, ArrayList<String> userData){
        switch (screen){
            case "MenuClientes": stage.setScene(menuClientes); break;
            case "MenuProdutos": stage.setScene(menuProdutos); break;
            case "MenuConsumo": stage.setScene(menuConsumo); break;

            default: case "Principal": stage.setScene(principal);
        }
        notifyAllListeners(screen, userData);
    }
    public static void changeScreen(String screen){ changeScreen(screen, null); }
    public static void changeScreen(){ changeScreen("Principal"); }


    /**
     * Esta interface obriga todos os controllers a receber a tela atual bem como um parametro
     */
    public interface OnChangeScreen{
        void screenChanged(String newScreen, ArrayList<String> userData);
    }

    /**
     * @param newListener é a interface, assim a lista listeners terá todas os locais que a interface foi implementada
     */
    public static void setListener(OnChangeScreen newListener){ listeners.add(newListener); }

    /**
     * Notificará todas os controllers
     * @param newScreen = tela atual
     * @param userData = algum objeto, podendo ser nulo ou não
     */
    private static void notifyAllListeners(String newScreen, ArrayList<String> userData){
        for (OnChangeScreen l : listeners)
            l.screenChanged(newScreen, userData);
    }

}
