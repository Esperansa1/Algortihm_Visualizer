package Visualizer;

import java.util.Set;

public interface Graph<T> {
    Set<T> getVertices();
    Set<T> getNeighbours(T vertex);
    Set<T> getNeighbours(int row, int col);
    Set<T> getEdges();

}
