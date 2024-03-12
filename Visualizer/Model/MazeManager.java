package Visualizer.Model;

import Visualizer.BoardObserver;
import Visualizer.Cell;
import Visualizer.HighlightObserver;
import Visualizer.Model.MazeAlgorithms.MazeAlgorithm;
import Visualizer.Model.MazeAlgorithms.Prims;
import Visualizer.Model.MazeAlgorithms.SAW;
import Visualizer.Observable;

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

    public void stepMazeGeneration(Cell[][] cells){
        getCurrentAlgorithm().stepMazeGeneration(cells);
        notifyObservers();
    }

    public void initializeMazeGeneration(Cell[][] cells){
        getCurrentAlgorithm().initializeMazeGeneration(cells);
    }

    public String getCurrentAlgorithmName(){
        return getCurrentAlgorithm().toString();
    }

    public void nextAlgorithm(){
        currentAlgorithmIndex++;
        currentAlgorithmIndex %= mazeAlgorithm.size();
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
//        currentAlgorithm.resetHighlight();
    }

    // Maze Observers
    private final List<HighlightObserver> observers = new ArrayList<>();
    public void addObserver(HighlightObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(HighlightObserver observer) {
        observers.remove(observer);
    }


}
