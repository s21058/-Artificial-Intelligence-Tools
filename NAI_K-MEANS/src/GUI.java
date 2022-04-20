import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends Canvas implements Runnable {
    ArrayList<Centroids>centroids;
    public GUI(ArrayList<Centroids> centroidsArrayList) {
        this.centroids=centroidsArrayList;
    }

    public void paint(Graphics g) {
//      setForeground(Color.BLUE);
        g.setColor(Color.BLUE);
        for (int i = 0; i <centroids.size() ; i++) {
            for (int j = 0; j <centroids.get(i).nodes.size() ; j++) {
                for (int k = 0; k <centroids.get(i).nodes.get(j).attributes.size() ; k++) {
                    if(k==2) {
                        break;
                    }else{
//                        g.fillOval(, , 10, 10);
                    }
                }
            }
            if(i==1){
                g.setColor(Color.RED);
            }else{
                g.setColor(Color.GREEN);
            }
        }
    }

    @Override
    public void run() {
        JFrame frame=new JFrame();
        GUI gui=new GUI(centroids);
        frame.setBounds(500,200,500,500);

        JPanel panel=new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        frame.add(gui);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
