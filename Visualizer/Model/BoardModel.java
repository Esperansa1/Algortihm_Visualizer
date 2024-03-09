package Visualizer.Model;


import Visualizer.*;
import Visualizer.View.Screen;

public class BoardModel extends Observable {

    private Graph graph;
    private Cell startCell, endCell;

    public BoardModel() {
        int rows = Screen.GRID_WIDTH / Cell.CELL_SIZE;
        int cols = Screen.GRID_HEIGHT / Cell.CELL_SIZE;

        updateGraphMatrix(rows, cols);
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public Cell[][] getCells() {
        return graph.getMatrix();
    }

    public Cell getCell(int row, int col){
        return getCells()[row][col];
    }

    public void updateGraphMatrix(int rows, int cols) {
        this.graph = new Graph(rows, cols);
        startCell = null;
        endCell = null;
        notifyObservers();

    }

    public void onCellSelected(int row, int col) {
        Cell cell = graph.getCell(row, col);
        if (cell.getCellType() == Cell.CellType.START_POINT || cell.getCellType() == Cell.CellType.END_POINT)
            return;

        if (startCell == null) {
            startCell = cell;
            startCell.setCellType(Cell.CellType.START_POINT);
        } else if (endCell == null) {
            endCell = cell;
            endCell.setCellType(Cell.CellType.END_POINT);
        }
        notifyObservers();
    }

    public void addWall(Cell cell1, Cell cell2){
       cell1.addWall(cell2);
       notifyObservers();
    }
    public void removeWall(Cell cell1, Cell cell2){
        cell1.removeWall(cell2);
        notifyObservers();
    }

    public void onCellDeselect(int row, int col) {
        Cell selected = graph.getCell(row, col);
        selected.setCellType(Cell.CellType.EMPTY);
        if (selected.equals(startCell))
            startCell = null;
        else if (selected.equals(endCell))
            endCell = null;

        notifyObservers();
    }

}