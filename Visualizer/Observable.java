package Visualizer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    private final List<T> observers = new ArrayList<>();
    public void addObserver(T observer) {
        observers.add(observer);
    }

    public void removeObserver(T observer) {
        observers.remove(observer);
    }

    public List<T> getObservers() {
        return observers;
    }

    public void notifyObservers(){

    }

}