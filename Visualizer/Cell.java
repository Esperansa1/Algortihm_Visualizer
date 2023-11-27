package Visualizer;

import java.util.ArrayList;


public class Cell {
    public enum CellType {EMPTY, WALL, PATH, OPEN_SET, CLOSE_SET, START_POINT, END_POINT}

    public static int CELL_SIZE = 40;

    private int row;
    private int col;

    private CellType cellType;
    private ArrayList<Cell> neighbours;


    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellType = CellType.EMPTY;
        neighbours = new ArrayList<>();

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

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}

