package Visualizer.MazeAlgorithms;

import Visualizer.Cell;
import java.util.ArrayList;
import java.util.HashSet;

public class Prims extends MazeAlgorithm {

    private HashSet<Cell> closedSet;
    private ArrayList<Cell> uncheckedNeighbours;
    private Cell current, previous;
    private boolean isRunning;

    @Override
    public void initializeMazeGeneration(Cell[][] cells) {
        super.initializeMazeGeneration(cells);
        isRunning = true;

        uncheckedNeighbours = new ArrayList<>();
        closedSet = new HashSet<>();

        int randomX = (int)(Math.random() * cells.length);
        int randomY = (int)(Math.random() * cells.length);

        current = cells[randomY][randomX];
        previous = current;
        closedSet.add(current);
        uncheckedNeighbours.addAll(current.getNeighbours());

    }

    @Override
    public void stepMazeGeneration(Cell[][] cells) {

        if(uncheckedNeighbours.isEmpty()) {
            if(!(previous == null || current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT))
                previous.setCellType(Cell.CellType.EMPTY);
            isRunning = false;
            return;
        }

        closedSet.add(current);

        for(Cell neighbour : current.getNeighbours()){
            if(!closedSet.contains(neighbour)){
                closedSet.add(neighbour);
                current.removeWall(neighbour);
                uncheckedNeighbours.add(neighbour);
                highlight(neighbour);

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

    @Override
    public boolean isRunning() {
        return isRunning;
    }

}
