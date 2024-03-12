package Visualizer.Model;

import Visualizer.BoardObserver;
import Visualizer.Cell;
import Visualizer.Observable;
import Visualizer.Model.SearchAlgortihms.AStar.AStar;

import Visualizer.Model.SearchAlgortihms.GreedyBFS;
import Visualizer.Model.SearchAlgortihms.Pledge;
import Visualizer.Model.SearchAlgortihms.SearchAlgorithm;
import Visualizer.Model.SearchAlgortihms.Tremaux;

import java.util.ArrayList;

public class SearchManager extends Observable<BoardObserver> {
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
        notifyObservers();
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

    @Override
    public void notifyObservers() {
        for(BoardObserver observer : getObservers()){
            observer.onBoardChanged();
        }
    }
}
