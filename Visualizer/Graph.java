package Visualizer;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private Cell[][] matrix;
    private final int rows;
    private final int cols;

    public Graph(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new Cell[rows][cols];

        // Initialize cells in the matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Cell(i, j);
            }
        }

        // Set up neighbors for each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j].setupNeighbours(matrix);
            }
        }
    }

    public void removeVertex(int row, int col) {
        if (isValidCell(row, col)) {
            matrix[row][col] = null;
        }
    }

    public List<Cell> getNeighbors(int row, int col) {
        if (isValidCell(row, col)) {
            return matrix[row][col].getNeighbours();
        }
        return new ArrayList<>();
    }

    public boolean hasVertex(int row, int col) {
        return isValidCell(row, col) && matrix[row][col] != null;
    }


    public int getVertexCount() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != null) {
                    count++;
                }
            }
        }
        return count;
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

    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != null) {
                    count += matrix[i][j].getNeighbours().size();
                }
            }
        }
        return count / 2;
    }



    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}