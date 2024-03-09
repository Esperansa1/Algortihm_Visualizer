package Visualizer;
import Visualizer.Model.BoardModel;
import Visualizer.Model.MazeManager;
import Visualizer.Model.SearchManager;
import Visualizer.View.Screen;

import javax.swing.*;

public class Controller {
    private static BoardModel model = null;
    private final MazeManager mazeManager;
    private final SearchManager searchManager;
    private Screen view;

    private boolean isBusy;

    private Cell previousSelectedCell, previousDeselectedCell;


    public Controller() {
        model = new BoardModel();
        mazeManager = new MazeManager();
        searchManager = new SearchManager();
        view = new Screen(this);
    }

    public BoardModel getModel() {
        return model;
    }

    public void onCellSelected(int row, int col) {
        if(isBusy) return;

        if(model.getStartCell() == null || model.getEndCell() == null)
            model.onCellSelected(row, col);
        else{
            if(previousSelectedCell == null) {
                previousSelectedCell = model.getCell(row, col);
            }else{
                Cell current = model.getCell(row, col);
                model.addWall(previousSelectedCell, current);
                previousSelectedCell = null;
            }

        }
    }

    public void onCellDeselect(int row, int col) {
        if(isBusy) return;
        Cell current = model.getCell(row, col);
        if(current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT)
            model.onCellDeselect(row, col);
        if(previousDeselectedCell == null)
            previousDeselectedCell = model.getCell(row, col);
        else{
            model.removeWall(previousDeselectedCell, current);
            previousDeselectedCell = null;
        }
    }

    public void visualizeSearch() {
        new Thread(() -> {
            if (model.getStartCell() == null || model.getEndCell() == null) return;

            Cell[][] cells = model.getCells();
            searchManager.initializeSearch(cells);

            isBusy = true;
            while (searchManager.isRunning()) {
                try {
                    searchManager.stepSearch();
                    Thread.sleep(1);
                } catch (InterruptedException exception) {
                    break;
                }
            }
            view.drawPath(model.getStartCell(), model.getEndCell());

//            isBusy = false;
        }).start();
    }

    public void visualizeMaze() {
        new Thread(() -> {
            if (isBusy) return;
            isBusy = true;

            Cell[][] cells = model.getCells();
            mazeManager.initializeMazeGeneration(cells);

            while (mazeManager.isRunning()) {
                mazeManager.stepMazeGeneration(cells);
            }
            isBusy = false;
        }).start();
    }


    public String getCurrentPathfindingAlgorithmName() {
        return searchManager.getCurrentAlgorithmName();
    }

    public String getCurrentMazeAlgorithmName() {
        return mazeManager.getCurrentAlgorithmName();
    }

    public void nextPathfindingAlgorithm() {
        searchManager.nextAlgorithm();
    }

    public void nextMazeAlgorithm() {
        mazeManager.nextAlgorithm();
    }

    public static BoardModel getInstance(){
        return model;
    }

    public void subscribeToModel(BoardObserver observer){
        model.addObserver(observer);
        mazeManager.addObserver(observer);
        searchManager.addObserver(observer);
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void updateCellsArray(int value) {

        int rows = Screen.GRID_HEIGHT / value;
        int cols = Screen.GRID_WIDTH / value;

        Cell[][] cells = model.getCells();
        if(cells.length == rows && cells[0].length == cols) return;

        Cell.CELL_SIZE = value;
        model.updateGraphMatrix(rows, cols);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
        });
    }
}
