package Visualizer.SearchAlgortihms;

import Visualizer.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Pledge extends SearchAlgorithm {

    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

    private Cell[][] cells;
    private Cell currentCell;
    private int currentDirection;

    public void initializeSearch(Cell[][] cells, Cell startCell, Cell endCell) {
        super.initializeSearch(cells,startCell,endCell);
        currentDirection = RIGHT;
        currentCell = startCell;
        this.cells = cells;

    }

    private void moveForward(){
        Cell forward = getForward();
        if(currentCell.getCellType() != Cell.CellType.START_POINT && currentCell.getCellType() != Cell.CellType.END_POINT){
            currentCell.setCellType(Cell.CellType.CLOSE_SET);
        }
        if(forward != null && forward.getCameFrom() == null)
            forward.setCameFrom(currentCell);
        currentCell = forward;

    }

    @Override
    public void stepSearch() {
        if (isGoal(endCell, currentCell)) {
            reconstructPath(currentCell);
            isRunning = false;
            return;
        }
        if(currentCell.equals(startCell)){
            while (noWallOnForward()){
                moveForward();
            }
            turnLeft();
        }


        if(isRightClear()){
            turnRight();
            moveForward();
        }else if(noWallOnForward()){
            moveForward();
        }else{
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
        return "Pledge's Algorithm";
    }


}
