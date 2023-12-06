package Visualizer.SearchAlgortihms.AStar;

import Visualizer.Cell;

import java.util.ArrayList;

public class AStarCell extends Cell{


    public double h;
    public double g;
    public double f;
    public int weight;

    private ArrayList<AStarCell> neighbours;

    public AStarCell(Cell cell) {
        super(cell.getRow(), cell.getCol(), cell.getCellType(), cell.getWalls());
        weight = 1;
    }
    public AStarCell(int row, int col) {
        super(row, col);
        weight = 1;
    }

    public int[] getDistances(AStarCell otherCell) {
        int dx = Math.abs(getCol() - otherCell.getCol());
        int dy = Math.abs(getRow() - otherCell.getRow());
        return new int[]{dx, dy};
    }

    public double manhattanDist(AStarCell otherCell) {
        int[] distances = getDistances(otherCell);
        return distances[0] + distances[1];
    }

    public double euclideanDist(AStarCell otherCell) {
        int[] distances = getDistances(otherCell);
        return Math.sqrt(distances[0] * distances[0] + distances[1] * distances[1]);
    }

    public double octileDist(AStarCell otherCell) {
        int[] distances = getDistances(otherCell);
        double f = Math.sqrt(2) - 1;
        double dx = distances[0];
        double dy = distances[1];

        if (dx < dy) {
            return f * dx + dy;
        } else {
            return f * dy + dx;
        }
    }

    public double chebyshev(AStarCell otherCell) {
        int[] distances = getDistances(otherCell);
        return Math.max(distances[0], distances[1]);
    }
    public ArrayList<AStarCell> getNeighbours() {
        return neighbours;
    }


    public void setupNeighbours(AStarCell[][] cells, boolean allowDiagonals){
        super.setupNeighbours(cells, allowDiagonals);
        neighbours = new ArrayList<>();

        int numRows = cells.length;
        int numCols = cells[0].length;

        int col = getCol();
        int row = getRow();
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

}
