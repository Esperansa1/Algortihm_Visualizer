package Visualizer.SearchAlgortihms;

import Visualizer.Cell;
import Visualizer.SearchAlgortihms.AStar.AStarCell;

import java.util.ArrayList;

public abstract class SearchAlgorithm<T extends Cell>  {

    public boolean isFinished = false;

    protected ArrayList<T> openSet;
    protected ArrayList<T> closedSet;


    public void reconstructPath(Cell endCell, Cell startCell){
        Cell current = endCell;
        while(!current.equals(startCell)){
            current = current.getCameFrom();
            current.setCellType(Cell.CellType.PATH);
        }
        startCell.setCellType(Cell.CellType.START_POINT);

    }

    public boolean isGoal(Cell current, Cell endCell){
        return current.equals(endCell);
    }

    public void initializeSearch(Cell[][] cells, Cell startCell, Cell endCell) {
        if(startCell == null || endCell == null) return;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                if(cell.getCellType() == Cell.CellType.PATH || cell.getCellType() == Cell.CellType.CLOSE_SET || cell.getCellType() == Cell.CellType.OPEN_SET){
                    cell.setCellType(Cell.CellType.EMPTY);
                }
            }
        }

        this.openSet = new ArrayList<>();
        this.closedSet = new ArrayList<>();
        isFinished = false;

    }
    public void setupCellTypes(Cell startCell, Cell endCell) {

        for(T cell : openSet){
            cell.setCellType(Cell.CellType.OPEN_SET);
        }
        for(T cell : closedSet){
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

        public void initializeNeighbours(Cell[][] cells, boolean allowDiagonals){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].setupNeighbours(cells, allowDiagonals);
            }
        }
    }

    public abstract void stepSearch();
}
