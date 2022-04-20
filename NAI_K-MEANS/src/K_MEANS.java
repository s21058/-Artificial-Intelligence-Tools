
import org.apache.commons.collections4.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class K_MEANS {
    static ArrayList<Node> nodeArrayList;
    static ArrayList<Centroids> centroidsArrayList;
    static ArrayList<ArrayList<ArrayList<Double>>> tmpCluster;
    static Map<Integer, Double> map;
    static int k;
    static boolean check;

    public static void main(String[] args) {
        check = false;
        tmpCluster = new ArrayList<>();
        map = new TreeMap<>();
        nodeArrayList = new ArrayList<>();
        centroidsArrayList = new ArrayList<>();
        fillNodeList();
        k = 3;
        randomNode();
        makeCentroids();
        for (int i = 0; i < centroidsArrayList.size(); i++) {
            tmpCluster.add(new ArrayList<>(0));
        }
        int count=0;
        while (!check) {
            grouping();
            showSumOfSquaresOfAttributes();
//            SwingUtilities.invokeLater(new GUI(centroidsArrayList));
            method();
            updatePoints();
            clear(centroidsArrayList);
            grouping();
            showSumOfSquaresOfAttributes();
//            SwingUtilities.invokeLater(new GUI(centroidsArrayList));
            check = checkIfDifferent();
            tmpCluster.clear();
            for (int i = 0; i < centroidsArrayList.size(); i++) {
                tmpCluster.add(new ArrayList<>(0));
            }
            updatePoints();
            clear(centroidsArrayList);
            count++;
        }
        System.out.println("ENDS ON: "+count+" TIME");
    }

    public static void showSumOfSquaresOfAttributes() {
        double sum=0;
        for (int i = 0; i < centroidsArrayList.size(); i++) {
            sum=0;
            for (int j = 0; j < centroidsArrayList.get(i).nodes.size(); j++) {
                for (int l = 0; l <centroidsArrayList.get(i).nodes.get(j).attributes.size() ; l++) {
                    sum +=Math.pow(centroidsArrayList.get(i).nodes.get(j).attributes.get(l)+centroidsArrayList.get(i).points.get(l),2);
                }
            }
            sum=Math.sqrt(sum);
            System.out.println("SUM OF SQUARES OF ATTRIBUTES FOR C"+i+" IS: "+sum);
        }
    }
    public static boolean checkIfDifferent() {
        ArrayList<ArrayList<ArrayList<Double>>> list1 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> list2 = new ArrayList<>();

        for (int i = 0; i < centroidsArrayList.size(); i++) {
            list1.add(new ArrayList<>(0));
            list2.add(new ArrayList<>(0));
        }
        for (int i = 0; i < centroidsArrayList.size(); i++) {
            for (int j = 0; j < centroidsArrayList.get(i).nodes.size(); j++) {
                list1.get(i).add(centroidsArrayList.get(i).nodes.get(j).attributes);
            }
        }
        for (int i = 0; i < tmpCluster.size(); i++) {
            for (int j = 0; j < tmpCluster.get(i).size(); j++) {
                list2.get(i).add(tmpCluster.get(i).get(j));
            }
        }
        if (CollectionUtils.isEqualCollection(list1, list2) == false) {
            System.out.println(list1);
            System.out.println();
            System.out.println(list2);
        }
        return CollectionUtils.isEqualCollection(list1, list2);
    }

    public static void method() {
        for (int i = 0; i < centroidsArrayList.size(); i++) {
            for (int j = 0; j < centroidsArrayList.get(i).nodes.size(); j++) {
                tmpCluster.get(i).add(centroidsArrayList.get(i).nodes.get(j).attributes);
            }
        }
    }

    public static void grouping() {
        double count;
        for (int i = 0; i < nodeArrayList.size(); i++) {
            for (int j = 0; j < centroidsArrayList.size(); j++) {
                count = 0;
                for (int l = 0; l < nodeArrayList.get(j).attributes.size(); l++) {
                    count += Math.pow(centroidsArrayList.get(j).points.get(l) - nodeArrayList.get(i).attributes.get(l), 2);
                }
//                count = Math.sqrt(count);
                map.put(j, count);
            }
            for (Map.Entry<Integer, Double> value : map.entrySet()) {
                if (value.getValue().equals(Collections.min(map.values()))) {
                    centroidsArrayList.get(value.getKey()).nodes.add(nodeArrayList.get(i));
                }
            }
        }

        System.out.println("-------------------------------------------------------------------");
        for (int i = 0; i < centroidsArrayList.size(); i++) {
            System.out.println(centroidsArrayList.get(i).points + "-------------" + centroidsArrayList.get(i).nodes + "[" + centroidsArrayList.get(i).nodes.size() + "]" + "\n");
        }
        System.out.println("-------------------------------------------------------------------");

    }

    public static void updatePoints() {

        for (int i = 0; i < centroidsArrayList.size(); i++) {
            ArrayList<Double> list = new ArrayList<>();
            for (int k = 0; k < centroidsArrayList.get(0).nodes.get(0).attributes.size(); k++) {
                list.add((double) 0);
            }
            for (int j = 0; j < centroidsArrayList.get(i).nodes.size(); j++) {
                for (int l = 0; l < centroidsArrayList.get(i).nodes.get(j).attributes.size(); l++) {
                    list.set(l, list.get(l) + centroidsArrayList.get(i).nodes.get(j).attributes.get(l));
                }
            }
            for (int j = 0; j < centroidsArrayList.get(i).points.size(); j++) {
                centroidsArrayList.get(i).points.set(j, list.get(j) / centroidsArrayList.get(i).nodes.size());
                System.out.println(list.get(j) / centroidsArrayList.get(i).nodes.size());
            }
            list.clear();
        }
    }

    public static void clear(ArrayList<Centroids> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).nodes.clear();
        }
    }

    public static void fillNodeList() {
        Path path = Paths.get("iris.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String str = reader.readLine();
            while (str != null) {
                String[] strings = str.split(",");
                ArrayList<Double> list = new ArrayList<>();
                for (int i = 0; i < strings.length - 1; i++) {
                    list.add(Double.valueOf(strings[i]));
                }
                nodeArrayList.add(new Node(list));
                str = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void randomNode() {
        Collections.shuffle(nodeArrayList);
    }

    public static void makeCentroids() {
        for (int i = 0; i < k; i++) {
            ArrayList<Double> list = new ArrayList<>(nodeArrayList.get(i).attributes);
            centroidsArrayList.add(new Centroids(list));
        }
    }
}
