import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {
    public Double t;
    public int y;
    public int d;
    public double alpha;
    private ArrayList<ArrayList<Double>> testList;
    private ArrayList<String> listWithIrisName;
    private ArrayList<Double> weights;
    private ArrayList<Node> nodeArrayList;
    ArrayList<String> getIris;
    double countSuc = 0;
    String name;
    double unSuc = 0;

    Perceptron() {
        nodeArrayList = new ArrayList<>();
        weights = new ArrayList<>();
        y = 0;
        listWithIrisName = new ArrayList<>();
        getIris = new ArrayList<>();
        fillTestSetList();
        fillNodeList();
//        randomValues();
//        System.out.println(t + ": t");
//        System.out.println(alpha + ": alpha");
        int i = 0;
//        imMet(i,3);
        System.out.println("------------------------------------------LETS GET TRAIN--------------------------------");
//        training();
    }

    public void fillNodeList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("new_TrainSet.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] arr = line.split(",");
                findUniqueIrisName(line);
                nodeArrayList.add(new Node(method(line), arr[arr.length - 1]));
                line = reader.readLine();
            }
            for (int i = 0; i <nodeArrayList.size() ; i++) {
                System.out.println(nodeArrayList.get(i).nameOfThisIris+" "+nodeArrayList.get(i).trainSetList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void study(ArrayList<Node> nodeArrayList, int i) {
        double net;
        double accracyWhenStud;
        for (; i < nodeArrayList.size(); i++) {
            net = 0;
            for (int j = 0; j < nodeArrayList.get(i).getTrainSetList().size(); j++) {
                net += weights.get(j) * nodeArrayList.get(i).getTrainSetList().get(j);
            }
            //setosa
            if (net < t && nodeArrayList.get(i).getNameOfThisIris().equals(listWithIrisName.get(0))) {
                unSuc++;
                y = 0;
                makeCalculations(i);
                System.out.println("UNSUCCESS" + " [" + i + "]");
                System.out.println(t);
                System.out.println(weights);
                System.out.println(i);
                study(nodeArrayList, i);
                return;
                //virginica
            } else if ((net > t && nodeArrayList.get(i).getNameOfThisIris().equals(listWithIrisName.get(1)))) {
                y = 1;
                unSuc++;
                makeCalculations(i);
                System.out.println("UNSUCCESS" + " [" + i + "]");
                System.out.println(t);
                System.out.println(weights);
                System.out.println(i);
                study(nodeArrayList, i);
                return;
            } else {
                System.out.println("SUCCESS" + " [" + i + "]");
                countSuc++;
                System.out.println(i);
            }
        }
        accracyWhenStud = ((countSuc - unSuc) * 100) / nodeArrayList.size();
        System.out.println("Total accuracy after training: " + accracyWhenStud + "%");
        System.out.println(weights);
    }

    public void training() {
        double net;
        double countSuc = 0;
        for (int i = 0; i < testList.size(); i++) {
            net = 0;
            for (int j = 0; j < testList.get(i).size(); j++) {
                net += weights.get(j) * testList.get(i).get(j);
            }
//            &&getIris.get(i).equals(listWithIrisName.get(0))
//            &&getIris.get(i).equals(listWithIrisName.get(1))
            if (net > t) {
                System.out.println(listWithIrisName.get(0));

                countSuc++;
            } else if (net < t) {
                countSuc++;
                System.out.println(listWithIrisName.get(1));
            } else {

                System.out.println("Undefinide");
            }
        }
        System.out.println("Accuracy: " + ((countSuc) * 100) / testList.size() + "%");
    }

    public void showWhatKindOfIris(ArrayList<Double> list) {
        double net = 0;
        for (int j = 0; j < list.size(); j++) {
            net += weights.get(j) * list.get(j);
        }
        if (net > t) {
            System.out.println(listWithIrisName.get(0));
            countSuc++;
            name=listWithIrisName.get(0);
        } else if (net < t) {
            countSuc++;
            System.out.println(listWithIrisName.get(1));
            name=listWithIrisName.get(1);
        } else {
            System.out.println("Undefined");
        }
//        System.out.println("Accuracy: " + ((countSuc) * 100) / + "%");
    }

    public void makeCalculations(int i) {
        nodeArrayList.get(i).trainSetList.add((double) -1);
        weights.add(t);
        ArrayList<Double> dd = new ArrayList<>(multiply(i));
        for (int j = 0; j < weights.size(); j++) {
            weights.set(j, dd.get(j) + weights.get(j));
        }
        t = weights.get(weights.size() - 1);
        weights.remove(weights.size() - 1);
        nodeArrayList.get(i).trainSetList.remove(nodeArrayList.get(i).trainSetList.size() - 1);


    }

    public ArrayList<Double> multiply(int i) {

        setD();
        double prikol = (d - y) * alpha;
        ArrayList<Double> list = new ArrayList<>();
        for (int j = 0; j < nodeArrayList.get(i).trainSetList.size(); j++) {
            list.add(nodeArrayList.get(i).trainSetList.get(j));
        }
        for (int j = 0; j < list.size(); j++) {
            list.set(j, prikol * list.get(j));
        }
        return list;
    }

    public void setD() {
        if (y == 0) {
            d = 1;
        } else {
            d = 0;
        }
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


    public void fillTestSetList() {
        testList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("TestSet.txt"));
            String line = reader.readLine();
            while (line != null) {
                getFromTestIris(line);
                testList.add(method(line));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFromTestIris(String line) {
        String[] args = line.split(",");
        getIris.add(args[args.length - 1]);
    }

    public ArrayList<Double> method(String line) {
        String[] args = line.split(",");
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < args.length - 1; i++) {
            list.add(Double.valueOf(args[i]));
        }
        return list;
    }

    public void randomValues() {
        alpha = ThreadLocalRandom.current().nextDouble(0.01, 0.99 + 0.01);
//        alpha=0.2;
        t = ThreadLocalRandom.current().nextDouble(0, 0.99 + 0.01);
        for (int i = 0; i < nodeArrayList.get(0).trainSetList.size(); i++) {
            weights.add((Math.random() * 10) - 5);
        }
    }

    public void randomNode() {
        for (int i = 0; i < nodeArrayList.size(); i++) {
            Collections.swap(nodeArrayList, i, ThreadLocalRandom.current().nextInt(0, nodeArrayList.size() - 1));
        }
    }

    public void imMet(int i, int countOfStudying) {
        for (int j = 0; j < countOfStudying; j++) {
            study(nodeArrayList, i);
            countSuc = 0;
            unSuc = 0;
            randomNode();
            i = 0;
        }
    }

    public String getName() {
        return name;
    }
}