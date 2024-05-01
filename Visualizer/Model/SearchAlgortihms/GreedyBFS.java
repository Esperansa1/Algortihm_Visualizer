package Visualizer.Model.SearchAlgortihms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.HashMap;
import java.util.Map;

public class GreedyBFS extends SearchAlgorithm {

    public static final String NAME = "GreedyBFS";

    /**
     * A map that stores the heuristic value (distance from the end cell) for each cell in the maze.
     */
    private Map<Cell, Double> cellMap;

    /**
     * The BoardGraph representing the maze.
     */
    private BoardGraph graph;

    /**
     * Initializes the cell map with the heuristic values (distances from the end cell) for all cells in the maze.
     *
     * @param graph    The BoardGraph representing the maze.
     * @param startCell The starting cell of the search.
     * @param endCell  The ending cell of the search.
     */
    private void initializeCells(BoardGraph graph, Cell startCell, Cell endCell) {
        cellMap = new HashMap<>();
        this.graph = graph;

        Cell[][] starting_cells = graph.getMatrix();
        for (Cell[] cellArray : starting_cells) {
            for (Cell cell : cellArray) {
                double distance = cell.euclideanDist(endCell);
                cellMap.put(cell, distance);
            }
        }
        cellMap.put(startCell, 0.0);
    }

    /**
     * Initializes the search by calling the superclass method and initializing the cell map.
     *
     * @param graph The BoardGraph representing the maze.
     */
    @Override
    public void initializeSearch(BoardGraph graph) {
        super.initializeSearch(graph);
        initializeCells(graph, startCell, endCell);
    }

    /**
     * Returns the cell with the lowest heuristic value (distance from the end cell) from the open set.
     *
     * @return The cell with the lowest heuristic value, or null if the open set is empty.
     */
    public Cell getLowestScore() {
        if (openSet.isEmpty()) {
            return null;
        }

        Cell bestCell = openSet.get(0);
        for (Cell cell : openSet) {
            if (cellMap.get(cell) < cellMap.get(bestCell)) {
                bestCell = cell;
            }
        }

        return bestCell;
    }

    /**
     * Performs a single step of the Greedy Best-First Search algorithm.
     */
    @Override
    public void stepSearch() {
        if (openSet.isEmpty()) {
            isRunning = false;
            return;
        }

        Cell currentCell = getLowestScore();

        openSet.remove(currentCell);
        closedSet.add(currentCell);
        setupCellTypes();

        if (isGoal(endCell, currentCell)) {
            isRunning = false;
            return;
        }

        for (Cell neighbour : graph.getNeighbours(currentCell)) {
            if (neighbour.getCellType() != Cell.CellType.WALL && !closedSet.contains(neighbour) && isPossibleToMove(currentCell, neighbour)) {
                neighbour.setCameFrom(currentCell);
                if (!openSet.contains(neighbour))
                    openSet.add(neighbour);
            }
        }
    }

    /**
     * Returns the name of the algorithm.
     *
     * @return The name of the algorithm.
     */
    @Override
    public String toString() {
        return NAME;
    }
}