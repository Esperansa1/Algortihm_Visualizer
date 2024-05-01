package Visualizer.Model.SearchAlgortihms.AStar;

import Visualizer.BoardGraph;
import Visualizer.Cell;
import Visualizer.Model.SearchAlgortihms.SearchAlgorithm;

import java.util.*;

public class AStar extends SearchAlgorithm {
    public static final String NAME = "A Star (A*)";

    /**
     * A map that stores the A* cost values (f, g, h) for each cell in the maze.
     */
    private Map<Cell, AStarCell> cellMap;

    /**
     * The BoardGraph representing the maze.
     */
    private BoardGraph graph;

    /**
     * Initializes the cell map with the heuristic values (h) and the distances from the start cell (g) for all cells in the maze.
     *
     * @param graph     The BoardGraph representing the maze.
     * @param startCell The starting cell of the search.
     * @param endCell   The ending cell of the search.
     */
    private void initializeCells(BoardGraph graph, Cell startCell, Cell endCell) {
        cellMap = new HashMap<>();
        this.graph = graph;

        Cell[][] starting_cells = graph.getMatrix();
        for (Cell[] cellArray : starting_cells) {
            for (Cell cell : cellArray) {
                double distance = cell.euclideanDist(endCell);
                cellMap.put(cell, new AStarCell(distance, distance, 0));
            }
        }
        cellMap.get(startCell).g = 0;
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
     * Returns the cell with the lowest f-score (f = g + h) from the open set.
     *
     * @return The cell with the lowest f-score, or null if the open set is empty.
     */
    public Cell getLowestFScore() {
        if (openSet.isEmpty()) {
            return null;
        }

        Cell bestCell = openSet.get(0);
        for (Cell cell : openSet) {
            if (cellMap.get(cell).f < cellMap.get(bestCell).f) {
                bestCell = cell;
            }
        }

        return bestCell;
    }

    /**
     * Performs a single step of the A* search algorithm.
     */
    @Override
    public void stepSearch() {
        if (openSet.isEmpty()) {
            isRunning = false;
            return;
        }

        Cell currentCell = getLowestFScore();

        openSet.remove(currentCell);
        closedSet.add(currentCell);
        setupCellTypes();

        if (isGoal(endCell, currentCell)) {
            isRunning = false;
            return;
        }

        for (Cell neighbour : graph.getNeighbours(currentCell)) {
            if (neighbour.getCellType() != Cell.CellType.WALL && !closedSet.contains(neighbour) && isPossibleToMove(currentCell, neighbour)) {

                double tentativeGScore = cellMap.get(currentCell).g + currentCell.euclideanDist(neighbour);

                if (!openSet.contains(neighbour)) {
                    openSet.add(neighbour);
                }

                // If a better GSCore has been found, switch them around
                if (tentativeGScore >= cellMap.get(neighbour).g) {
                    neighbour.setCameFrom(currentCell);
                    cellMap.get(neighbour).g = tentativeGScore;
                    cellMap.get(neighbour).f = tentativeGScore + cellMap.get(neighbour).h;
                }
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