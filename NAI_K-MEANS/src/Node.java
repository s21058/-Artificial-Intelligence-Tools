import java.util.ArrayList;

public class Node {
    public  ArrayList<Double>attributes;
    public Node(ArrayList<Double>attributes){
        this.attributes =attributes;
    }
    public  ArrayList<Double> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return attributes +"";
    }
}
