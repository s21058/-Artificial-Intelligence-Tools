import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Node {
    ArrayList<Double>trainSetList;
    String nameOfThisIris;
    public Node(ArrayList<Double>trainSetList,String nameOfThisIris) {
        this.trainSetList=trainSetList;
        this.nameOfThisIris=nameOfThisIris;
    }

    @Override
    public String toString() {
        return  trainSetList +","+nameOfThisIris+"\n";
    }

    public ArrayList<Double> getTrainSetList() {
        return trainSetList;
    }

    public String getNameOfThisIris() {
        return nameOfThisIris;
    }
}
