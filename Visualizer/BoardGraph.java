package Visualizer;

import java.util.HashSet;
import java.util.Set;

public class BoardGraph implements Graph<Cell> {

    /** A 2D array representing the grid of cells in the board. */
    private Cell[][] matrix;

    /** The number of rows in the board. */
    private final int rows;

    /** The number of columns in the board. */
    private final int cols;

    /**
     *  Constructor. Creates a BoardGraph with the specified dimensions and initializes the Cell objects.
     *  @param rows The number of rows in the board.
     *  @param cols The number of columns in the board.
     */
    public BoardGraph(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new Cell[rows][cols];

        // Initialize each cell with its row and column coordinates
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     *  Gets the 2D Cell array representing the board graph.
     *  @return The matrix of Cell objects.
     */
    public Cell[][] getMatrix() {
        return matrix;
    }

    /**
     * Retrieves a specific Cell object from the graph.
     * @param row The row index of the desired Cell.
     * @param col The column index of the desired Cell.
     * @return The Cell object at the specified (row, col) position.
     */
    public Cell getCell(int row, int col){
        return matrix[row][col];
    }

    /**
     * Updates the 2D Cell array representing the board graph.
     * @param matrix The new matrix of Cell objects.
     */
    public void setMatrix(Cell[][] matrix) {
        this.matrix = matrix;
    }


    /**
     * Checks if a given cell is within the valid bounds of the graph.
     * @param row The row index to check.
     * @param col The column index to check.
     * @return True if the (row, col) position is within the graph's dimensions, false otherwise.
     */
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * (Interface method - Unimplemented)
     * @return Placeholder - Not used in the current context
     */
    @Override
    public Set<Cell> getVertices() {
        return null;
    }

    /**
     * Retrieves the direct neighbors (up, down, left, right) of a given Cell.
     * @param vertex The Cell object whose neighbors are requested.
     * @return A set of Cell objects representing the valid neighbors.
     */
    @Override
    public Set<Cell> getNeighbours(Cell vertex) {
        return getNeighbours(vertex.getRow(), vertex.getCol());
    }

    /**
     * Retrieves the direct neighbors (up, down, left, right) of a cell by coordinates.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return A set of Cell objects representing the valid neighbors.
     */
    @Override
    public Set<Cell> getNeighbours(int row, int col) {
        Set<Cell> neighbours = new HashSet<>();

        // Check for out of bounds indices
        if (isValidCell(row, col)) {
            if (col + 1 < cols) neighbours.add(matrix[row][col + 1]); // Right
            if (col - 1 >= 0) neighbours.add(matrix[row][col - 1]);   // Left
            if (row + 1 < rows) neighbours.add(matrix[row + 1][col]); // Down
            if (row - 1 >= 0) neighbours.add(matrix[row - 1][col]);   // Up
        }

        return neighbours;
    }

    /**
     * (Interface method - Unimplemented)
     * @return Placeholder - Not used in the current context
     */
    @Override
    public Set<Cell> getEdges() {
        return null;
    }
}
