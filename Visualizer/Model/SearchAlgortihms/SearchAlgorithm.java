package Visualizer.Model.SearchAlgortihms;

import Visualizer.*;
import Visualizer.Model.BoardModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class SearchAlgorithm extends Observable<BoardObserver> {

    /**
     * Indicates whether the search algorithm is currently running or not.
     */
    public boolean isRunning;

    /**
     * A list of cells that are open for exploration during the search.
     */
    protected ArrayList<Cell> openSet;

    /**
     * A set of cells that have already been explored during the search.
     */
    protected Set<Cell> closedSet;

    /**
     * The starting cell for the search.
     */
    protected Cell startCell;

    /**
     * The ending cell (goal) for the search.
     */
    protected Cell endCell;

    /**
     * Checks if the given current cell is the goal (end cell).
     *
     * @param current The current cell.
     * @param endCell The end cell (goal).
     * @return true if the current cell is the end cell, false otherwise.
     */
    public boolean isGoal(Cell current, Cell endCell) {
        return current.equals(endCell);
    }

    /**
     * Resets the algorithm by clearing the openSet and closedSet, setting the start and end cells,
     * and adding the start cell to the openSet.
     */
    public void resetAlgorithm() {
        if (openSet != null)
            openSet.forEach(cell -> {
                if (cell != null) cell.setCameFrom(null);
            });
        if (closedSet != null)
            closedSet.forEach(cell -> {
                if (cell != null) cell.setCameFrom(null);
            });

        this.openSet = new ArrayList<>();
        this.closedSet = new HashSet<>();

        if (this.startCell != null && this.endCell != null) {
            this.startCell.setCameFrom(null);
            this.endCell.setCameFrom(null);
            this.openSet.add(startCell);
        }

        isRunning = true;
    }

    /**
     * Initializes the search by resetting the cell types and setting the start and end cells.
     *
     * @param graph The BoardGraph representing the maze.
     */
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

    /**
     * Sets the cell types based on the openSet, closedSet, start cell, and end cell.
     */
    public void setupCellTypes() {
        for (Cell cell : openSet) {
            cell.setCellType(Cell.CellType.OPEN_SET);
        }
        for (Cell cell : closedSet) {
            cell.setCellType(Cell.CellType.CLOSE_SET);
        }
        startCell.setCellType(Cell.CellType.START_POINT);
        endCell.setCellType(Cell.CellType.END_POINT);
    }

    /**
     * Checks if it's possible to move from the current cell to the wanted cell based on the wall positions.
     *
     * @param current The current cell.
     * @param wanted  The cell to move to.
     * @return true if it's possible to move from the current cell to the wanted cell, false otherwise.
     */
    protected boolean isPossibleToMove(Cell current, Cell wanted) {
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

    /**
     * Performs a single step of the search algorithm.
     * This method must be implemented by concrete subclasses.
     */
    public abstract void stepSearch();
}