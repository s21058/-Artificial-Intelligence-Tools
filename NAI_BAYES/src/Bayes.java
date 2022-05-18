import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Bayes {
static ArrayList<ArrayList<String>>testList;
static ArrayList<ArrayList<String>>trainList;
static int countYes;
static int countNo;
static ArrayList arrayList;
    public static void main(String[] args) {
        testList = new ArrayList<>();
        trainList = new ArrayList<>();
        feelTrainTest();
        feelTestTest();
        bayesAlgorithm();
       input();
    }
    public static void input(){
        String s="";
        while (!s.equals("exit")) {
            System.out.println("Enter your own values:" +
                    "pogoda,tak/nie,temperatura,cisknicie");
            Scanner scanner = new Scanner(System.in);
            s = scanner.nextLine();
            String[] help = s.split(",");
            ArrayList<String> userList = new ArrayList<>(Arrays.asList(help));
            bayesAlgorithm(userList);
        }
    }
    public static void bayesAlgorithm() {
        findingYESNO();
        double probabilityForTrue = 0;
        double probabilityForFalse = 0;
        for (int i = 0; i < testList.size(); i++) {
            probabilityForTrue += (double) countYes / (double) trainList.size();
            probabilityForFalse += (double) countNo / (double) trainList.size();
            for (int k = 0; k < testList.get(k).size(); k++) {
                int tmp = probabilityTrue(testList.get(i).get(k), k);
                if (tmp == 0) {
                    double newTmp = smoothing(tmp, countYes, k);
                    probabilityForTrue = probabilityForTrue * newTmp;
                } else {
                    probabilityForTrue = probabilityForTrue * ((double) tmp / (double) countYes);
                }
                int tmpF = probabilityFalse(testList.get(i).get(k), k);
                if (tmpF == 0) {
                    double newTmp = smoothing(tmpF, countNo, k);
                    probabilityForFalse = probabilityForFalse * newTmp;
                } else {
                    probabilityForFalse = probabilityForFalse * ((double) tmpF / countNo);
                }
            }
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("FOR ROUND: " + i + "\n" + "TRUE: " + String.format("%.9g%n", probabilityForTrue) + "\n" + "FALSE: " + String.format("%.9g%n", probabilityForFalse));
            System.out.println("Decision: "+chekMaxProbability(probabilityForTrue,probabilityForFalse));
            System.out.println("-------------------------------------------------------------------------------------------------");

            probabilityForTrue = 0;
            probabilityForFalse = 0;
        }
    }public static void bayesAlgorithm(ArrayList<String>userList) {
        countYes=0;
        countNo=0;
        findingYESNO();
        double probabilityForTrue = 0;
        double probabilityForFalse = 0;
        for (int i = 0; i < 1; i++) {
            probabilityForTrue += (double) countYes / (double) trainList.size();
            probabilityForFalse += (double) countNo / (double) trainList.size();
            for (int j = 0; j < userList.size(); j++) {
                int tmp = probabilityTrue(userList.get(j), j);
                if (tmp == 0) {
                    double newTmp = smoothing(tmp, countYes, j);
                    probabilityForTrue = probabilityForTrue * newTmp;
                } else {
                    probabilityForTrue = probabilityForTrue * ((double) tmp / (double) countYes);
                }
                int tmpF = probabilityFalse(userList.get(j), j);
                if (tmpF == 0) {
                    double newTmp = smoothing(tmpF, countNo, j);
                    probabilityForFalse = probabilityForFalse * newTmp;
                } else {
                    probabilityForFalse = probabilityForFalse * ((double) tmpF / countNo);
                }
            }
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("FOR ROUND: " + i + "\n" + "TRUE: " + String.format("%.9g%n", probabilityForTrue) + "\n" + "FALSE: " + String.format("%.9g%n", probabilityForFalse));
            System.out.println("Decision: "+chekMaxProbability(probabilityForTrue,probabilityForFalse));
            System.out.println("-------------------------------------------------------------------------------------------------");

            probabilityForTrue = 0;
            probabilityForFalse = 0;
        }
    }
    public static String chekMaxProbability(double first,double second){
        Map<String,Double>maxi=new TreeMap<>();
        maxi.put("tak",first);
        maxi.put("nie",second);
        String retStr="";
        for (Map.Entry<String,Double>value:maxi.entrySet()) {
            for (Map.Entry<String,Double>value1:maxi.entrySet()) {
                if(value.getValue()>value.getValue()){
                    retStr+=value.getKey();
                }else if(value.getValue()<value1.getValue()){
                    retStr+=value1.getKey();
                }
            }
        }
        return retStr;
    }
    public static <T>double smoothing(double tmp,double countYN,int k) {
        tmp += 1;
        double total = 0;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < trainList.size(); i++) {
                set.add(trainList.get(i).get(k));
        }
        System.out.println(set);
        total = tmp / (countYN+(double) set.size());
        System.out.println("After smoothing value become: ["+total+"]");
        return total;
    }
    public static int probabilityTrue(String str,int k) {
        int count = 0;
        for (int i = 0; i < trainList.size(); i++) {
                if (trainList.get(i).get(k).equals(str) && trainList.get(i).get(trainList.get(i).size() - 1).equals(arrayList.get(1))) {
                    count++;
            }
        }
        System.out.println("True: "+str+"-"+count);
        return count;
    }
    public static int probabilityFalse(String str,int k) {
        int count = 0;
        for (int i = 0; i < trainList.size(); i++) {
            if (trainList.get(i).get(k).equals(str) && trainList.get(i).get(trainList.get(i).size() - 1).equals(arrayList.get(0))) {
                count++;
            }
        }
        System.out.println("False: "+str+"-"+count);
        return count;
    }

    public static<T> void findingYESNO() {
        Set<T> set = new HashSet<>();
        arrayList=new ArrayList<T>();
        for (int i = 0; i < trainList.size(); i++) {
            set.add((T) trainList.get(i).get(trainList.get(i).size() - 1));
        }
        for (T hm : set) {
            for (int i = 0; i < trainList.size(); i++) {
                if (trainList.get(i).get(trainList.get(i).size() - 1).equals(hm)) {
                    countNo++;
                } else {
                    countYes++;
                }
            }
            break;
        }
//        System.out.println(countYes);
//        System.out.println(countNo);
        arrayList.addAll(set);
    }
    public static void feelTrainTest()  {
        Path path = Path.of("Train.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String str = reader.readLine();
            while (str != null) {
                String[] helpStr = str.split(",");
                ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(helpStr));
                trainList.add(tmpList);
                str = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void feelTestTest()  {
        Path path = Path.of("Test.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String str = reader.readLine();
            while (str != null) {
                String[] helpStr = str.split(",");
                ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(helpStr));
                testList.add(tmpList);
                str = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
