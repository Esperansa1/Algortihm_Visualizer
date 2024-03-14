package Visualizer;

import java.util.ArrayList;


public class Cell {
    public enum CellType {EMPTY, WALL, PATH, OPEN_SET, CLOSE_SET, START_POINT, END_POINT, HIGHLIGHT}
                            // TOP  RIGHT BOTTOM LEFT
//    private boolean[] walls = {true, true, true, true};
    private boolean[] walls = {false, false, false, false};

    public static int CELL_SIZE = 40;

    private final int row;
    private final int col;

    private CellType cellType;
    private Cell cameFrom;

    public Cell(int row, int col, CellType cellType, boolean[] walls) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
        this.walls = walls;

    }

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellType = CellType.EMPTY;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public void removeWall(Cell other){
        changeWallState(other, false);
    }

    public void addWall(Cell other){
        changeWallState(other, true);
    }

    public void changeWallState(Cell other, boolean state){
        int x = col - other.col;
        int y = row - other.row;

        if (x == 1) {
            walls[3] = state;
            other.walls[1] = state;
        } else if (x == -1) {
            walls[1] = state;
            other.walls[3] = state;
        }
        else if (y == 1) {
            walls[0] = state;
            other.walls[2] = state;
        } else if (y == -1) {
            walls[2] = state;
            other.walls[0] = state;
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

    public static void setCellSize(int size){
        CELL_SIZE = size;
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

