package Visualizer.Managers;

import Visualizer.Cell;
import Visualizer.SearchAlgortihms.AStar.AStar;

import Visualizer.SearchAlgortihms.GreedyBFS;
import Visualizer.SearchAlgortihms.Pledge;
import Visualizer.SearchAlgortihms.SearchAlgorithm;
import Visualizer.SearchAlgortihms.Tremaux;

import java.util.ArrayList;

public class SearchManager {
    private final ArrayList<SearchAlgorithm> searchAlgorithm;
    private int currentAlgorithmIndex;

    public SearchManager() {
        searchAlgorithm = new ArrayList<>();

        searchAlgorithm.add(new AStar());
        searchAlgorithm.add(new Tremaux());
        searchAlgorithm.add(new Pledge());
        searchAlgorithm.add(new GreedyBFS());
    }

    public boolean isRunning(){
        return getCurrentAlgorithm().isRunning;
    }
    private SearchAlgorithm getCurrentAlgorithm()
    {
        return searchAlgorithm.get(currentAlgorithmIndex);
    }

    public String getCurrentAlgorithmName(){
        return getCurrentAlgorithm().toString();
    }


    public void stepSearch(){
        getCurrentAlgorithm().stepSearch();
    }
    public void initializeSearch(Cell[][] cells){
        getCurrentAlgorithm().initializeSearch(cells);
    }

    public void nextAlgorithm(){
        getCurrentAlgorithm().resetAlgorithm();
        currentAlgorithmIndex++;
        currentAlgorithmIndex %= searchAlgorithm.size();
        getCurrentAlgorithm().resetAlgorithm();
    }

}
