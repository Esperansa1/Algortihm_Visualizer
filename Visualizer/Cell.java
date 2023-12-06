package Visualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Cell {
    public enum CellType {EMPTY, WALL, PATH, OPEN_SET, CLOSE_SET, START_POINT, END_POINT, HIGHLIGHT}
                            // TOP  RIGHT BOTTOM LEFT
    private boolean[] walls = {true, true, true, true};

    public static int CELL_SIZE = 40;

    private int row;
    private int col;

    private CellType cellType;
    private Cell cameFrom;

    private ArrayList<Cell> neighbours;

    public Cell(int row, int col, CellType cellType, boolean[] walls) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
        this.walls = walls;
        neighbours = new ArrayList<>();

    }

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellType = CellType.EMPTY;
        neighbours = new ArrayList<>();

    }

    public boolean[] getWalls() {
        return walls;
    }

    public void removeWall(Cell other){
        int x = col - other.col;

        if (x == 1) {
            walls[3] = false;
            other.walls[1] = false;
        } else if (x == -1) {
            walls[1] = false;
            other.walls[3] = false;
        }

        int y = row - other.row;

        if (y == 1) {
            walls[0] = false;
            other.walls[2] = false;
        } else if (y == -1) {
            walls[2] = false;
            other.walls[0] = false;
        }
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

    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }
    @Override
    public String toString() {
        return "Row: "+row + " Col:" +col;
    }
    
}

