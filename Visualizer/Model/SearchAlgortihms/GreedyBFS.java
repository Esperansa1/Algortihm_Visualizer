package Visualizer.Model.SearchAlgortihms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.HashMap;
import java.util.Map;

public class GreedyBFS extends SearchAlgorithm {

    public static final String NAME = "A Star (A*)";
    private Map<Cell, Double> cellMap;
    private BoardGraph graph;

    private void initializeCells(BoardGraph graph, Cell startCell, Cell endCell){
        cellMap = new HashMap<>();
        this.graph = graph;

        Cell[][] starting_cells = graph.getMatrix();
        for(Cell[] cellArray : starting_cells){
            for(Cell cell : cellArray){
                double distance = cell.euclideanDist(endCell);
                cellMap.put(cell, distance);
            }
        }
        cellMap.put(startCell, 0.0);
    }

    @Override
    public void initializeSearch(BoardGraph graph) {
        super.initializeSearch(graph);
        initializeCells(graph, startCell, endCell);
    }


    public Cell getLowestScore() {
        if (openSet.isEmpty()) {
            return null;
        }

        Cell bestCell = openSet.get(0);
        for (Cell cell : openSet) {
            if (cellMap.get(cell) < cellMap.get(bestCell)) {
                bestCell = cell;
            }
        }

        return bestCell;
    }

    @Override
    public void stepSearch() {
        if (openSet.isEmpty()) {
            isRunning = false;
            return;
        }

        Cell currentCell = getLowestScore();

        openSet.remove(currentCell);
        closedSet.add(currentCell);
        setupCellTypes();

        if (isGoal(endCell, currentCell)) {
            isRunning = false;
            return;
        }

        for (Cell neighbour : graph.getNeighbours(currentCell)) {
            if (neighbour.getCellType() != Cell.CellType.WALL && !closedSet.contains(neighbour) && isPossibleToMove(currentCell, neighbour)) {
                neighbour.setCameFrom(currentCell);
                if(!openSet.contains(neighbour))
                    openSet.add(neighbour);
            }
        }

    }
    @Override
    public String toString() {
        return NAME;
    }

}
