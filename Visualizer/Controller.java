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
    private final Screen view;
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
        BoardGraph graph = model.getBoardGraph();

        Cell current = graph.getCell(row, col);
        if(isBusy || current.equals(previousDeselectedCell)) return;

        if(model.getStartCell() == null || model.getEndCell() == null)
            model.onCellSelected(row, col);
        else if(previousSelectedCell == null) {
            previousSelectedCell = graph.getCell(row, col);
        }else{
            model.addWall(previousSelectedCell, current);
            previousSelectedCell = null;
        }
    }

    public void onCellDeselect(int row, int col) {
        BoardGraph graph = model.getBoardGraph();
        Cell current = graph.getCell(row, col);

        if(isBusy || current.equals(previousDeselectedCell)) return;

        if(current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT)
            model.onCellDeselect(row, col);
        else if(previousDeselectedCell == null)
            previousDeselectedCell = graph.getCell(row, col);
        else{
            model.removeWall(previousDeselectedCell, current);
            previousDeselectedCell = null;
        }
    }

    public void visualizeSearch() {
        new Thread(() -> {
            if (model.getStartCell() == null || model.getEndCell() == null || isBusy) return;

            BoardGraph graph = model.getBoardGraph();
            searchManager.initializeSearch(graph);

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

        }).start();
    }

    public void visualizeMaze() {
        new Thread(() -> {
            if (isBusy) return;
            isBusy = true;


            mazeManager.initializeMazeGeneration(model.getBoardGraph());

            while (mazeManager.isRunning()) {
                try {
                    mazeManager.stepMazeGeneration(model.getBoardGraph());
                    Thread.sleep(2);
                } catch (InterruptedException exception) {
                    throw new RuntimeException();
                }
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

    public void subscribeToModel(BoardObserver observer1, HighlightObserver observer2){
        model.addObserver(observer1);
        mazeManager.addObserver(observer1);
        searchManager.addObserver(observer1);

        mazeManager.addObserver(observer2);

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

        Cell.setCellSize(value);
        model.updateGraphMatrix(rows, cols);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
        });
    }
}
