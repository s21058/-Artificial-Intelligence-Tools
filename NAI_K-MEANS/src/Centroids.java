import java.util.ArrayList;

public class Centroids {
    ArrayList<Double>points;
    ArrayList<Node> nodes;
    public Centroids(ArrayList<Double>points){
        this.points=points;
        nodes=new ArrayList<>();
    }

    public ArrayList<Double> getPoints() {
        return points;
    }

    public void setNode(Node node) {
      nodes.add(node);
    }

    public ArrayList<Node> getNode() {
        return nodes;
    }

    @Override
    public String toString() {
        return points + "   " + nodes;
    }
}
