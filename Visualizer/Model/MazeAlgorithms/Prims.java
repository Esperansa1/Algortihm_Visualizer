package Visualizer.Model.MazeAlgorithms;

import Visualizer.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

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

        ArrayList<Cell> neighbours = getUnvisitedNeighbours();

        uncheckedNeighbours.addAll(neighbours);
        if(!neighbours.isEmpty()) {
            Cell neighbour = current.getNeighbours().get((int)(Math.random() * current.getNeighbours().size()));
            closedSet.add(neighbour);
            current.removeWall(neighbour);
            highlight(neighbour);
        }
        current = popRandomUncheckedCell();
    }

    private ArrayList<Cell> getUnvisitedNeighbours(){
        ArrayList<Cell> neighbours = current.getNeighbours();
        neighbours = neighbours.stream().filter(o -> !closedSet.contains(o)).collect(Collectors.toCollection(ArrayList::new));
        return neighbours;
    }

    private void highlight(Cell current){

        if(previous == null || current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT) return;

        previous.setCellType(Cell.CellType.EMPTY);
        current.setCellType(Cell.CellType.HIGHLIGHT);
        previous = current;
    }

    private Cell popRandomUncheckedCell(){
        int index = (int)(Math.random() * uncheckedNeighbours.size());
        return uncheckedNeighbours.remove(index);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public String toString() {
        return "Prim's Algorithm";
    }
}
