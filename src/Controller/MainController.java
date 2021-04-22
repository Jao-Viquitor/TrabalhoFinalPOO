package Controller;

import Model.*;

import java.sql.SQLException;

public class MainController {
    public static void main(String[] args) {
        try {
            System.out.println(Vip.read("nome ASC").getString("nome"));
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
