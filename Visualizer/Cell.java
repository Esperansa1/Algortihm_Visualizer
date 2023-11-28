package Visualizer;

import java.util.ArrayList;


public class Cell {
    public enum CellType {EMPTY, WALL, PATH, OPEN_SET, CLOSE_SET, START_POINT, END_POINT}

    public static int CELL_SIZE = 40;

    private int row;
    private int col;

    private CellType cellType;

    private Cell cameFrom;
    private ArrayList<Cell> neighbours;


    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellType = CellType.EMPTY;
        neighbours = new ArrayList<>();

    }

    public void setupNeighbours(Cell[][] cells, boolean allowDiagonals){
        neighbours = new ArrayList<>();

        int numRows = cells.length;
        int numCols = cells[0].length;

        // Right
        if (col + 1 < numCols) {
            neighbours.add(cells[row][col + 1]);
        }

        // Left
        if (col - 1 >= 0) {
            neighbours.add(cells[row][col - 1]);
        }

        // Down
        if (row + 1 < numRows) {
            neighbours.add(cells[row + 1][col]);
        }

        // Up
        if (row - 1 >= 0) {
            neighbours.add(cells[row - 1][col]);
        }

        // Diagonals
        if (allowDiagonals) {
            // Down-Right
            if (row + 1 < numRows && col + 1 < numCols) {
                neighbours.add(cells[row + 1][col + 1]);
            }

            // Up-Right
            if (row - 1 >= 0 && col + 1 < numCols) {
                neighbours.add(cells[row - 1][col + 1]);
            }

            // Up-Left
            if (row - 1 >= 0 && col - 1 >= 0) {
                neighbours.add(cells[row - 1][col - 1]);
            }

            // Down-Left
            if (row + 1 < numRows && col - 1 >= 0) {
                neighbours.add(cells[row + 1][col - 1]);
            }
        }
    }


    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Cell getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Cell cameFrom) {
        this.cameFrom = cameFrom;
    }

    @Override
    public String toString() {
        return "Row: "+row + " Col:" +col;
    }
}

