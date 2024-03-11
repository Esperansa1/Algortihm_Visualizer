package Visualizer.Model.MazeAlgorithms;

import Visualizer.Cell;

public abstract class MazeAlgorithm {

    protected boolean isRunning;

    public boolean isRunning(){
        return isRunning;
    }
    public abstract void stepMazeGeneration(Cell[][] cells);

    public void initializeMazeGeneration(Cell[][] cells){

        for(Cell[] cellArray : cells) {
            for (Cell cell : cellArray) {
                cell.setWalls(new boolean[]{true, true, true, true});
                Cell.CellType type = cell.getCellType();
                if(type != Cell.CellType.START_POINT && type != Cell.CellType.END_POINT){
                    cell.setCellType(Cell.CellType.EMPTY);
                }
            }
        }
        for(Cell[] cellArray : cells) {
            for (Cell cell : cellArray) {
                cell.setupNeighbours(cells);
            }
        }
    }




}
