package minesweeper;

import javax.swing.*;

public class Game extends JFrame {
    private Board board;

    public Game() {
        super();
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null); // Center the window

        board = new Board();
        add(board);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}
