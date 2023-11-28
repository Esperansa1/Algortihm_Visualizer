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
        this.cells[startCell.getCell().getRow()][startCell.getCell().getCol()] = startCell;
        this.cells[endCell.getCell().getRow()][endCell.getCell().getCol()] = endCell;

    }

    private void initializeCellValues(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                AStarCell cell = cells[i][j];

                cell.g = Double.MAX_VALUE;
                cell.h = cell.euclideanDist(endCell);
                cell.f = cell.g + cell.h;
            }
        }
        this.startCell.g = 0;
    }

    public void initializeNeighbours(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j].setupNeighbours(cells);
            }
        }
        this.startCell.setupNeighbours(cells);
        this.endCell.setupNeighbours(cells);
    }

    @Override
    public void initializeSearch(Cell startCell, Cell endCell, Cell[][] cells) {
        this.startCell = new AStarCell(startCell);
        this.endCell = new AStarCell(endCell);

        this.openSet = new ArrayList<>();
        this.openSet.add(this.startCell);
        this.closedSet = new ArrayList<>();

        if(startCell != null && endCell != null){
            initializeCellArray(cells);
            initializeCellValues();
            initializeNeighbours();

            System.out.println("Initialization Successful");
        }

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
    public void stepSearch() {
        if (isFinished) {
            return;
        }

        AStarCell currentCell = getLowestFScore();

        openSet.remove(currentCell);
        closedSet.add(currentCell);

        if(isGoal(endCell.getCell(), startCell.getCell(), currentCell.getCell())) return; // CHANGE IT SO RECONSTRUCT PATH WILL BE CALLED WITHIN THE IF STATEMENT


        for (AStarCell neighbour : currentCell.getNeighbours()) {
            if (neighbour.getCell().getCellType() == Cell.CellType.WALL || closedSet.contains(neighbour))
                continue;

            double tentativeGScore = currentCell.g + neighbour.weight +
                    currentCell.euclideanDist(neighbour);

            System.out.println(tentativeGScore + " " + neighbour.g);
            if (tentativeGScore < neighbour.g) {
                neighbour.getCell().setCameFrom(currentCell.getCell());
                neighbour.g = tentativeGScore;
                neighbour.f = tentativeGScore + neighbour.h;

                if (!openSet.contains(neighbour)) {
                    openSet.add(neighbour);
                }
            }
        }
        System.out.println(openSet);
    }
}
