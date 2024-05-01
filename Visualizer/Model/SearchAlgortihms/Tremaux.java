package Visualizer.Model.SearchAlgortihms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.ArrayList;
import java.util.Stack;

public class Tremaux extends SearchAlgorithm {

    public static final String NAME = "Tremaux's Algorithm";
    private BoardGraph graph;

    private Stack<Cell> marks; // Cells visited and potentially needing backtracking
    private Cell currentCell;

    @Override
    public void initializeSearch(BoardGraph graph) {
        super.initializeSearch(graph);
        this.graph = graph;
        currentCell = startCell;
        marks = new Stack<>();
        marks.push(startCell); // Begin at the start cell
    }

    @Override
    public void setupCellTypes() {
        for (Cell mark : marks) {
            mark.setCellType(Cell.CellType.OPEN_SET); // Mark cells on the path for visualization
        }
        super.setupCellTypes(); // Handle any additional setup required by the base class
    }

    private ArrayList<Cell> getUnvisitedNeighbours(Cell currentCell) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell neighbour : graph.getNeighbours(currentCell)) {
            if (neighbour.getCellType() != Cell.CellType.WALL &&      // Not a wall
                    !closedSet.contains(neighbour) &&                     // Not previously explored
                    isPossibleToMove(currentCell, neighbour) &&           // Check any custom move constraints
                    !marks.contains(neighbour)) {                         // Not already marked
                cells.add(neighbour);
            }
        }
        return cells;
    }

    public Cell getRandomNeighbour(ArrayList<Cell> neighbours) {
        return neighbours.get((int) (Math.random() * neighbours.size())); // Choose any unvisited neighbor randomly
    }

    @Override
    public void stepSearch() {
        if (marks.isEmpty() || isGoal(endCell, currentCell)) {
            isRunning = false; // Search complete if there's no path or we reached the goal
            return;
        }

        setupCellTypes(); // Update cell visualizations

        ArrayList<Cell> unvisitedNeighbours = getUnvisitedNeighbours(currentCell);

        Cell next = unvisitedNeighbours.isEmpty() ? null : getRandomNeighbour(unvisitedNeighbours);

        if (next != null) {
            closedSet.add(currentCell);  // Mark the current cell as explored
            if (!graph.getNeighbours(next).isEmpty())
                marks.push(next);        // Push for possible backtracking
            next.setCameFrom(currentCell); // Record the path
        } else if (!marks.isEmpty()) {
            currentCell = marks.pop();   // Backtrack if no valid moves
        }
    }

    @Override
    public String toString() {
        return NAME; // Provide a descriptive name for the algorithm
    }
}
