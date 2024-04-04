package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Board extends JPanel {
    private final int size = 12; // 10x10 board
    private int revealedCellsCount = 0;
    private final int mineCount = 20; // Assuming 10 mines for simplicity, adjust as needed


    private final Cell[][] cells;

    public Board() {
        setLayout(new GridLayout(size, size));
        cells = new Cell[size][size];
        initializeBoard();
        setMines(); // Set 10 mines for simplicity
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell();
                cells[i][j] = cell;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        cell.setFlagged(!cell.isFlagged());
                    } else{
                        cellClicked(cell);
                    }
                }});
                add(cell);
            }
        }
    }

    private void setMines() {
        Random rand = new Random();
        int minesSet = 0;
        while (minesSet < mineCount) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);

            if (!cells[x][y].isMine()) {
                cells[x][y].setMine(true);
                updateNeighboringMinesCount(x, y);
                minesSet++;
                // Increment neighboring cells mine count here
            }
        }
    }

    private void updateNeighboringMinesCount(int mineX, int mineY) {
        for (int x = mineX - 1; x <= mineX + 1; x++) {
            for (int y = mineY - 1; y <= mineY + 1; y++) {
                // Check if coordinates are within bounds and not the mine itself
                if (x >= 0 && x < size && y >= 0 && y < size && !(x == mineX && y == mineY)) {
                    Cell cell = cells[x][y];
                    if (!cell.isMine()) {
                        cell.incrementNeighboringMines();
                    }
                }
            }
        }
    }


    private void cellClicked(Cell cell) {
        if (cell.isRevealed() || cell.isFlagged()) {
            return; // Do nothing if the cell is already revealed or flagged
        }

        cell.reveal(); // Assume this reveals the cell and updates its appearance

        if (cell.isMine()) {
            gameOver(false); // Implement gameOver to handle losing the game
        } else {
            revealedCellsCount++; // Increment the count for a revealed non-mine cell
            if (cell.getNeighboringMines() == 0) {
                revealAdjacentCells(cell);
            }
            checkWinCondition(); // Check if the player has won after each reveal
        }
    }



    // Additional methods like revealing cells, checking for wins, etc.
    private void revealAdjacentCells(Cell cell) {
        int x = -1, y = -1;
        // Find the coordinates of the cell. You might store the coordinates in the Cell object to avoid this search.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j] == cell) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && nx < size && ny >= 0 && ny < size && !cells[nx][ny].isRevealed() && !cells[nx][ny].isMine()) {
                    cells[nx][ny].reveal();
                    if (cells[nx][ny].getNeighboringMines() == 0) {
                        revealAdjacentCells(cells[nx][ny]);
                    }
                }
            }
        }
    }

    private void checkWinCondition() {
        int totalNonMineCells = (size * size) - mineCount; // mineCount should be the total number of mines
        if (revealedCellsCount == totalNonMineCells) {
            gameOver(true); // Player wins
        }
    }


    private void gameOver(boolean won) {
        String message = won ? "Congratulations, you win!" : "Game Over, you hit a mine!";
        JOptionPane.showMessageDialog(this, message);
        // Disable further clicks or reset the game as needed
    }



}

