package Visualizer.Model;

import Visualizer.*;
import Visualizer.Model.MazeAlgorithms.MazeAlgorithm;
import Visualizer.Model.MazeAlgorithms.Prims;
import Visualizer.Model.MazeAlgorithms.SAW;

import java.util.ArrayList;
import java.util.List;

public class MazeManager extends Observable<BoardObserver> {

    /**
     * A list of available maze generation algorithms.
     */
    private final ArrayList<MazeAlgorithm> mazeAlgorithm;

    /**
     * The index of the currently selected maze generation algorithm.
     */
    private int currentAlgorithmIndex;

    /**
     * Constructs a new MazeManager instance and initializes the available maze generation algorithms.
     */
    public MazeManager() {
        mazeAlgorithm = new ArrayList<>();

        mazeAlgorithm.add(new SAW());
        mazeAlgorithm.add(new Prims());
    }

    /**
     * Checks if the current maze generation algorithm is running.
     *
     * @return true if the algorithm is running, false otherwise.
     */
    public boolean isRunning() {
        return getCurrentAlgorithm().isRunning();
    }

    /**
     * Returns the currently selected maze generation algorithm.
     *
     * @return The currently selected maze generation algorithm.
     */
    private MazeAlgorithm getCurrentAlgorithm() {
        return mazeAlgorithm.get(currentAlgorithmIndex);
    }

    /**
     * Performs a single step of the current maze generation algorithm on the given graph.
     *
     * @param graph The BoardGraph representing the maze.
     */
    public void stepMazeGeneration(BoardGraph graph) {
        getCurrentAlgorithm().stepMazeGeneration(graph);
        notifyObservers();
    }

    /**
     * Initializes the current maze generation algorithm with the given graph.
     *
     * @param graph The BoardGraph representing the maze.
     */
    public void initializeMazeGeneration(BoardGraph graph) {
        getCurrentAlgorithm().initializeMazeGeneration(graph);
    }

    /**
     * Returns the name of the currently selected maze generation algorithm.
     *
     * @return The name of the currently selected maze generation algorithm.
     */
    public String getCurrentAlgorithmName() {
        return getCurrentAlgorithm().toString();
    }

    /**
     * Notifies all observers of changes in the board and highlights the current cell being processed by the maze generation algorithm.
     */
    @Override
    public void notifyObservers() {
        for (BoardObserver observer : getObservers()) {
            observer.onBoardChanged();
        }
        MazeAlgorithm currentAlgorithm = getCurrentAlgorithm();
        for (HighlightObserver observer : observers) {
            observer.onMazeHighlight(currentAlgorithm.getCurrent());
        }
    }

    /**
     * Returns an array of the names of the available maze generation algorithms.
     *
     * @return An array of the names of the available maze generation algorithms.
     */
    public String[] getAvailablePathfindingAlgorithms() {
        String[] names = new String[mazeAlgorithm.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = mazeAlgorithm.get(i).toString();
        }
        return names;
    }

    /**
     * Chooses the maze generation algorithm by its name.
     *
     * @param selected The name of the maze generation algorithm to be selected.
     */
    public void chooseAlgorithmByIndex(String selected) {
        currentAlgorithmIndex = mazeAlgorithm.indexOf(mazeAlgorithm.stream()
                .filter(algorithm -> algorithm.toString().equals(selected))
                .findFirst()
                .orElse(null));
    }

    /**
     * Returns the index of the currently selected maze generation algorithm.
     *
     * @return The index of the currently selected maze generation algorithm.
     */
    public int getCurrentAlgorithmIndex() {
        return currentAlgorithmIndex;
    }

    // Maze Observers
    /**
     * A list of HighlightObservers to be notified when a maze cell is highlighted during the generation process.
     */
    private final List<HighlightObserver> observers = new ArrayList<>();

    /**
     * Adds a HighlightObserver to the list of observers.
     *
     * @param observer The HighlightObserver to be added.
     */
    public void addObserver(HighlightObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes a HighlightObserver from the list of observers.
     *
     * @param observer The HighlightObserver to be removed.
     */
    public void removeObserver(HighlightObserver observer) {
        observers.remove(observer);
    }
}