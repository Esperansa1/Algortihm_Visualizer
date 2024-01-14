package Visualizer.MazeAlgorithms;

import Visualizer.Cell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Prims extends MazeAlgorithm {

    private final HashSet<Cell> closedSet;
    private final ArrayList<Cell> uncheckedNeighbours;
    private Cell current, previous;
    private boolean isFinished;

    public Prims() {
        closedSet = new HashSet<>();
        uncheckedNeighbours = new ArrayList<>();
    }

    @Override
    public void stepMazeGeneration(Cell[][] cells) {
        if(current == null){
            int randomX = (int)(Math.random() * cells.length);
            int randomY = (int)(Math.random() * cells.length);

            current = cells[randomY][randomX];
            previous = current;
            closedSet.add(current);

            current.setupNeighbours(cells, false);
            uncheckedNeighbours.addAll(current.getNeighbours());
        }
        if(uncheckedNeighbours.isEmpty())
            return;

        closedSet.add(current);

        for(Cell neighbour : current.getNeighbours()){
            if(!closedSet.contains(neighbour)){
                closedSet.add(neighbour);
                current.removeWall(neighbour);
                neighbour.setupNeighbours(cells, false);
                uncheckedNeighbours.add(neighbour);

            }
        }
        current = popRandomUncheckedCell();

    }

    private void highlight(Cell current){

        if(previous == null || current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT) return;

        previous.setCellType(Cell.CellType.EMPTY);
        current.setCellType(Cell.CellType.HIGHLIGHT);
        previous = current;
    }

    public Cell popRandomUncheckedCell(){
        int index = (int)(Math.random() * uncheckedNeighbours.size());

        return uncheckedNeighbours.remove(index);
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

    @Override
    public boolean isFinished() {
        return isFinished;
    }

}
