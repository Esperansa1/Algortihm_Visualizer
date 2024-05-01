package Visualizer.Model.SearchAlgortihms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

public class Pledge extends SearchAlgorithm {

    public static final String NAME = "Pledge's Algorithm";

    /**
     * Constants representing the four directions: UP, DOWN, LEFT, RIGHT.
     */
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

    /**
     * The BoardGraph representing the maze.
     */
    private BoardGraph graph;

    /**
     * The current cell being visited by the algorithm.
     */
    private Cell currentCell;

    /**
     * The current direction the algorithm is facing.
     */
    private int currentDirection;

    /**
     * A 2D array to keep track of the number of steps taken at each cell.
     * Used to detect and handle loops.
     */
    private int[][] stepsCounter;

    /**
     * Initializes the search by setting up the graph and the stepsCounter array.
     *
     * @param graph The BoardGraph representing the maze.
     */
    public void initializeSearch(BoardGraph graph) {
        super.initializeSearch(graph);
        this.graph = graph;

        // Used to find loops
        stepsCounter = new int[graph.getMatrix().length][graph.getMatrix()[0].length];
    }

    /**
     * Resets the algorithm by setting the currentDirection to RIGHT and the currentCell to the startCell.
     */
    @Override
    public void resetAlgorithm() {
        super.resetAlgorithm();
        currentDirection = RIGHT;
        currentCell = startCell;
    }

    /**
     * Moves the algorithm forward to the next cell in the current direction.
     * Updates the cell types and cameFrom properties accordingly.
     */
    private void moveForward() {
        Cell forward = getForward();
        if (currentCell.getCellType() != Cell.CellType.START_POINT && currentCell.getCellType() != Cell.CellType.END_POINT) {
            currentCell.setCellType(Cell.CellType.CLOSE_SET);
            closedSet.add(currentCell);
        }
        if (forward != null && forward.getCameFrom() == null)
            forward.setCameFrom(currentCell);
        currentCell = forward;
        closedSet.add(currentCell);
    }

    /**
     * Performs a single step of Pledge's algorithm.
     */
    @Override
    public void stepSearch() {
        if (isGoal(endCell, currentCell)) {
            isRunning = false;
            return;
        }

        Cell[][] cells = graph.getMatrix();
        if (closedSet.size() == cells.length * cells[0].length || stepsCounter[currentCell.getRow()][currentCell.getCol()] >= 5) {
            // All reachable cells have been explored, but the goal was not found
            isRunning = false;
            return;
        }

        stepsCounter[currentCell.getRow()][currentCell.getCol()]++;

        if (isRightClear()) {
            turnRight();
            moveForward();
        } else if (noWallOnForward()) {
            moveForward();
        } else {
            turnLeft();
        }
    }

    /**
     * Checks if there is a clear path to the right of the current cell.
     *
     * @return true if there is no wall to the right, false otherwise.
     */
    private boolean isRightClear() {
        boolean[] walls = currentCell.getWalls();

        turnRight();
        Cell rightCell = getForward();
        turnLeft();
        if (rightCell != null && rightCell.getCellType() == Cell.CellType.WALL)
            return false;

        switch (currentDirection) {
            case UP -> {
                return !walls[1];
            }
            case RIGHT -> {
                return !walls[2];
            }
            case DOWN -> {
                return !walls[3];
            }
            case LEFT -> {
                return !walls[0];
            }
        }
        return false;
    }

    /**
     * Returns the cell in front of the current cell based on the current direction.
     *
     * @return The cell in front of the current cell, or null if there is no valid cell in that direction.
     */
    private Cell getForward() {
        Cell[][] cells = graph.getMatrix();
        int numRows = cells.length;
        int numCols = cells[0].length;

        int col = currentCell.getCol();
        int row = currentCell.getRow();

        // Right
        if (currentDirection == RIGHT) {
            if (col + 1 < numCols && isPossibleToMove(currentCell, cells[row][col + 1])) {
                return cells[row][col + 1];
            } else {
                return null;
            }
        }

        // Left
        if (currentDirection == LEFT) {
            if (col - 1 >= 0 && isPossibleToMove(currentCell, cells[row][col - 1])) {
                return cells[row][col - 1];
            } else {
                return null;
            }
        }

        // Down
        if (currentDirection == DOWN) {
            if (row + 1 < numRows && isPossibleToMove(currentCell, cells[row + 1][col])) {
                return cells[row + 1][col];
            } else {
                return null;
            }
        }

        // Up
        if (currentDirection == UP) {
            if (row - 1 >= 0 && isPossibleToMove(currentCell, cells[row - 1][col])) {
                return cells[row - 1][col];
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Checks if there is no wall in front of the current cell based on the current direction.
     *
     * @return true if there is no wall in front, false otherwise.
     */
    private boolean noWallOnForward() {
        boolean[] walls = currentCell.getWalls();

        Cell forward = getForward();

        if (forward != null && forward.getCellType() == Cell.CellType.WALL)
            return false;

        switch (currentDirection) {
            case UP -> {
                return !walls[0];
            }
            case RIGHT -> {
                return !walls[1];
            }
            case DOWN -> {
                return !walls[2];
            }
            case LEFT -> {
                return !walls[3];
            }
        }
        return false;
    }

    /**
     * Turns the algorithm's direction 90 degrees counter-clockwise.
     */
    private void turnLeft() {
        switch (currentDirection) {
            case UP -> currentDirection = LEFT;
            case DOWN -> currentDirection = RIGHT;
            case LEFT -> currentDirection = DOWN;
            case RIGHT -> currentDirection = UP;
        }
    }

    /**
     * Turns the algorithm's direction 90 degrees clockwise.
     */
    private void turnRight() {
        switch (currentDirection) {
            case UP -> currentDirection = RIGHT;
            case DOWN -> currentDirection = LEFT;
            case LEFT -> currentDirection = UP;
            case RIGHT -> currentDirection = DOWN;
        }
    }

    /**
     * Returns the name of the algorithm.
     *
     * @return The name of the algorithm.
     */
    @Override
    public String toString() {
        return NAME;
    }
}