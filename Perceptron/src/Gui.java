import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Gui {
    JFrame frame;
    JPanel panel;
    static String vector;

    Gui() {
        uI();
    }

    public void uI() {
        Perceptron perceptron= new Perceptron();
        frame = new JFrame("My Drawing");
        frame.setBounds(500, 200, 400, 400);
        JButton button = new JButton("Generate start random values");
        button.setBounds(70, 10, 250, 40);
        JButton button1 = new JButton("Apply vector");
        TextField textField1 = new TextField();
        textField1.setBounds(200, 70, 120, 20);
        button1.setBounds(70, 70, 120, 20);
        JButton button2 = new JButton("KNOW KIND OF IRIS");
        button2.setBounds(110, 150, 170, 100);
        button.addActionListener(e -> {
           perceptron.randomValues();
        });
        button1.addActionListener(e -> {
            vector = textField1.getText();
        });
        button2.addActionListener(e -> {
            ArrayList<Double> list = new ArrayList();
            list = new ArrayList<>();
            String[]args=vector.split(",");
            for (int i = 0; i <args.length ; i++) {
                list.add(Double.valueOf(args[i]));
            }
            int k=0;
            perceptron.imMet(k,3);
            perceptron.training();
            perceptron.showWhatKindOfIris(list);
            frame.dispose();
            JFrame frame1 = new JFrame();
            frame1.setVisible(true);
            frame1.setBounds(500, 200, 400, 400);
            JPanel panel = new JPanel();
            panel.setLayout(null);
            frame1.add(panel);

            JButton button3 = new JButton("Try to identify another Iris");
            button3.addActionListener(e1 -> {
            frame1.dispose();
            Gui gui = new Gui();
            });
            button3.setBounds(100, 80, 200, 70);
            JButton button4 = new JButton("Exit");

            button4.setBounds(120, 200, 150, 70);
            button4.addActionListener(e2 -> {
                System.exit(1);
            });
            JTextArea textArea = new JTextArea();
            textArea.setBounds(100, 30, 200, 30);
            textArea.setText(perceptron.getName());
            panel.add(button3);

            panel.add(textArea);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            panel.add(button4);
        });
        panel = new JPanel();
        panel.add(button2);
        panel.setLayout(null);
        panel.add(textField1);
        panel.add(button1);
        frame.add(panel);
        panel.add(button);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
