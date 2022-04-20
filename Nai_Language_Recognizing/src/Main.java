import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {
    static ArrayList<Perceptron> perceptronList;
    static ArrayList<String> listWithDirectories;
    static ArrayList<String> listWithFiles;
    static ArrayList<Node> nodeArrayList;
    static double countSuc;
    static double countUnSuc;
    static double alpha;
    static int y = 0;
    static int d = 0;

    static String string = "C:\\Users\\Sviatoslav\\IdeaProjects\\Nai_Language_Recognizing\\src\\Languages";

    public static void main(String[] args) throws IOException {

        perceptronList = new ArrayList<>();
        nodeArrayList = new ArrayList<>();
        listWithDirectories = getDirectories();
        System.out.println(listWithDirectories);
        listWithFiles = getFiles();
        generateValues();
        for (int i = 0; i < listWithDirectories.size(); i++) {
            readAllFilesFromGivenFolder(listWithDirectories.get(i));
        }
        sortNode();
        for (int i = 0; i < listWithDirectories.size(); i++) {
            File file = new File(listWithDirectories.get(i));
            perceptronList.add(new Perceptron(file.getName()));
        }
        for (int i = 0; i < nodeArrayList.size(); i++) {
            System.out.println(nodeArrayList.get(i).nameOfLanguage+"  "+nodeArrayList.get(i).trainSetList+"\n");
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("---------------------------------"+i+" CYLCE OF TRAINIG--------------------------------------------");
            Study(nodeArrayList, 0, 0);
            countSuc=0;
            countUnSuc=0;
            sortNode();
            System.out.println("-----------------------------------------------------------------------------------------------------");
        }

        Scanner scanner = new Scanner(System.in);
//        String string=scanner.nextLine();
//        string=string.replaceAll("[^a-z]","");
//        showLanguage(countChars(string));
        while (!string.equals("exit")) {
            string = scanner.nextLine();
            string=string.replaceAll("[^a-z]","");
            showLanguage(countChars(string));
        }
//            forSc=scanner.next();
//        }
    }

    public static void Study(ArrayList<Node> nodeArrayList, int i, int j) {
        double net = 0;
        countSuc = 0;
        countUnSuc = 0;

        for (; j < perceptronList.size(); j++) {
            countSuc=0;
            countUnSuc=0;
           i=0;
            for (; i < nodeArrayList.size(); i++) {
                net = 0;
                for (int l = 0; l < nodeArrayList.get(i).getTrainSetList().size(); l++) {
                    net += perceptronList.get(j).getWeights().get(l) * nodeArrayList.get(i).getTrainSetList().get(l);
                }
                if (net < perceptronList.get(j).t && perceptronList.get(j).languageName.equals(nodeArrayList.get(i).nameOfLanguage)) {
                    countUnSuc++;
                    y = 0;
                    makeCalculations(i,j);
                    System.out.println("UNSUCCESS" + " [" + i + "]"+" ["+j+" ]");
                    Study(nodeArrayList, i,j);
                    return;
                } else if (net > perceptronList.get(j).t && !perceptronList.get(j).languageName.equals(nodeArrayList.get(i).nameOfLanguage)) {
                    y = 1;
                    countUnSuc++;
                    makeCalculations(i,j);
                    System.out.println("UNSUCCESS" + " [" + i + "]"+" ["+j+" ]");
                    Study(nodeArrayList, i,j);
                    return;
                } else {
                    System.out.println("SUCCESS" + " [" + i + "]");
                    countSuc++;

                }
            }
            System.out.println("ACCURACY: " + ((countSuc - countUnSuc) * 100) / nodeArrayList.size() + " %");
            System.out.println("\n"+perceptronList.get(j).weights+"\n");
        }
    }
    public static void showLanguage(ArrayList<Double>list) {
        System.out.println(list.size());
        Map<String, Double> map = new TreeMap<>();
        double net = 0;
        for (int i = 0; i < perceptronList.size(); i++) {
            net = 0;
            for (int j = 0; j < list.size(); j++) {
                net += perceptronList.get(i).weights.get(j) * list.get(j);
            }
            if (net > perceptronList.get(i).t) {
//                System.out.println(perceptronList.get(i).languageName);
                map.put(perceptronList.get(i).languageName, net);
            }
        }
        int count=0;
        if(map.size()==0) {
            sortNode();
            for (int i = 0; i < perceptronList.size(); i++) {
                perceptronList.get(i).setT(perceptronList.get(i).generateT());
            }
            Study(nodeArrayList, 0, 0);
            showLanguage(list);
            return;
         }

        Double max = Collections.max(map.values());
        System.out.println(max);
        for (Map.Entry<String, Double> value:map.entrySet()) {
            if(value.getValue().equals(max)){
                System.out.println(value.getKey());
            }
        }
    }

    public static void makeCalculations(int i, int j) {
        nodeArrayList.get(i).trainSetList.add((double) -1);
        perceptronList.get(j).getWeights().add(perceptronList.get(j).t);
        ArrayList<Double> dd = new ArrayList<>(multiply(i));
        for (int l = 0; l < perceptronList.get(j).getWeights().size(); l++) {
            perceptronList.get(j).getWeights().set(l, dd.get(l) + perceptronList.get(j).getWeights().get(l));
        }
        perceptronList.get(j).t = perceptronList.get(j).getWeights().get(perceptronList.get(j).getWeights().size() - 1);
        perceptronList.get(j).getWeights().remove(perceptronList.get(j).getWeights().size() - 1);
        nodeArrayList.get(i).trainSetList.remove(nodeArrayList.get(i).trainSetList.size() - 1);
    }

    public static ArrayList<Double> multiply(int i) {
        if (y == 0) {
            d = 1;
        } else {
            d = 0;
        }

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

    public static ArrayList<String> getDirectories() throws IOException {
        List<Path> list = Files.walk(Paths.get(string)).filter(Files::isDirectory).collect(Collectors.toList());
        list.remove(0);
        return (ArrayList<String>) list.stream().map(Path::toString).collect(Collectors.toList());
    }

    public static ArrayList<String> getFiles() throws IOException {
        List<Path> list = Files.walk(Paths.get(string)).filter(Files::isRegularFile).collect(Collectors.toList());
        return (ArrayList<String>) list.stream().map(Path::toString).collect(Collectors.toList());
    }

    public static void readAllFilesFromGivenFolder(String directory) throws IOException {
        File file = new File(directory);
        File[] listOfFiles = file.listFiles();
        String str;
        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            str = "";
            String path = String.valueOf(Paths.get(String.valueOf(listOfFiles[i])));
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String string = reader.readLine();
            while (string != null) {
                str += string;
                string = reader.readLine();
            }
            str = str.replaceAll("[^a-z]", "");
            nodeArrayList.add(new Node(file.getName(), countChars(str)));
        }

    }

    public static void sortNode() {
//        for (int i = 0; i < nodeArrayList.size(); i++) {
//            Collections.swap(nodeArrayList, i, ThreadLocalRandom.current().nextInt(0, nodeArrayList.size() - 1));
        Collections.shuffle(nodeArrayList);
//        }
    }

    public static ArrayList<Double> countChars(String str) {
        Map<Character, Integer> textMap = new TreeMap<>();
        for (int i = 0; i < 26; i++) {
            textMap.put((char) ('a' + i), 0);
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            count = 0;
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    count++;
                }
            }
            textMap.put(str.charAt(i), count);
        }
        ArrayList<Double> list = new ArrayList<>();
        for (Map.Entry<Character, Integer> li : textMap.entrySet()) {
            list.add(Double.valueOf(li.getValue()));
        }
//        double sum = list.stream().reduce(0.0, Double::sum);
        double sum=0;
        for (int i = 0; i <list.size() ; i++) {
            sum+=Math.pow(list.get(i),2);
        }
        sum=Math.sqrt(sum);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) / sum);
        }
        return list;
    }

    public static void generateValues() {
        alpha = 0.7;
    }

}
