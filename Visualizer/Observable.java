package Visualizer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<BoardObserver> observers = new ArrayList<>();
    public void addObserver(BoardObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BoardObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (BoardObserver observer : observers) {
            observer.onBoardChanged();
        }
    }

}