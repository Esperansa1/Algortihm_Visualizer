package Visualizer.SearchAlgortihms;

import Visualizer.Cell;
import java.util.ArrayList;

public abstract class SearchAlgorithm  {
    public boolean isRunning;
    protected ArrayList<Cell> openSet;
    protected ArrayList<Cell> closedSet;
    protected Cell startCell, endCell;


    public void reconstructPath(Cell currentCell){
        Cell current = currentCell;
        while(!current.equals(startCell)){
            current = current.getCameFrom();
            System.out.println(current);
            current.setCellType(Cell.CellType.PATH);
        }
        startCell.setCellType(Cell.CellType.START_POINT);

    }

    public boolean isGoal(Cell current, Cell endCell){
        return current.equals(endCell);
    }

    public void initializeSearch(Cell[][] cells, Cell startCell, Cell endCell) {
        if(startCell == null || endCell == null) return;

        for (Cell[] value : cells) {
            for (Cell cell : value) {
                Cell.CellType cellType = cell.getCellType();
                if (cellType != Cell.CellType.START_POINT && cellType != Cell.CellType.END_POINT && cellType != Cell.CellType.WALL) {
                    cell.setCellType(Cell.CellType.EMPTY);
                }
            }
        }

        initializeNeighbours(cells);
        this.openSet = new ArrayList<>();
        this.closedSet = new ArrayList<>();

        this.startCell = startCell;
        this.endCell = endCell;

        this.openSet.add(startCell);

        isRunning = true;

    }
    public void setupCellTypes() {

        for(Cell cell : openSet){
            cell.setCellType(Cell.CellType.OPEN_SET);
        }
        for(Cell cell : closedSet){
            cell.setCellType(Cell.CellType.CLOSE_SET);
        }
        startCell.setCellType(Cell.CellType.START_POINT);
        endCell.setCellType(Cell.CellType.END_POINT);


    }

    protected boolean isPossibleToMove(Cell current, Cell wanted){
        int rowDifference = current.getRow() - wanted.getRow();
        int colDifference = current.getCol() - wanted.getCol();

        boolean[] walls = current.getWalls();

        if (rowDifference == 1 && walls[0]) {
            return false;  // Wall above
        } else if (colDifference == -1 && walls[1]) {
            return false;  // Wall to the right
        } else if (rowDifference == -1 && walls[2]) {
            return false;  // Wall below
        } else if (colDifference == 1 && walls[3]) {
            return false;  // Wall to the left
        }

        return true;

    }


    public void initializeNeighbours(Cell[][] cells){
        for(Cell[] cellArray : cells) {
            for(Cell cell : cellArray) {
                cell.setupNeighbours(cells);
            }
        }
    }

    public abstract void stepSearch();
}
