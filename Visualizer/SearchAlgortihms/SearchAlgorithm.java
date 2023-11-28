package Visualizer.SearchAlgortihms;

import Visualizer.Cell;
import Visualizer.SearchAlgortihms.AStar.AStarCell;

import java.util.ArrayList;

public abstract class SearchAlgorithm<T>  {


    public boolean isFinished = false;

    protected ArrayList<T> openSet;
    protected ArrayList<T> closedSet;

    public boolean isInClosedSet(T target){
        for(T cell : closedSet){
            if(cell.equals(target)) return true;
        }
        return false;
    }

    public void initializeSearch(Cell startCell, Cell endCell, Cell[][] cells) {

    }

    public abstract void stepSearch(ArrayList<T> cells);


    public ArrayList<T> getOpenSet() {
        return openSet;
    }

    public ArrayList<T> getClosedSet() {
        return closedSet;
    }
}
