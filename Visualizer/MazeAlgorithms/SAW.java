package Visualizer.MazeAlgorithms;

import Visualizer.Cell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class SAW extends MazeAlgorithm {

    private HashSet<Cell> closedSet;
    private Stack<Cell> stack;
    private Cell current, previous;
    private boolean isRunning;

    @Override
    public void initializeMazeGeneration(Cell[][] cells) {
        super.initializeMazeGeneration(cells);
        isRunning = true;

        closedSet = new HashSet<>();
        stack = new Stack<>();

        int randomY = (int)(Math.random() * cells.length);
        int randomX = (int)(Math.random() * cells.length);

        current = cells[randomY][randomX];
        previous = current;

        closedSet.add(current);

    }

    @Override
    public void stepMazeGeneration(Cell[][] cells) {
        if(closedSet.size() == cells.length * cells[0].length){
            isRunning = false;
            if(previous != null) {
                previous.setCellType(Cell.CellType.EMPTY);
            }
            return;
        }

        highlight(current);

        Cell next = getRandomNeighbour(current);
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

    public Cell getRandomNeighbour(Cell current) {

        ArrayList<Cell> neighbours = current.getNeighbours();
        List<Cell> filteredNeighbours = neighbours.stream().filter(cell -> !closedSet.contains(cell)).toList();

        if(filteredNeighbours.size() == 0)
            return null;
        int randomIndex = (int)(Math.random() * filteredNeighbours.size());
        return filteredNeighbours.get(randomIndex);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

}
