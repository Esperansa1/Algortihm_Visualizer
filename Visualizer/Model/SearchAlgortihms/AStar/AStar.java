package Visualizer.Model.SearchAlgortihms.AStar;

import Visualizer.Cell;
import Visualizer.Model.SearchAlgortihms.SearchAlgorithm;

import java.util.*;

public class AStar extends SearchAlgorithm {
    private Map<Cell, AStarCell> cellMap;

    private void initializeCells(Cell[][] starting_cells, Cell startCell, Cell endCell){
        cellMap = new HashMap<>();


        for(Cell[] cellArray : starting_cells){
            for(Cell cell : cellArray){
                double distance = cell.euclideanDist(endCell);
                cellMap.put(cell, new AStarCell(distance, distance, 0));
            }
        }
        cellMap.get(startCell).g = 0;
    }

    @Override
    public void initializeSearch(Cell[][] cells) {
        super.initializeSearch(cells);
        initializeCells(cells, startCell, endCell);
    }

    public Cell getLowestFScore() {
        if (openSet.isEmpty()) {
            return null;
        }

        Cell bestCell = openSet.get(0);
        for (Cell cell : openSet) {
            if (cellMap.get(cell).f < cellMap.get(bestCell).f) {
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

        Cell currentCell = getLowestFScore();

        openSet.remove(currentCell);
        closedSet.add(currentCell);
        setupCellTypes();

        if (isGoal(endCell, currentCell)) {
            isRunning = false;
            return;
        }

        for (Cell neighbour : currentCell.getNeighbours()) {
            if (neighbour.getCellType() == Cell.CellType.WALL || closedSet.contains(neighbour) || !isPossibleToMove(currentCell, neighbour))
                continue;

            double tentativeGScore = cellMap.get(currentCell).g + currentCell.euclideanDist(neighbour);

            if (!openSet.contains(neighbour)) {
                openSet.add(neighbour);
            }else if(tentativeGScore >= cellMap.get(neighbour).g){
                continue;
            }

            neighbour.setCameFrom(currentCell);
            cellMap.get(neighbour).g = tentativeGScore;
            cellMap.get(neighbour).f = tentativeGScore +  cellMap.get(neighbour).h;

        }
    }

    @Override
    public String toString() {
        return "A Star (A*)";
    }
}
