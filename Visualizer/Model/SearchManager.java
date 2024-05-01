package Visualizer.Model;

import Visualizer.BoardGraph;
import Visualizer.BoardObserver;
import Visualizer.Model.SearchAlgortihms.AStar.AStar;
import Visualizer.Model.SearchAlgortihms.GreedyBFS;
import Visualizer.Model.SearchAlgortihms.Pledge;
import Visualizer.Model.SearchAlgortihms.SearchAlgorithm;
import Visualizer.Model.SearchAlgortihms.Tremaux;
import Visualizer.Observable;

import java.util.ArrayList;

/**
 * The SearchManager class manages and coordinates various pathfinding algorithms
 * within the Visualizer application. It acts as an Observable, notifying registered
 * BoardObservers of changes to the search state.
 */
public class SearchManager extends Observable<BoardObserver> {

    /**
     * A list of supported search algorithms used by the SearchManager.
     */
    private final ArrayList<SearchAlgorithm> searchAlgorithm;

    /**
     *  The index of the currently active search algorithm in the searchAlgorithm list.
     */
    private int currentAlgorithmIndex;

    /**
     * Constructor for the SearchManager class. Initializes the list of available
     * search algorithms.
     */
    public SearchManager() {
        searchAlgorithm = new ArrayList<>();

        searchAlgorithm.add(new AStar());
        searchAlgorithm.add(new Tremaux());
        searchAlgorithm.add(new Pledge());
        searchAlgorithm.add(new GreedyBFS());
    }

    /**
     * Checks if a search algorithm is currently running.
     * @return True if the current algorithm is running, false otherwise.
     */
    public boolean isRunning(){
        return getCurrentAlgorithm().isRunning;
    }

    /**
     * Gets the currently active search algorithm.
     * @return The SearchAlgorithm object representing the current algorithm.
     */
    private SearchAlgorithm getCurrentAlgorithm() {
        return searchAlgorithm.get(currentAlgorithmIndex);
    }

    /**
     * Gets the name of the currently active search algorithm.
     * @return The name of the current algorithm as a String.
     */
    public String getCurrentAlgorithmName(){
        return getCurrentAlgorithm().toString();
    }

    /**
     * Executes a single step of the current search algorithm.
     */
    public void stepSearch(){
        getCurrentAlgorithm().stepSearch();
        notifyObservers();
    }

    /**
     * Initializes the current search algorithm using the provided BoardGraph.
     * @param graph The BoardGraph representing the search space.
     */
    public void initializeSearch(BoardGraph graph){
        getCurrentAlgorithm().initializeSearch(graph);
    }

    /**
     * Updates BoardObservers when the board state changes during the search process.
     */
    @Override
    public void notifyObservers() {
        for(BoardObserver observer : getObservers()){
            observer.onBoardChanged();
        }
    }

    /**
     * Returns an array of available pathfinding algorithm names as Strings.
     * @return An array of algorithm names.
     */
    public String[] getAvailablePathfindingAlgorithms() {
        String[] names = new String[searchAlgorithm.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = searchAlgorithm.get(i).toString();
        }
        return names;
    }

    /**
     * Selects a search algorithm based on a provided name and resets its state.
     * @param selected The name of the search algorithm to select.
     */
    public void chooseAlgorithmByIndex(String selected) {
        getCurrentAlgorithm().resetAlgorithm(); // Reset the previous algorithm

        currentAlgorithmIndex = searchAlgorithm.indexOf(searchAlgorithm.stream()
                .filter(algorithm -> algorithm.toString().equals(selected))
                .findFirst()
                .orElse(null));

        getCurrentAlgorithm().resetAlgorithm(); // Initialize the newly selected one
    }

    /**
     * Gets the index of the currently active search algorithm.
     * @return The index of the current algorithm in the searchAlgorithm list.
     */
    public int getCurrentAlgorithmIndex() {
        return currentAlgorithmIndex;
    }
}
