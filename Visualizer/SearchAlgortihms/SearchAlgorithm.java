package Visualizer.SearchAlgortihms;

import Visualizer.Cell;
import Visualizer.SearchAlgortihms.AStar.AStarCell;

import java.util.ArrayList;

public abstract class SearchAlgorithm<T>  {


    public boolean isFinished = false;

    protected ArrayList<T> openSet;
    protected ArrayList<T> closedSet;


    public void reconstructPath(Cell endCell, Cell startCell ){
        Cell current = endCell;
        while(!current.equals(startCell)){
            current = current.getCameFrom();
            current.setCellType(Cell.CellType.PATH);
        }
        startCell.setCellType(Cell.CellType.START_POINT);

    }

    public boolean isGoal(Cell endCell, Cell startCell, Cell current){
        if(current.equals(endCell)){
            reconstructPath(endCell, startCell);
            return true;
        }
        return false;
    }


    public void initializeSearch(Cell startCell, Cell endCell, Cell[][] cells) {

    }

    public abstract void stepSearch();


    public ArrayList<T> getOpenSet() {
        return openSet;
    }

    public ArrayList<T> getClosedSet() {
        return closedSet;
    }
}
