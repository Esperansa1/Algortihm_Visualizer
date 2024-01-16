package Visualizer.MazeAlgorithms;

import Visualizer.Cell;

public abstract class MazeAlgorithm {

    public abstract boolean isRunning();
    public abstract void stepMazeGeneration(Cell[][] cells);

    public void initializeMazeGeneration(Cell[][] cells){

        for(Cell[] cellArray : cells) {
            for (Cell cell : cellArray) {
                cell.setWalls(new boolean[]{true, true, true, true});
            }
        }


        for(Cell[] cellArray : cells) {
            for (Cell cell : cellArray) {
                cell.setupNeighbours(cells, false);
            }
        }

    }


}
