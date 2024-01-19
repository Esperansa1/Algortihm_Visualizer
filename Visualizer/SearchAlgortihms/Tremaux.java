package Visualizer.SearchAlgortihms;

import Visualizer.Cell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Tremaux extends SearchAlgorithm {

    private Stack<Cell> marks;
    private Cell currentCell;
    @Override
    public void initializeSearch(Cell[][] cells, Cell startCell, Cell endCell) {
        super.initializeSearch(cells, startCell, endCell);
        currentCell = startCell;
        marks = new Stack<>();
        marks.push(startCell);

    }

    @Override
    public void setupCellTypes() {
        for(Cell mark : marks){
            mark.setCellType(Cell.CellType.OPEN_SET);
        }
        super.setupCellTypes();
    }

    private ArrayList<Cell> getUnvisitedNeighbours(Cell currentCell){
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell neighbour : currentCell.getNeighbours()) {
            if (neighbour.getCellType() == Cell.CellType.WALL || closedSet.contains(neighbour) || !isPossibleToMove(currentCell, neighbour) || marks.contains(neighbour)) {
                continue;
            }
            cells.add(neighbour);
        }
        return cells;
    }


    public Cell getRandomNeighbour(ArrayList<Cell> neighbours) {
        return neighbours.get((int)(Math.random() * neighbours.size()));
    }


    @Override
    public void stepSearch() {
        if (marks.isEmpty()) {
            isRunning = false;
            return;
        }
        if (isGoal(endCell, currentCell)) {
            reconstructPath(currentCell);
            isRunning = false;
            return;
        }

        setupCellTypes();

        ArrayList<Cell> unvisitedNeighbours  = getUnvisitedNeighbours(currentCell);

        Cell next = unvisitedNeighbours.isEmpty() ? null :  getRandomNeighbour(unvisitedNeighbours);
        if(next != null){
            closedSet.add(currentCell);
            if(!next.getNeighbours().isEmpty())
                marks.push(next);
            next.setCameFrom(currentCell);
        }else if(!marks.isEmpty()){
            currentCell = marks.pop();
        }
    }

    @Override
    public String toString() {
        return "Tremaux's Algorithm";
    }


}
