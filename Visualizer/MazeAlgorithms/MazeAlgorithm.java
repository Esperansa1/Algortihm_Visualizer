package Visualizer.MazeAlgorithms;

import Visualizer.Cell;

public abstract class MazeAlgorithm {

    public abstract boolean isFinished();
    public abstract void stepMazeGeneration(Cell[][] cells);



}
