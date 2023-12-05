package Visualizer.Managers;

import Visualizer.SearchAlgortihms.AStar.AStar;
import Visualizer.SearchAlgortihms.SearchAlgorithm;

import java.util.ArrayList;

public class SearchManager {
    private final ArrayList<SearchAlgorithm> searchAlgorithm;
    private int currentAlgorithmIndex;

    public SearchManager() {
        searchAlgorithm = new ArrayList<>();

//        AStar aStar = new AStar();
//        searchAlgorithm.add(aStar);
    }

    public void nextAlgorithm(){
        currentAlgorithmIndex++;
        currentAlgorithmIndex %= searchAlgorithm.size();
    }

    public void stepAlgorithm(){
        searchAlgorithm.get(currentAlgorithmIndex).stepSearch();
    }




}
