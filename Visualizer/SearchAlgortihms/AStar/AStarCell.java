package Visualizer.SearchAlgortihms.AStar;

import Visualizer.Cell;

import java.util.ArrayList;

public class AStarCell {


    public double h;
    public double g;
    public double f;
    public int weight;

    private ArrayList<AStarCell> neighbours;

    private Cell cell;


    public AStarCell(Cell cell) {
        this.cell = cell;
        weight = 1;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public int[] getDistances(AStarCell otherCell) {
        int dx = Math.abs(this.cell.getCol() - otherCell.cell.getCol());
        int dy = Math.abs(this.cell.getRow() - otherCell.cell.getRow());
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

    public void setupNeighbours(AStarCell[][] cells){
        neighbours = new ArrayList<>();

        int numRows = cells.length;
        int numCols = cells[0].length;


        int row = cell.getRow();
        int col = cell.getCol();

        if (col + 1 < numCols) {
            neighbours.add(cells[row][col + 1]);
        }

        if (col - 1 >= 0) {
            neighbours.add(cells[row][col - 1]);
        }

        if (row + 1 < numRows) {
            neighbours.add(cells[row + 1][col]);
        }

        if (row - 1 >= 0) {
            neighbours.add(cells[row - 1][col]);
        }
        System.out.println(neighbours);

//        // Diagonals
//        if (allowDiagonals) {
//            // Down-Right
//            if (row + 1 < numRows && col + 1 < numCols) {
//                neighbours.add(cells[row + 1][col + 1]);
//            }
//
//            // Up-Right
//            if (row - 1 >= 0 && col + 1 < numCols) {
//                neighbours.add(cells[row - 1][col + 1]);
//            }
//
//            // Up-Left
//            if (row - 1 >= 0 && col - 1 >= 0) {
//                neighbours.add(cells[row - 1][col - 1]);
//            }
//
//            // Down-Left
//            if (row + 1 < numRows && col - 1 >= 0) {
//                neighbours.add(cells[row + 1][col - 1]);
//            }

    }

    @Override
    public String toString() {
        return "AStarCell{" +
                "cell=" + cell +
                '}';
    }
}
