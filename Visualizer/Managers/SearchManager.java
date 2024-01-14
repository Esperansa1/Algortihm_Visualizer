package Visualizer.Managers;

import Visualizer.Cell;
import Visualizer.SearchAlgortihms.AStar.AStar;
import Visualizer.SearchAlgortihms.GreedyBFS;
import Visualizer.SearchAlgortihms.SearchAlgorithm;

import java.util.ArrayList;

public class SearchManager {
    private final ArrayList<SearchAlgorithm> searchAlgorithm;
    private int currentAlgorithmIndex;

    public SearchManager() {
        searchAlgorithm = new ArrayList<>();

        searchAlgorithm.add(new GreedyBFS());
        searchAlgorithm.add(new AStar());
    }

    public boolean isFinished(){
        return getCurrentAlgorithm().isFinished;
    }
    private SearchAlgorithm getCurrentAlgorithm(){
        return searchAlgorithm.get(currentAlgorithmIndex);
    }

    public void stepSearch(){
        getCurrentAlgorithm().stepSearch();
    }
    public void initializeSearch(Cell[][] cells, Cell startCell, Cell endCell){
        getCurrentAlgorithm().initializeSearch(cells, startCell, endCell);
    }

    public void nextAlgorithm(){
        currentAlgorithmIndex++;
        currentAlgorithmIndex %= searchAlgorithm.size();
    }

}
