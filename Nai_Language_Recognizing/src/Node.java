import java.util.ArrayList;

public class Node {
    String nameOfLanguage;
    ArrayList<Double> trainSetList;

    Node(String nameOfLanguage, ArrayList<Double> list) {
        this.nameOfLanguage = nameOfLanguage;
        this.trainSetList = list;
    }

    @Override
    public String toString() {
        return nameOfLanguage +" "+ trainSetList;
    }

    public ArrayList<Double> getTrainSetList() {
        return trainSetList;
    }

    public String getNameOfLanguage() {
        return nameOfLanguage;
    }
}
