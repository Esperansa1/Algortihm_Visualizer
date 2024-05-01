package Visualizer.Model.MazeAlgorithms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Prims extends MazeAlgorithm {

    public static final String NAME = "Prim's Algorithm";

    /**
     * The BoardGraph representing the maze.
     */
    private BoardGraph graph;

    /**
     * A set of visited cells in the maze generation process.
     */
    private HashSet<Cell> closedSet;

    /**
     * A list of cells to be visited in the maze generation process.
     */
    private ArrayList<Cell> openSet;

    /**
     * Initializes the maze generation using Prim's algorithm by setting up the initial state
     * of the algorithm with a randomly selected starting cell and its neighbors.
     *
     * @param graph The BoardGraph representing the maze.
     */
    @Override
    public void initializeMazeGeneration(BoardGraph graph) {
        super.initializeMazeGeneration(graph);
        isRunning = true;
        this.graph = graph;

        openSet = new ArrayList<>();
        closedSet = new HashSet<>();

        Cell[][] cells = graph.getMatrix();
        int randomX = (int)(Math.random() * cells.length);
        int randomY = (int)(Math.random() * cells.length);

        current = cells[randomY][randomX];
        closedSet.add(current);
        openSet.addAll(graph.getNeighbours(current));
    }

    /**
     * Performs a single step of the maze generation using Prim's algorithm.
     *
     * @param graph The BoardGraph representing the maze.
     */
    @Override
    public void stepMazeGeneration(BoardGraph graph) {
        if(openSet.isEmpty()) {
            finish();
            return;
        }
        current = popRandomCell(openSet);

        closedSet.add(current);

        ArrayList<Cell> visitedNeighbours = getVisitedNeighbours(current);

        if(!visitedNeighbours.isEmpty()){
            Cell randomCell = popRandomCell(visitedNeighbours);
            randomCell.removeWall(current);
            openSet.addAll(getUnvisitedNeighbours(current));
        }

        openSet.removeIf(cell1 -> closedSet.contains(cell1));
    }

    /**
     * Returns a list of neighbors of a given cell based on whether they are contained
     * in the closedSet or not.
     *
     * @param cell The cell for which to get the neighbors.
     * @param contains Whether to include neighbors that are contained in the closedSet or not.
     * @return A list of neighbors that satisfy the given condition.
     */
    private ArrayList<Cell> getSpecificNeighbours(Cell cell, boolean contains){
        Set<Cell> neighbours = graph.getNeighbours(cell);
        ArrayList<Cell> neighboursArr = neighbours.stream().filter(o -> closedSet.contains(o) == contains).collect(Collectors.toCollection(ArrayList::new));
        return neighboursArr;
    }

    /**
     * Returns a list of unvisited neighbors of a given cell.
     *
     * @param cell The cell for which to get the unvisited neighbors.
     * @return A list of unvisited neighbors.
     */
    private ArrayList<Cell> getUnvisitedNeighbours(Cell cell){
        return getSpecificNeighbours(cell, false);
    }

    /**
     * Returns a list of visited neighbors of a given cell.
     *
     * @param cell The cell for which to get the visited neighbors.
     * @return A list of visited neighbors.
     */
    private ArrayList<Cell> getVisitedNeighbours(Cell cell){
        return getSpecificNeighbours(cell, true);
    }

    /**
     * Removes and returns a random cell from the given list.
     *
     * @param arrayList The list from which to remove a random cell.
     * @return A random cell from the list.
     */
    private Cell popRandomCell(ArrayList<Cell> arrayList){
        int index = (int)(Math.random() * arrayList.size());
        return arrayList.remove(index);
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