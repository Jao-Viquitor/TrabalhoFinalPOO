package Controller;

import Model.Cliente;
import Model.Vip;

import java.sql.SQLException;

public class MainController {
    public static void main(String[] args) {
        try {
            Vip.delete(1234567890);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
