package Visualizer;
import Visualizer.Model.BoardModel;
import Visualizer.Model.MazeManager;
import Visualizer.Model.SearchManager;
import Visualizer.View.Screen;

import javax.swing.*;

public class Controller {
    private static BoardModel model = null; // Represents the layout of the maze
    private final MazeManager mazeManager;  // Handles the process of generating mazes
    private final SearchManager searchManager; // Manages the execution of pathfinding algorithms
    private final Screen view; // Responsible for the visual interface
    private boolean isBusy; // Tracks whether the application is currently generating a maze or finding a path
    private Cell previousSelectedCell, previousDeselectedCell; // Support for wall placement

    public Controller() {
        model = new BoardModel();
        mazeManager = new MazeManager();
        searchManager = new SearchManager();
        view = new Screen(this);
    }

    // Returns the main BoardModel instance
    public BoardModel getModel() {
        return model;
    }

    // Handles user selection of a cell on the grid
    public void onCellSelected(int row, int col) {
        BoardGraph graph = model.getBoardGraph();

        Cell current = graph.getCell(row, col);
        if(isBusy || current.equals(previousDeselectedCell)) return;

        // Logic to assign start/end point or add a wall based on the current state
        if(model.getStartCell() == null || model.getEndCell() == null)
            model.onCellSelected(row, col);
        else if(previousSelectedCell == null) {
            previousSelectedCell = graph.getCell(row, col);
        }else{
            model.addWall(previousSelectedCell, current);
            previousSelectedCell = null;
        }
    }


    /**
     * Handles the logic when a cell is deselected by the user.
     * - Prevents actions if the system is busy or the same cell is deselected twice.
     * - Handles special cases for START_POINT and END_POINT cells.
     * - Manages wall removal between previously and currently deselected cells.
     *
     * @param row  The row index of the deselected cell
     * @param col  The column index of the deselected cell
     */
    public void onCellDeselect(int row, int col) {
        BoardGraph graph = model.getBoardGraph();
        Cell current = graph.getCell(row, col);

        if (isBusy || current.equals(previousDeselectedCell)) return;

        if (current.getCellType() == Cell.CellType.START_POINT || current.getCellType() == Cell.CellType.END_POINT) {
            // Delegate special handling of start/end points to the model
            model.onCellDeselect(row, col);
        } else if (previousDeselectedCell == null) {
            previousDeselectedCell = graph.getCell(row, col);
        } else {
            model.removeWall(previousDeselectedCell, current);
            previousDeselectedCell = null;
        }
    }

    /**
     * Controls pathfinding visualization.
     * - If 'visualize' is true, performs step-by-step visualization on a separate thread.
     * - Otherwise, performs immediate path calculation.
     *
     * @param visualize  Boolean flag to enable step-by-step visualization
     */
    public void visualizeSearch(boolean visualize) {
        if (visualize) {
            new Thread(() -> {
                if (model.getStartCell() == null || model.getEndCell() == null || isBusy) return;

                BoardGraph graph = model.getBoardGraph();
                searchManager.initializeSearch(graph); // Prepare the search

                isBusy = true; // Prevent other actions while search is running
                while (searchManager.isRunning()) {
                    try {
                        searchManager.stepSearch(); // Perform one step of the search algorithm
                        Thread.sleep(1); // Pause for 1 millisecond for visualization
                    } catch (InterruptedException exception) {
                        break; // Handle interruptions if needed
                    }
                }
                view.drawPath(model.getStartCell(), model.getEndCell()); // Display the found path

            }).start();
        } else {
            BoardGraph graph = model.getBoardGraph();
            searchManager.initializeSearch(graph);

            isBusy = true;
            while (searchManager.isRunning()) {
                searchManager.stepSearch(); // Run the search algorithm to completion
            }
            view.drawPath(model.getStartCell(), model.getEndCell());
        }
    }

    /**
     * Initiates maze generation on a separate thread.
     * - Checks if the system is already busy and returns if so.
     * - Manages the maze generation process until completion.
     */
    public void visualizeMaze() {
        new Thread(() -> {
            if (isBusy) return;
            isBusy = true;

            mazeManager.initializeMazeGeneration(model.getBoardGraph()); // Prepare maze generation

            while (mazeManager.isRunning()) {
                try {
                    mazeManager.stepMazeGeneration(model.getBoardGraph()); // Perform one step of maze generation
                    Thread.sleep(2); // Pause for 2 milliseconds for visualization
                } catch (InterruptedException exception) {
                    throw new RuntimeException();
                }
            }

            isBusy = false; // Maze generation complete
        }).start();
    }


    public static BoardModel getInstance(){
        return model;
    }

    public void subscribeToBoardModel(BoardObserver observer){
        model.addObserver(observer);
        mazeManager.addObserver(observer);
        searchManager.addObserver(observer);
    }

    public void subscribeToHighlightModel(HighlightObserver observer){
        mazeManager.addObserver(observer);
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
        SwingUtilities.invokeLater(Controller::new);
    }

    public String[] getAvailablePathfindingAlgorithms() {
        return searchManager.getAvailablePathfindingAlgorithms();
    }

    public void sendSelectedPathfindingAlgorithm(String selected) {
        searchManager.chooseAlgorithmByIndex(selected);
    }

    public String[] getAvailableMazeAlgorithms() {
        return mazeManager.getAvailablePathfindingAlgorithms();
    }

    public void sendSelectedMazeAlgorithm(String selectedItem) {
        mazeManager.chooseAlgorithmByIndex(selectedItem);
    }

    public int getCurrentSearchAlgorithmIndex() {
        return searchManager.getCurrentAlgorithmIndex();
    }

    public int getCurrentMazeAlgorithmIndex() {
        return mazeManager.getCurrentAlgorithmIndex();
    }
}
