package Visualizer.Model.MazeAlgorithms;

import Visualizer.BoardGraph;
import Visualizer.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Prims extends MazeAlgorithm {

    public static final String NAME = "Prim's Algorithm";

    private BoardGraph graph;
    private HashSet<Cell> closedSet;
    private ArrayList<Cell> openSet;

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

    private ArrayList<Cell> getSpecificNeighbours(Cell cell, boolean contains){
        Set<Cell> neighbours = graph.getNeighbours(cell);
        ArrayList<Cell> neighboursArr = neighbours.stream().filter(o -> closedSet.contains(o) == contains).collect(Collectors.toCollection(ArrayList::new));
        return neighboursArr;
    }

    private ArrayList<Cell> getUnvisitedNeighbours(Cell cell){
        return getSpecificNeighbours(cell, false);
    }

    private ArrayList<Cell> getVisitedNeighbours(Cell cell){
        return getSpecificNeighbours(cell, true);
    }

    private Cell popRandomCell(ArrayList<Cell> arrayList){
        int index = (int)(Math.random() * arrayList.size());
        return arrayList.remove(index);
    }

    @Override
    public String toString() {
        return NAME;
    }
}
