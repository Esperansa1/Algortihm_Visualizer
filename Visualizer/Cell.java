package Visualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Cell {
    /**
     *  An enum representing various types a Cell can have within the Visualizer.
     */
    public enum CellType {
        EMPTY,          // A regular empty cell
        WALL,           // An obstacle cell
        PATH,           // Part of a calculated path
        OPEN_SET,       // Cell being considered in a search algorithm
        CLOSE_SET,      // Cell already evaluated in a search algorithm
        START_POINT,    // Designated starting point
        END_POINT,      // Designated target destination
        HIGHLIGHT       // Cell temporarily highlighted for visualization
    }
    /**
     * An array storing the state of walls around the cell: [Top, Right, Bottom, Left]
     * A value of 'true' indicates the presence of a wall in that direction.
     */
    private boolean[] walls = {false, false, false, false};

    /** The size of a cell (in pixels) used for rendering. */
    public static int CELL_SIZE = 40;

    /** The row coordinate of the Cell on the grid. */
    private final int row;

    /** The column coordinate of the Cell on the grid. */
    private final int col;

    /** The current type of the Cell (as defined in the CellType enum). */
    private CellType cellType;

    /** Reference to the Cell from which this Cell was reached (for path reconstruction). */
    private Cell cameFrom;

    /**
     * Constructor to create a Cell with a specified type and walls.
     * @param row The row index of the Cell.
     * @param col The column index of the Cell.
     * @param cellType The type of Cell to create.
     * @param walls An array indicating which walls are present.
     */
    public Cell(int row, int col, CellType cellType, boolean[] walls) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
        this.walls = walls;
    }

    /**
     * Constructor to create a default Cell (type EMPTY).
     * @param row The row index of the Cell.
     * @param col The column index of the Cell.
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellType = CellType.EMPTY;
    }

    /**
     * Retrieves the wall state of the Cell.
     * @return An array of booleans representing the presence of walls (Top, Right, Bottom, Left).
     */
    public boolean[] getWalls() {
        return walls;
    }

    /**
     *  Removes a wall between this Cell and another Cell (used in maze generation).
     *  @param other The other Cell involved in the wall removal.
     */
    public void removeWall(Cell other){
        changeWallState(other, false);
    }

    /**
     *  Adds a wall between this Cell and another Cell (used in maze generation).
     *  @param other The other Cell involved in the wall addition.
     */
    public void addWall(Cell other){
        changeWallState(other, true);
    }

    /**
     *  Helper method to update the wall state between two Cells.
     *  @param other The other Cell involved in the change.
     *  @param state True to add a wall, false to remove a wall.
     */
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

    /**
     * Helper function to calculate the horizontal and vertical distances between this Cell and another Cell.
     * @param otherCell The Cell to compare distances with.
     * @return An array of two integers: [horizontal distance, vertical distance]
     */
    private int[] getDistances(Cell otherCell) {
        int dx = Math.abs(getCol() - otherCell.getCol());
        int dy = Math.abs(getRow() - otherCell.getRow());
        return new int[]{dx, dy};
    }

    /**
     * Calculates the Euclidean distance between this Cell and another Cell. Used in heuristics for pathfinding.
     * @param otherCell The Cell to calculate distance to.
     * @return The Euclidean distance between the two Cells.
     */
    public double euclideanDist(Cell otherCell) {
        int[] distances = getDistances(otherCell);
        return Math.sqrt(distances[0] * distances[0] + distances[1] * distances[1]);
    }

    /**
     * Static method to adjust the CELL_SIZE, affecting the rendering of all Cells.
     * @param size The new desired size for Cells.
     */
    public static void setCellSize(int size){
        CELL_SIZE = size;
    }

    /**
     * Gets the current CellType of this Cell.
     * @return The CellType of the Cell.
     */
    public CellType getCellType() {
        return cellType;
    }

    /**
     * Sets the CellType of this Cell.
     * @param cellType The new CellType for the Cell.
     */
    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    /**
     * Gets the row index of the Cell within the grid.
     * @return The row position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of the Cell within the grid.
     * @return The column position.
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the Cell object that led to this Cell in a search path.
     * @return The predecessor Cell in the path.
     */
    public Cell getCameFrom() {
        return cameFrom;
    }

    /**
     * Sets the predecessor Cell used for path reconstruction.
     * @param cameFrom The preceding Cell in the path.
     */
    public void setCameFrom(Cell cameFrom) {
        this.cameFrom = cameFrom;
    }

    /**
     * Sets the wall states of the Cell.
     * @param walls An array specifying which walls exist (Top, Right, Bottom, Left)
     */
    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }

    /**
     * Provides a textual representation of the Cell's row and column position.
     * @return A string in the format "Row: [row] Col: [col]"
     */
    @Override
    public String toString() {
        return "Row: "+row + " Col:" +col;
    }
}

