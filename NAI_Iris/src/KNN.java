import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KNN {
    private final ArrayList<ArrayList<Double>> listOfTestSet;
    private ArrayList<ArrayList<Double>> listTrainSet;
    private ArrayList<String> listWithIrisName;
    private ArrayList<Double> distance;
    private ArrayList<String> fullListNameIris;
    private Map<String, Long> mapToGetSearchedIrisType;
    private  String identifyIris;
    int k;
    int count;

    public KNN(int k) {
        this.k = k;
        listOfTestSet = new ArrayList<>();
        listWithIrisName = new ArrayList<>();
        fullListNameIris = new ArrayList<>();
        feelTrainSetList();
        feelTestSetList();
        System.out.println("Im Studying...\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calculateDistance();
        System.out.println("\nDONE!");


    }

    public void feelTestSetList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test-set.txt"));
            String line = reader.readLine();
            while (line != null) {
                findUniqueIrisName(line);
                listOfTestSet.add(method(line));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void feelTrainSetList(int size) {
        listTrainSet.clear();
        fullListNameIris.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("train-set.txt"));
            String line = reader.readLine();
            while (line != null) {
                listTrainSet.add(method(line, size));
                feelIrisList(line);
                line = reader.readLine();
            }
            System.out.println("["+GUI_KNN.getVector()+"] - YOUR VECTOR");
            System.out.println("["+listTrainSet.get(0).size()+"] - SIZE OF GIVING VECTOR\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void feelTrainSetList() {
        fullListNameIris.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("train-set.txt"));
            String line = reader.readLine();
            listTrainSet = new ArrayList<>();
            while (line != null) {
                listTrainSet.add(method(line));
                feelIrisList(line);
                line = reader.readLine();
            }
            System.out.println("        -------------------------------------------TRAIN DATA-------------------------------------------------");
            System.out.println(listTrainSet);
            System.out.println("        -------------------------------------------TRAIN DATA-------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Double> method(String line) {
        ArrayList<Double> list = new ArrayList<>();
        String[] helpStr = line.split(",");
        for (int i = 0; i < helpStr.length - 1; i++) {
            list.add(Double.valueOf(helpStr[i]));
        }
        return list;
    }

    public ArrayList<Double> method(String line, int size) {
        ArrayList<Double> list = new ArrayList<>();
        String[] helpStr = line.split(",");
        for (int i = 0; i < size; i++) {
            list.add(Double.valueOf(helpStr[i]));
        }
        return list;
    }

    public void findUniqueIrisName(String line) {
        String[] helpStr = line.split(",");
        for (int i = 0; i < helpStr.length; i++) {
            if (listWithIrisName.contains(helpStr[helpStr.length - 1])) {
            } else {
                listWithIrisName.add(helpStr[helpStr.length - 1]);
            }
        }
    }

    public void feelIrisList(String line) {
        String[] helpStr = line.split(",");
        fullListNameIris.add(helpStr[helpStr.length - 1]);
    }


    public void calculateDistance() {
        double som;
        while (count != listOfTestSet.size()) {
            distance = new ArrayList<>();
            for (int i = 0; i < listTrainSet.size(); i++) {
                som = 0;
                for (int j = 0; j < listTrainSet.get(i).size(); j++) {
                    som += Math.pow(listOfTestSet.get(count).get(j) - listTrainSet.get(i).get(j), 2);
                }
                distance.add(som);
            }
            sort(distance, fullListNameIris);
            SearchKnn(k);
            distance.clear();
        }

    }

    public void showWhatKindOfIris(ArrayList<Double> list) {
        double som;
        distance = new ArrayList<>();
        for (int i = 0; i < listTrainSet.size(); i++) {
            som = 0;
            for (int j = 0; j < listTrainSet.get(i).size(); j++) {
                som += Math.pow(list.get(j) - listTrainSet.get(i).get(j), 2);
            }
            distance.add(som);
        }
        sort(distance, fullListNameIris);
        show();
        SearchKnn(k);
    }

    public void show() {
//        System.out.println("--------------------The K distances for finding KNN for our vector: "+Main.list);
        System.out.println("----------------------------------------------------------------------------");

        for (int i = 0; i <=k; i++) {
            System.out.println(distance.get(i) + "     " + fullListNameIris.get(i));
        }
        System.out.println("----------------------------------------------------------------------------");
    }


    public void sort(ArrayList<Double> distance, ArrayList<String> fullListNameIris) {
        for (int i = 0; i < fullListNameIris.size(); i++) {
            for (int j = 0; j < fullListNameIris.size(); j++) {
                if (distance.get(i) > distance.get(j)) {
                    Collections.swap(distance, i, j);
                    Collections.swap(fullListNameIris, i, j);
                }
            }
        }
        Collections.reverse(distance);
        Collections.reverse(fullListNameIris);
    }

    public void SearchKnn(int k) {
        mapToGetSearchedIrisType = new TreeMap<>();
        mapToGetSearchedIrisType = fullListNameIris.stream().limit(k).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        mapToGetSearchedIrisType.forEach((key, value) -> System.out.println(key + " " + value));
        Long mostLessValues = (Collections.max(mapToGetSearchedIrisType.values()));
         identifyIris = "";
        for (Map.Entry<String, Long> entry : mapToGetSearchedIrisType.entrySet()) {
            if (entry.getValue().equals(mostLessValues)) {
                identifyIris = entry.getKey();
            }
        }
        System.out.println("The new flower type was determined as: " + identifyIris);
        System.out.println("Accuracy: " + ((double) mostLessValues / (double) k) * 100 + "%");
        System.out.println("------------------------------------------------------");
        count++;
    }

    public String getIdentifyIris() {
        return identifyIris;
    }
}
