package santorini.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code Board} class represents the game board in the Santorini game.
 * It extends {@code JPanel} and manages a grid of {@code Cell} objects.
 * The board is responsible for initializing cells, retrieving neighbors,
 * and highlighting or resetting cell states.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class Board extends JPanel {
    private int rows;
    private int cols;
    private Cell[][] cells;

    /**
     * Constructs a new Board with the specified number of rows and columns.
     *
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        setLayout(new GridLayout(rows, cols));
    }

    /**
     * Initializes the board by creating {@code Cell} objects for each position
     * and adding them to the panel.
     */
    public void initialize() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = new Cell(row, col);
                cells[row][col] = cell;
                add(cell);
            }
        }
    }

    /**
     * Returns the number of rows in the board.
     *
     * @return the row count
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the board.
     *
     * @return the column count
     */
    public int getCols() {
        return cols;
    }

    /**
     * Retrieves the cell at the specified row and column.
     *
     * @param row the row index
     * @param col the column index
     * @return the {@code Cell} at the specified position
     */
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    /**
     * Returns the 2D array of all cells on the board.
     *
     * @return a 2D array of {@code Cell} objects
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Retrieves a list of neighboring cells around a given cell.
     * Neighbors are any adjacent cells (8-directional).
     *
     * @param cell the cell whose neighbors are to be found
     * @return a list of neighboring {@code Cell} objects
     */
    public List<Cell> getNeighbourCells(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    int newRow = x + dx;
                    int newCol = y + dy;
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                        neighbours.add(cells[newRow][newCol]);
                    }
                }
            }
        }
        return neighbours;
    }

    /**
     * Highlights a specific cell by setting its highlight state to true
     * and repainting it.
     *
     * @param row the row index of the cell to highlight
     * @param col the column index of the cell to highlight
     */
    public void highlightCell(int row, int col) {
        Cell c = cells[row][col];
        c.setIsHighlighted(true);
        c.repaint();
    }

    /**
     * Clears all highlights on the board and removes any mouse listeners
     * from each cell.
     */
    public void clearHighlights() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = cells[row][col];
                cell.setIsHighlighted(false);
                cell.repaint();

                MouseListener[] listeners = cell.getMouseListeners();
                for (int j = 0; j < listeners.length; j++) {
                    cell.removeMouseListener(listeners[j]);
                }
            }
        }
    }
}
