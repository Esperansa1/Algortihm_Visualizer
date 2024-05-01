package Visualizer.Model;

import Visualizer.*;
import Visualizer.View.Screen;

public class BoardModel extends Observable<BoardObserver> {

    /**
     * An enum representing the next action to be performed (placing start, end, or wall).
     */
    public enum NextPlace {START, END, WALL}

    /**
     * The graph representing the board.
     */
    private BoardGraph boardGraph;

    /**
     * The cell representing the start point.
     */
    private Cell startCell;

    /**
     * The cell representing the end point.
     */
    private Cell endCell;

    /**
     * Constructs a new BoardModel with an initial matrix size based on the screen dimensions.
     */
    public BoardModel() {
        int rows = Screen.GRID_WIDTH / Cell.CELL_SIZE;
        int cols = Screen.GRID_HEIGHT / Cell.CELL_SIZE;

        updateGraphMatrix(rows, cols);
    }

    /**
     * Returns the start cell.
     *
     * @return The start cell.
     */
    public Cell getStartCell() {
        return startCell;
    }

    /**
     * Returns the end cell.
     *
     * @return The end cell.
     */
    public Cell getEndCell() {
        return endCell;
    }

    /**
     * Returns the board graph.
     *
     * @return The board graph.
     */
    public BoardGraph getBoardGraph() {
        return boardGraph;
    }

    /**
     * Returns the matrix of cells in the board graph.
     *
     * @return The matrix of cells.
     */
    public Cell[][] getCells() {
        return boardGraph.getMatrix();
    }

    /**
     * Updates the board graph with a new matrix of the specified dimensions.
     *
     * @param rows The number of rows in the new matrix.
     * @param cols The number of columns in the new matrix.
     */
    public void updateGraphMatrix(int rows, int cols) {
        this.boardGraph = new BoardGraph(rows, cols);
        startCell = null;
        endCell = null;
        notifyObservers();
    }

    /**
     * Handles the selection of a cell on the board.
     *
     * @param row The row index of the selected cell.
     * @param col The column index of the selected cell.
     */
    public void onCellSelected(int row, int col) {
        Cell cell = boardGraph.getCell(row, col);
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

    /**
     * Adds a wall between two adjacent cells.
     *
     * @param cell1 The first cell.
     * @param cell2 The second cell.
     */
    public void addWall(Cell cell1, Cell cell2) {
        cell1.addWall(cell2);
        notifyObservers();
    }

    /**
     * Removes a wall between two adjacent cells.
     *
     * @param cell1 The first cell.
     * @param cell2 The second cell.
     */
    public void removeWall(Cell cell1, Cell cell2) {
        cell1.removeWall(cell2);
        notifyObservers();
    }

    /**
     * Handles the deselection of a cell on the board.
     *
     * @param row The row index of the deselected cell.
     * @param col The column index of the deselected cell.
     */
    public void onCellDeselect(int row, int col) {
        Cell selected = boardGraph.getCell(row, col);
        selected.setCellType(Cell.CellType.EMPTY);
        if (selected.equals(startCell))
            startCell = null;
        else if (selected.equals(endCell))
            endCell = null;

        notifyObservers();
    }

    /**
     * Returns the next place (start, end, or wall) to be placed on the board.
     *
     * @return The next place to be placed.
     */
    public NextPlace getNextPlace() {
        if (this.startCell == null) return NextPlace.START;
        if (this.endCell == null) return NextPlace.END;
        return NextPlace.WALL;
    }

    /**
     * Notifies all observers of changes in the board.
     */
    @Override
    public void notifyObservers() {
        for (BoardObserver observer : getObservers()) {
            observer.onBoardChanged();
        }
    }
}