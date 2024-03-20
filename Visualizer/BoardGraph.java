package Visualizer;

import java.util.HashSet;
import java.util.Set;

public class BoardGraph implements Graph<Cell> {
    private Cell[][] matrix;
    private final int rows;
    private final int cols;

    public BoardGraph(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new Cell[rows][cols];

        // Initialize cells in the matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public Cell getCell(int row, int col){
        return matrix[row][col];
    }

    public void setMatrix(Cell[][] matrix) {
        this.matrix = matrix;
    }


    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    @Override
    public Set<Cell> getVertices() {
        return null;
    }

    @Override
    public Set<Cell> getNeighbours(Cell vertex) {
        return getNeighbours(vertex.getRow(), vertex.getCol());
    }

    @Override
    public Set<Cell> getNeighbours(int row, int col) {
        Set<Cell> neighbours = new HashSet<>();

        int numRows = matrix.length;
        int numCols = matrix[0].length;

        if (col + 1 < numCols) {
            neighbours.add(matrix[row][col + 1]);
        }

        // Left
        if (col - 1 >= 0) {
            neighbours.add(matrix[row][col - 1]);
        }

        // Down
        if (row + 1 < numRows) {
            neighbours.add(matrix[row + 1][col]);
        }

        // Up
        if (row - 1 >= 0) {
            neighbours.add(matrix[row - 1][col]);
        }

        return neighbours;
    }

    @Override
    public Set<Cell> getEdges() {
        return null;
    }
}