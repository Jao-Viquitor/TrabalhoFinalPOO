package Controller;

import Model.*;

import java.sql.SQLException;

public class MainController {
    public static void main(String[] args) {
        try {
            Produto.aumentaEstoque(2, 2);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
