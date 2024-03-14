package Visualizer.Model.MazeAlgorithms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.*;

public class SAW extends MazeAlgorithm {

    public static final String NAME = "Self Avoiding Walk";

    private BoardGraph graph;
    private HashSet<Cell> closedSet;
    private Stack<Cell> stack;

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

    public Cell getRandomNeighbour(Cell current) {

        Set<Cell> neighbours = graph.getNeighbours(current);
        List<Cell> filteredNeighbours = neighbours.stream().filter(cell -> !closedSet.contains(cell)).toList();

        if(filteredNeighbours.isEmpty())
            return null;
        int randomIndex = (int)(Math.random() * filteredNeighbours.size());
        return filteredNeighbours.get(randomIndex);
    }

    @Override
    public String toString() {
        return NAME;
    }

}
