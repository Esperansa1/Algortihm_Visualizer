package Visualizer.SearchAlgortihms;

import Visualizer.Cell;

import java.util.HashMap;
import java.util.Map;

public class GreedyBFS extends SearchAlgorithm {

    private Map<Cell, Double> cellMap;

    private void initializeCells(Cell[][] starting_cells, Cell startCell, Cell endCell){
        for(Cell[] cellArray : starting_cells){
            for(Cell cell : cellArray){
                double distance = cell.euclideanDist(endCell);
                cellMap.put(cell, distance);
            }
        }
        cellMap.put(startCell, 0.0);
    }

    @Override
    public void initializeSearch(Cell[][] cells, Cell startCell, Cell endCell) {
        super.initializeSearch(cells, startCell, endCell);
        cellMap = new HashMap<>();
        initializeCells(cells, startCell, endCell);
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
            reconstructPath(currentCell);
            isRunning = false;
            return;
        }

        for (Cell neighbour : currentCell.getNeighbours()) {
            if (neighbour.getCellType() == Cell.CellType.WALL || closedSet.contains(neighbour) || !isPossibleToMove(currentCell, neighbour))
                continue;

            neighbour.setCameFrom(currentCell);
            if(!openSet.contains(neighbour))
                openSet.add(neighbour);
        }

    }
    @Override
    public String toString() {
        return "GreedyBFS";
    }

}
