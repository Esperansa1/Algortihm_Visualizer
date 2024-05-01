package Visualizer.Model.MazeAlgorithms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.*;

public class SAW extends MazeAlgorithm {

    public static final String NAME = "Self Avoiding Walk";

    /**
     * The BoardGraph representing the maze.
     */
    private BoardGraph graph;

    /**
     * A set of visited cells in the maze generation process.
     */
    private HashSet<Cell> closedSet;

    /**
     * A stack of cells used to backtrack during the maze generation process.
     */
    private Stack<Cell> stack;

    /**
     * Initializes the maze generation using the Self Avoiding Walk algorithm by setting up
     * the initial state of the algorithm with a randomly selected starting cell.
     *
     * @param graph The BoardGraph representing the maze.
     */
    @Override
    public void initializeMazeGeneration(BoardGraph graph) {
        super.initializeMazeGeneration(graph);
        isRunning = true;
        this.graph = graph;

        closedSet = new HashSet<>();
        stack = new Stack<>();

        Cell[][] cells = graph.getMatrix();
        int randomY = (int)(Math.random() * cells.length);
        int randomX = (int)(Math.random() * cells.length);

        current = cells[randomY][randomX];

        closedSet.add(current);
    }

    /**
     * Performs a single step of the maze generation using the Self Avoiding Walk algorithm.
     *
     * @param graph The BoardGraph representing the maze.
     */
    @Override
    public void stepMazeGeneration(BoardGraph graph) {
        Cell[][] cells = graph.getMatrix();
        if(closedSet.size() == cells.length * cells[0].length){
            finish();
            return;
        }
        Cell next = getRandomNeighbour(current);

        if(next != null){
            closedSet.add(next);
            stack.push(current);
            current.removeWall(next);
            current = next;

        }else if(!stack.isEmpty()){
            current = stack.pop();
        }
    }

    /**
     * Returns a random unvisited neighbor of the given cell, or null if there are no unvisited neighbors.
     *
     * @param current The cell for which to find a random unvisited neighbor.
     * @return A random unvisited neighbor of the given cell, or null if there are no unvisited neighbors.
     */
    public Cell getRandomNeighbour(Cell current) {

        Set<Cell> neighbours = graph.getNeighbours(current);
        List<Cell> filteredNeighbours = neighbours.stream().filter(cell -> !closedSet.contains(cell)).toList();

        if(filteredNeighbours.isEmpty())
            return null;
        int randomIndex = (int)(Math.random() * filteredNeighbours.size());
        return filteredNeighbours.get(randomIndex);
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