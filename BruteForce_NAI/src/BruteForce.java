import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class BruteForce {
    static int knapsackSize;
    static int numberOfItems;
    static int resultProfit;
    static int[] profits;
    static int[] weights;
    static int[] result;
    static int[] tmp;
    static int resultWeight;
    static int sumOfProfit;

    public static void main(String[] args) throws IOException {
        readFromFile();
        result = new int[numberOfItems];
        Arrays.fill(result, 0);
        sumOfProfit = 0;
        int[] ret = bruteForceAlgorithm();
        System.out.print("\nResult of algorithm: ");
        for (int i = 0; i < ret.length; i++) {
            System.out.print(ret[i] + " ");
        }
        System.out.println("\nWith weight: " + resultWeight + " and profit: " + resultProfit);
    }

    public static int[] bruteForceAlgorithm() {
        int size;
        int iterationCount = 0;
        for (int i = 0; i < numberOfItems - 1; i++) {
            tmp = new int[numberOfItems];
            sumOfProfit = 0;
            iterationCount++;
            size = countIfPlaceIsEnough(knapsackSize, i);
            for (int j = numberOfItems - 1; j > i; j--) {
                size = countIfPlaceIsEnough(size, j);
                iterationCount++;

            }
            System.out.print("The better vector to that moment is: ");
            for (int h = 0; h < result.length; h++) {
                System.out.print(result[h]);
            }
            System.out.println("\nWith weight: " + resultWeight + " and profit: " + resultProfit);
            findBetter(tmp);
        }
        System.out.println("Was made: " + iterationCount + " iteration");
        return result;
    }

    public static void findBetter(int[] tmp) {
        resultProfit = findProfitsForResult();
        if (sumOfProfit > resultProfit) {
            resultProfit = sumOfProfit;
            for (int i = 0; i < tmp.length; i++) {
                result[i] = tmp[i];
                resultWeight = findWeightsForResult();
            }
        }
    }

    public static int findProfitsForResult() {
        int count = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] == 1) {
                count = count + profits[i];
            }
        }
        return count;
    }

    public static int findWeightsForResult() {
        int count = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] == 1) {
                count = count + weights[i];
            }
        }
        return count;
    }

    public static int countIfPlaceIsEnough(int size, int position) {
        if (size - weights[position] >= 0) {
            tmp[position] = 1;
            size = size - weights[position];
            sumOfProfit = sumOfProfit + profits[position];
        } else {
            tmp[position] = 0;
        }
        return size;
    }

    public static void readFromFile() throws IOException {
        Path path = Path.of("plecak.txt");
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
        String str = reader.readLine();
        int count = 0;
        String[] strings;

        while (str != null) {
            if (count == 0) {
                strings = str.split(",");
                knapsackSize = Integer.parseInt(strings[0]);
                numberOfItems = Integer.parseInt(strings[1]);
                weights = new int[numberOfItems];
                profits = new int[numberOfItems];
            } else if (count == 1) {
                strings = str.split(",");

                for (int i = 0; i < strings.length; i++) {
                    profits[i] = Integer.parseInt(strings[i]);
                }

            } else {
                strings = str.split(",");

                for (int i = 0; i < strings.length; i++) {
                    weights[i] = Integer.parseInt(strings[i]);
                }
            }
            str = reader.readLine();
            count++;
        }
    }
}
