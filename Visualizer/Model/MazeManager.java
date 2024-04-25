package Visualizer.Model;

import Visualizer.*;
import Visualizer.Model.MazeAlgorithms.MazeAlgorithm;
import Visualizer.Model.MazeAlgorithms.Prims;
import Visualizer.Model.MazeAlgorithms.SAW;

import java.util.ArrayList;
import java.util.List;

public class MazeManager extends Observable<BoardObserver> {

    private final ArrayList<MazeAlgorithm> mazeAlgorithm;
    private int currentAlgorithmIndex;

    public MazeManager() {
        mazeAlgorithm = new ArrayList<>();

        mazeAlgorithm.add(new SAW());
        mazeAlgorithm.add(new Prims());
    }

    public boolean isRunning(){
        return getCurrentAlgorithm().isRunning();
    }
    private MazeAlgorithm getCurrentAlgorithm(){
        return mazeAlgorithm.get(currentAlgorithmIndex);
    }

    public void stepMazeGeneration(BoardGraph graph){
        getCurrentAlgorithm().stepMazeGeneration(graph);
        notifyObservers();
    }

    public void initializeMazeGeneration(BoardGraph graph){
        getCurrentAlgorithm().initializeMazeGeneration(graph);
    }

    public String getCurrentAlgorithmName(){
        return getCurrentAlgorithm().toString();
    }

    @Override
    public void notifyObservers() {
        for(BoardObserver observer : getObservers()){
            observer.onBoardChanged();
        }
        MazeAlgorithm currentAlgorithm = getCurrentAlgorithm();
        for(HighlightObserver observer : observers){
            observer.onMazeHighlight(currentAlgorithm.getCurrent());
        }
    }

    public String[] getAvailablePathfindingAlgorithms() {
        String[] names = new String[mazeAlgorithm.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = mazeAlgorithm.get(i).toString();
        }
        return names;
    }

    public void chooseAlgorithmByIndex(String selected) {

        currentAlgorithmIndex = mazeAlgorithm.indexOf(mazeAlgorithm.stream()
                .filter(algorithm -> algorithm.toString().equals(selected))
                .findFirst()
                .orElse(null));

        System.out.println(getCurrentAlgorithm() + " is selected");

    }



    // Maze Observers
    private final List<HighlightObserver> observers = new ArrayList<>();
    public void addObserver(HighlightObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(HighlightObserver observer) {
        observers.remove(observer);
    }


    public int getCurrentAlgorithmIndex() {
        return currentAlgorithmIndex;
    }
}
