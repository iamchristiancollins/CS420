package minesweeper;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int neighboringMines;

    public Cell() {
        super();
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.neighboringMines = 0;
    }

    // Add getters and setters here

    public void reveal() {
        if (isRevealed || isFlagged) {
            return; // Don't reveal if already revealed or flagged
        }

        isRevealed = true;
        setText(isMine ? "M" : neighboringMines > 0 ? String.valueOf(neighboringMines) : "");
        setEnabled(false); // Disables the cell, so it doesn't react to clicks anymore

        if (isMine) {
            setBackground(Color.RED); // Optional: Set background to red if it's a mine
        } else {
            setBackground(Color.LIGHT_GRAY); // Change color to indicate it's revealed
        }
    }

    public void incrementNeighboringMines() {
        neighboringMines++;
    }

    // Existing getters and setters...

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
        // Optional: Change appearance based on flagged state
        setText(isFlagged ? "F" : "");
    }

    public int getNeighboringMines() {
        return neighboringMines;
    }

    public void setNeighboringMines(int neighboringMines) {
        this.neighboringMines = neighboringMines;
    }
}
