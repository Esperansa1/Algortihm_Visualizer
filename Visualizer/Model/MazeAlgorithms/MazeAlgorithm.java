package Visualizer.Model.MazeAlgorithms;

import Visualizer.BoardGraph;
import Visualizer.Cell;
import Visualizer.HighlightObserver;
import Visualizer.Observable;

public abstract class MazeAlgorithm extends Observable<HighlightObserver> {

    /**
     * Indicates whether the maze generation algorithm is currently running or not.
     */
    protected boolean isRunning;

    /**
     * The current cell being processed by the maze generation algorithm.
     */
    protected Cell current;

    /**
     * Performs a single step of the maze generation algorithm.
     *
     * @param graph The BoardGraph representing the maze.
     */
    public abstract void stepMazeGeneration(BoardGraph graph);

    /**
     * Initializes the maze generation by setting all cells to have walls on all sides,
     * and setting the cell type to EMPTY for non-start/end cells.
     *
     * @param graph The BoardGraph representing the maze.
     */
    public void initializeMazeGeneration(BoardGraph graph){
        Cell[][] cells = graph.getMatrix();
        for(Cell[] cellArray : cells) {
            for (Cell cell : cellArray) {
                cell.setWalls(new boolean[]{true, true, true, true});
                Cell.CellType type = cell.getCellType();
                if(type != Cell.CellType.START_POINT && type != Cell.CellType.END_POINT){
                    cell.setCellType(Cell.CellType.EMPTY);
                }
            }
        }
    }

    /**
     * Finishes the maze generation algorithm by setting isRunning to false
     * and current to null.
     */
    protected void finish(){
        isRunning = false;
        current = null;
    }

    /**
     * Returns the current cell being processed by the maze generation algorithm.
     *
     * @return The current cell.
     */
    public Cell getCurrent() {
        return current;
    }

    /**
     * Returns whether the maze generation algorithm is currently running or not.
     *
     * @return true if the algorithm is running, false otherwise.
     */
    public boolean isRunning(){
        return isRunning;
    }
}