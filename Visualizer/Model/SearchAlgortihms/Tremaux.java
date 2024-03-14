package Visualizer.Model.SearchAlgortihms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.ArrayList;
import java.util.Stack;

public class Tremaux extends SearchAlgorithm {

    public static final String NAME = "Tremaux's Algorithm";
    private BoardGraph graph;

    private Stack<Cell> marks;
    private Cell currentCell;
    @Override
    public void initializeSearch(BoardGraph graph) {
        super.initializeSearch(graph);
        this.graph = graph;
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
        for (Cell neighbour : graph.getNeighbours(currentCell)) {
            if (neighbour.getCellType() != Cell.CellType.WALL && !closedSet.contains(neighbour) && isPossibleToMove(currentCell, neighbour) && !marks.contains(neighbour)) {
                cells.add(neighbour);
            }
        }
        return cells;
    }


    public Cell getRandomNeighbour(ArrayList<Cell> neighbours) {
        return neighbours.get((int)(Math.random() * neighbours.size()));
    }


    @Override
    public void stepSearch() {
        if (marks.isEmpty() || isGoal(endCell, currentCell)) {
            isRunning = false;
            return;
        }

        setupCellTypes();

        ArrayList<Cell> unvisitedNeighbours  = getUnvisitedNeighbours(currentCell);

        Cell next = unvisitedNeighbours.isEmpty() ? null :  getRandomNeighbour(unvisitedNeighbours);
        if(next != null){
            closedSet.add(currentCell);
            if(!graph.getNeighbours(next).isEmpty())
                marks.push(next);
            next.setCameFrom(currentCell);
        }else if(!marks.isEmpty()){
            currentCell = marks.pop();
        }
    }

    @Override
    public String toString() {
        return NAME;
    }


}
