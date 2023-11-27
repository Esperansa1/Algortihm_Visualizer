package Visualizer.SearchAlgortihms;

import Visualizer.Cell;

import java.util.ArrayList;

public abstract class SearchAlgorithm  {

    public ArrayList<Cell> open_set;
    public ArrayList<Cell> closed_set;

    public boolean isInClosedSet(Cell target){
        for(Cell cell : closed_set){
            if(cell.equals(target)) return true;
        }
        return false;
    }

    public abstract void stepSearch(Cell[][] cells);


}
