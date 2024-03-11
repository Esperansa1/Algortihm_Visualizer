package Visualizer.Model.MazeAlgorithms;

import Visualizer.Cell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Prims extends MazeAlgorithm {

    public static final String NAME = "Prim's Algorithm";

    private HashSet<Cell> closedSet;
    private ArrayList<Cell> openSet;
    private Cell current;
    private boolean isRunning;

    @Override
    public void initializeMazeGeneration(Cell[][] cells) {
        super.initializeMazeGeneration(cells);
        isRunning = true;

        openSet = new ArrayList<>();
        closedSet = new HashSet<>();

        int randomX = (int)(Math.random() * cells.length);
        int randomY = (int)(Math.random() * cells.length);

        current = cells[randomY][randomX];
        closedSet.add(current);
        openSet.addAll(current.getNeighbours());

    }

    @Override
    public void stepMazeGeneration(Cell[][] cells) {

        if(openSet.isEmpty()) {
            isRunning = false;
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
        ArrayList<Cell> neighbours = cell.getNeighbours();
        neighbours = neighbours.stream().filter(o -> closedSet.contains(o) == contains).collect(Collectors.toCollection(ArrayList::new));
        return neighbours;
    }

    private ArrayList<Cell> getUnvisitedNeighbours(Cell cell){
        return getSpecificNeighbours(cell, false);
    }

    private ArrayList<Cell> getVisitedNeighbours(Cell cell){
        return getSpecificNeighbours(cell, true);
    }

    private Cell popRandomCell(ArrayList<Cell> arrayList){
        int index = (int)(Math.random() * arrayList.size());
        System.out.println(arrayList.size() + " " + index);
        return arrayList.remove(index);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
