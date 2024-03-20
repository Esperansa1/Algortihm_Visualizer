package Visualizer.Model.SearchAlgortihms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

public class Pledge extends SearchAlgorithm {

    public static final String NAME = "Pledge's Algorithm";

    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

    private BoardGraph graph;
    private Cell currentCell;
    private int currentDirection;
    private int stepsCounter;

    public void initializeSearch(BoardGraph graph) {
        super.initializeSearch(graph);
        this.graph = graph;
    }

    @Override
    public void resetAlgorithm() {
        super.resetAlgorithm();
        currentDirection = RIGHT;
        currentCell = startCell;
        stepsCounter = 0;



    }

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

    @Override
    public void stepSearch() {
        if (isGoal(endCell, currentCell) || stepsCounter >= graph.getMatrix().length *  graph.getMatrix().length * 1.5) {
            isRunning = false;
            return;
        }

        Cell[][] cells = graph.getMatrix();
        if (closedSet.size() == cells.length * cells[0].length) {
            // All reachable cells have been explored, but the goal was not found
            isRunning = false;
            return;
        }

        if (isRightClear()) {
            turnRight();
            moveForward();
        } else if (noWallOnForward()) {
            moveForward();
        } else {
            turnLeft();
        }
    }

    private boolean isRightClear(){
        boolean[] walls = currentCell.getWalls();

        turnRight();
        Cell rightCell = getForward();
        turnLeft();
        if(rightCell != null && rightCell.getCellType() == Cell.CellType.WALL)
            return false;

        switch(currentDirection){
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

    private Cell getForward() {

        Cell[][] cells = graph.getMatrix();
        // RIGHT LEFT DOWN UP
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
        if(currentDirection == DOWN) {
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

    private boolean noWallOnForward(){
        boolean[] walls = currentCell.getWalls();

        Cell forward = getForward();

        if(forward != null && forward.getCellType() == Cell.CellType.WALL)
            return false;

        switch(currentDirection){
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

    private void turnLeft() {
        switch (currentDirection) {
            case UP -> currentDirection = LEFT;
            case DOWN -> currentDirection = RIGHT;
            case LEFT -> currentDirection = DOWN;
            case RIGHT -> currentDirection = UP;
        }
    }

    private void turnRight() {
        switch (currentDirection) {
            case UP -> currentDirection = RIGHT;
            case DOWN -> currentDirection = LEFT;
            case LEFT -> currentDirection = UP;
            case RIGHT -> currentDirection = DOWN;
        }
    }

    @Override
    public String toString() {
        return NAME;
    }


}
