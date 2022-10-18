import java.util.ArrayList;

public class Vertex {
    String charachter;
    int values;
    ArrayList<Byte>byteArray=new ArrayList<>();

    public Vertex(String charachter,int values) {
        this.charachter = charachter;
        this.values=values;
    }

    public Vertex(String charachter,byte bb) {
        this.charachter = charachter;
        byteArray.add(bb);
    }

    @Override
    public String toString() {
        return  charachter + "  "+ values;
    }
    public String getCharachter() {
        return charachter;
    }
    public void addByte(byte bb){
        byteArray.add(bb);
    }
    public int getValues() {
        return values;
    }

    public ArrayList<Byte> getByteArray() {
        return byteArray;
    }
}
