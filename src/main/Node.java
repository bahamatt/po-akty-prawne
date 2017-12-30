import java.util.ArrayList;

public class Node {
    private String type;
    private String number;
    private String content;
    private ArrayList<Node> subNodes;

    public Node(String type, String number) {
        this.type = type;
        this.number = number;
    }

    public void add(String content) {
        this.content += content;
    }
}
