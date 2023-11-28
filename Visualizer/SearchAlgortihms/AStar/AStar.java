package Visualizer.SearchAlgortihms.AStar;

import Visualizer.Cell;
import Visualizer.SearchAlgortihms.SearchAlgorithm;

import java.util.ArrayList;

public class AStar extends SearchAlgorithm<AStarCell> {

    private AStarCell startCell, endCell;
    private AStarCell[][] cells;


    private void initializeCellArray(Cell[][] cells){
        this.cells = new AStarCell[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j] = new AStarCell(cells[i][j]);
            }
        }
    }

    private void initCellValues(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                AStarCell cell = cells[i][j];
                cell.g = cell.euclideanDist(startCell);
                cell.h = cell.euclideanDist(endCell);
                cell.f = cell.g + cell.h;
            }
        }
    }

    @Override
    public void initializeSearch(Cell startCell, Cell endCell, Cell[][] cells) {
        this.startCell = new AStarCell(startCell);
        this.endCell = new AStarCell(endCell);
        initializeCellArray(cells);
        initCellValues();
    }

    public AStarCell getLowestFScore() {
        if (openSet.isEmpty()) {
            return null;
        }

        AStarCell bestCell = openSet.get(0);

        for (AStarCell cell : openSet) {
            if (cell.f < bestCell.f) {
                bestCell = cell;
            }
        }

        return bestCell;
    }

    @Override
    public void stepSearch(ArrayList<AStarCell> cells) {
        if (isFinished) {
            return;
        }

        AStarCell currentCell = getLowestFScore();
//        isGoal(currentCell);

        openSet.remove(currentCell);
        closedSet.add(currentCell);

        for (AStarCell neighbour : currentCell.getNeighbours()) {
            if (neighbour.cell.getCellType() == Cell.CellType.WALL || neighbour.isVisited) {
//                continue;

            double tentativeGScore = currentCell.g + neighbour.weight +
                    heuristicFunction(currentCell, neighbour);

            if (tentativeGScore < neighbour.g) {
                neighbour.parent = currentCell;
                neighbour.g = tentativeGScore;
                neighbour.f = tentativeGScore + neighbour.h;

                if (!openSet.contains(neighbour)) {
                    openSet.add(neighbour);
                }
            }
        }
    }




    }
}
