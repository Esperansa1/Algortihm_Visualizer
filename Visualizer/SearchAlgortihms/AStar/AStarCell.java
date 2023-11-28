package Visualizer.SearchAlgortihms.AStar;

import Visualizer.Cell;

import java.util.ArrayList;

public class AStarCell {


    public double h;
    public double g;
    public double f;
    public int weight;

    private ArrayList<AStarCell> neighbours;
    public Cell cell;


    public AStarCell(Cell cell) {
        this.cell = cell;
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






}
