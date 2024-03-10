package Visualizer.Model.MazeAlgorithms;

import Visualizer.Cell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Prims extends MazeAlgorithm {


    private HashSet<Cell> closedSet;
    private ArrayList<Cell> openSet;
    private ArrayList<Cell> uncheckedNeighbours;
    private Cell current, previous;
    private boolean isRunning;

    @Override
    public void initializeMazeGeneration(Cell[][] cells) {
        super.initializeMazeGeneration(cells);
        isRunning = true;

        uncheckedNeighbours = new ArrayList<>();
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
            if(!(previous == null || current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT))
                previous.setCellType(Cell.CellType.EMPTY);
            isRunning = false;
            return;
        }
        current = popRandomCell(openSet);
        closedSet.add(current);

        ArrayList<Cell> visitedNeighbours = getUnvisitedNeighbours(current);

        if(!visitedNeighbours.isEmpty()){
            Cell randomCell = popRandomCell(visitedNeighbours);
            if(!closedSet.contains(randomCell))
                randomCell.removeWall(current);
            else
                openSet.add(current);
        }
        openSet.addAll(getUnvisitedNeighbours(current));

    }

    private ArrayList<Cell> getUnvisitedNeighbours(Cell cell){
        ArrayList<Cell> neighbours = cell.getNeighbours();
        neighbours = neighbours.stream().filter(o -> !closedSet.contains(o)).collect(Collectors.toCollection(ArrayList::new));



        return neighbours;
    }

    private ArrayList<Cell> getVisitedNeighbours(Cell cell){
        ArrayList<Cell> neighbours = cell.getNeighbours();
        neighbours = neighbours.stream().filter(o -> closedSet.contains(o)).collect(Collectors.toCollection(ArrayList::new));
        return neighbours;
    }

//    private void highlight(Cell current){
//
//        if(previous == null || current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT) return;
//
//        previous.setCellType(Cell.CellType.EMPTY);
//        current.setCellType(Cell.CellType.HIGHLIGHT);
//        previous = current;
//    }

    private Cell popRandomCell(ArrayList<Cell> arrayList){
        int index = (int)(Math.random() * arrayList.size());
        return arrayList.remove(index);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public String toString() {
        return "Prim's Algorithm";
    }
}
