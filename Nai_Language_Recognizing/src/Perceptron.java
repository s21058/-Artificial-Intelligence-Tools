import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {
    String languageName;

    double t;
    ArrayList<Double> weights;

    Perceptron(String languageName) {
        weights = new ArrayList<>();
        this.languageName = languageName;
        generateT();
        generateWeights();
    }

    public double getT() {
        return t;
    }

    @Override
    public String toString() {
        return languageName + "  " + t;
    }

    public void generateWeights() {
        for (int i = 0; i < 26; i++) {
            weights.add(Math.random());
        }
//        double count = weights.stream().reduce(0.0, Double::sum);
        double count=0;
        for (int i = 0; i <weights.size() ; i++) {
            count+=(Math.pow(weights.get(i),2));
        }
        count=Math.sqrt(count);
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, weights.get(i) / count);
        }
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public String getLanguageName() {
        return languageName;
    }

    public double generateT() {
        return t = ThreadLocalRandom.current().nextDouble(0, 0.99 + 0.01);
    }

    public void setT(double t) {
        this.t = t;
    }
}
