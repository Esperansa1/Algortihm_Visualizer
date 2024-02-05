package Visualizer;

import java.util.ArrayList;


public class Cell {
    public enum CellType {EMPTY, WALL, PATH, OPEN_SET, CLOSE_SET, START_POINT, END_POINT, HIGHLIGHT}
                            // TOP  RIGHT BOTTOM LEFT
    private boolean[] walls = {true, true, true, true};
//    private boolean[] walls = {false, false, false, false};

    public static int CELL_SIZE = 40;

    private final int row;
    private final int col;

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

    private int[] getDistances(Cell otherCell) {
        int dx = Math.abs(getCol() - otherCell.getCol());
        int dy = Math.abs(getRow() - otherCell.getRow());
        return new int[]{dx, dy};
    }
    public double euclideanDist(Cell otherCell) {
        int[] distances = getDistances(otherCell);
        return Math.sqrt(distances[0] * distances[0] + distances[1] * distances[1]);
    }

    public void setupNeighbours(Cell[][] cells){
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

    }
    public ArrayList<Cell> getNeighbours() {
        return neighbours;
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

