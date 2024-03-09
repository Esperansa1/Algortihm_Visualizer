package Visualizer.Model.SearchAlgortihms.AStar;

public class AStarCell{

    public double h;
    public double f;
    public double g;
    public int weight;


    public AStarCell(double h, double f, double g) {
        this.h = h;
        this.f = f;
        this.g = g;
        weight = 1;
    }


}
