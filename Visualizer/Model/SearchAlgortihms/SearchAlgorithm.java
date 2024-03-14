package Visualizer.Model.SearchAlgortihms;

import Visualizer.*;
import Visualizer.Model.BoardModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class SearchAlgorithm extends Observable<BoardObserver> {

    public boolean isRunning;
    protected ArrayList<Cell> openSet;
    protected Set<Cell> closedSet;
    protected Cell startCell, endCell;


    public boolean isGoal(Cell current, Cell endCell){
        return current.equals(endCell);
    }


    public void resetAlgorithm(){

        if(openSet != null)
            openSet.forEach(cell -> { if(cell != null) cell.setCameFrom(null); });
        if(closedSet != null)
            closedSet.forEach(cell -> { if(cell != null) cell.setCameFrom(null); });


        this.openSet = new ArrayList<>();
        this.closedSet = new HashSet<>();

        if(this.startCell != null && this.endCell != null) {
            this.startCell.setCameFrom(null);
            this.endCell.setCameFrom(null);
            this.openSet.add(startCell);
        }

        isRunning = true;
    }

    public void initializeSearch(BoardGraph graph) {
        Cell[][] cells = graph.getMatrix();
        for (Cell[] value : cells) {
            for (Cell cell : value) {
                Cell.CellType cellType = cell.getCellType();
                if (cellType != Cell.CellType.START_POINT && cellType != Cell.CellType.END_POINT && cellType != Cell.CellType.WALL) {
                    cell.setCellType(Cell.CellType.EMPTY);
                }
            }
        }

        BoardModel boardManager = Controller.getInstance();
        this.startCell = boardManager.getStartCell();
        this.endCell = boardManager.getEndCell();

        resetAlgorithm();

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

    public abstract void stepSearch();
}
