import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Huffman {
    static ArrayList<Vertex> vertexList;
    static ArrayList<Vertex> tmpVertex;
    static ArrayList<Vertex> resultList;
    public static void main(String[] args) throws IOException {
        vertexList = new ArrayList<>();
        tmpVertex = new ArrayList<>();
        resultList=new ArrayList<>();

        readFromFile();
        resultList.addAll(vertexList);
        sort(vertexList);
        sort(resultList);
        Algorithm(vertexList.get(0), vertexList.get(1));

        for (int i = 0; i <resultList.size() ; i++) {
            for (int j =tmpVertex.size()-1;j>=i;j--) {
                if(tmpVertex.get(j).charachter.contains(resultList.get(i).charachter)){
                    resultList.get(i).byteArray.add(tmpVertex.get(j).byteArray.get(0));
                }
            }
        }
        for (int i = 0; i <resultList.size() ; i++) {
            System.out.println(resultList.get(i).charachter+"  "+resultList.get(i).byteArray);
        }
    }

    public static void Algorithm(Vertex ver, Vertex ver1) {
        sumVertex(ver, ver1);
        vertexList.remove(ver);
        vertexList.remove(ver1);
        sort(vertexList);
        if (vertexList.size() == 1) {
            return;
        }
        Algorithm(vertexList.get(0), vertexList.get(1));
    }

    public static void sumVertex(Vertex ver, Vertex ver1) {
        if (ver.values == ver1.values) {
            int compare = ver.charachter.compareTo(ver1.charachter);
            //ver is larger
            if (compare > 0) {
                vertexList.add(new Vertex(ver1.charachter + ver.charachter, ver1.values + ver.values));
                tmpVertex.add(new Vertex(ver1.charachter, (byte) 0));
                tmpVertex.add(new Vertex(ver.charachter, (byte) 1));
            }
            // ver is smaller
            if (compare < 0) {
                vertexList.add(new Vertex(ver.charachter + ver1.charachter, ver.values + ver1.values));
                tmpVertex.add(new Vertex(ver.charachter, (byte)0));
                tmpVertex.add(new Vertex(ver1.charachter, (byte)1));
            }
        } else if (ver.values > ver1.values) {
            vertexList.add(new Vertex(ver1.charachter + ver.charachter, ver1.values + ver.values));
            tmpVertex.add(new Vertex(ver1.charachter, (byte)0));
            tmpVertex.add(new Vertex(ver.charachter, (byte)1));
        } else {
            vertexList.add(new Vertex(ver.charachter + ver1.charachter, ver.values + ver1.values));
            tmpVertex.add(new Vertex(ver.charachter, (byte)0));
            tmpVertex.add(new Vertex(ver1.charachter, (byte)1));
        }
    }

    public static void readFromFile() throws IOException {
        Path path = Path.of("test.txt");
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
        String str = reader.readLine();
        while (str != null) {
            String[] helpStr = str.split("\\s");
            vertexList.add(new Vertex(helpStr[0], Integer.parseInt(helpStr[1])));
            str = reader.readLine();
        }
    }

    public static void sort(ArrayList<Vertex> vertexList) {
        vertexList.sort(Comparator.comparingInt(ver -> ver.values));
    }
}
