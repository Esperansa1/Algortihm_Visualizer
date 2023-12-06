package Visualizer.MazeAlgorithms;

import Visualizer.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;


public class SAW implements MazeAlgorithm {

    private final HashSet<Cell> closedSet;
    private final Stack<Cell> stack;
    private Cell current;
    private Cell previous;
    public boolean isFinished;
    public SAW() {
        closedSet = new HashSet<>();
        stack = new Stack<>();
    }


    @Override
    public void stepMazeGeneration(Cell[][] cells) {
        if(current == null){
            current = cells[0][0];
            closedSet.add(current);
        }
        if(previous == null)
            previous = cells[0][0];

        if(closedSet.size() == cells.length * cells[0].length){
            isFinished = true;
            if(previous != null) {
                previous.setCellType(Cell.CellType.EMPTY);
            }
            return;
        }

        highlight(current);

        Cell next = getRandomNeighbour(cells, current);
        if(next != null){
            closedSet.add(next);
            stack.push(current);
            current.removeWall(next);
            current = next;

        }else if(stack.size() != 0){
            current = stack.pop();
        }
    }

    private void highlight(Cell current){

        if(previous == null || current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT) return;

        previous.setCellType(Cell.CellType.EMPTY);
        current.setCellType(Cell.CellType.HIGHLIGHT);
        previous = current;
    }

    public Cell getRandomNeighbour(Cell[][] cells, Cell current) {
        ArrayList<Cell> possibleOptions = new ArrayList<>();

        int numRows = cells.length;
        int numCols = cells[0].length;

        int row = current.getRow();
        int col = current.getCol();

        Cell top = (row > 0) ? cells[row-1][col] : null;
        Cell right = (col < numCols - 1) ? cells[row][col+1] : null;
        Cell bottom = (row < numRows - 1) ? cells[row+1][col] : null;
        Cell left = (col > 0) ? cells[row][col-1] : null;

        if(top != null && !closedSet.contains(top)){
            possibleOptions.add(top);
        }
        if(right != null && !closedSet.contains(right)){
            possibleOptions.add(right);
        }
        if(bottom != null && !closedSet.contains(bottom)){
            possibleOptions.add(bottom);
        }
        if(left != null && !closedSet.contains(left)){
            possibleOptions.add(left);
        }

        int randomIndex = (int)(Math.random()*possibleOptions.size());

        if(possibleOptions.size() == 0) return null;

        return possibleOptions.get(randomIndex);
    }




}
