import java.util.ArrayList;

public class Node {
    private int level;
    private String number;
    private String content = "";
    private ArrayList<Node> subNodes = new ArrayList<>();
    private Node parent;

    public Node(Node parent, int level, String number) {
        this.parent = parent;
        this.level = level;
        this.number = number;
    }

    public void add(String content) {
        if (this.content.equals("")) {
            this.content = content;
        } else if (this.content.endsWith(" ")) {
            this.content += content;
        } else {
            this.content = this.content +  " " + content;
        }
    }

    public void addSubNode(Node subNode) {
        subNodes.add(subNode);
    }

    public int getLevel() {
        return level;
    }

    public Node getParent() {
        return parent;
    }
    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        String result;
        if (level == -1) {
            result = "";
        } else if (level == 0) {
            result = "Dział " + number + " ";
        } else if (level == 1) {
            result = "Rozdział " + number + " ";
        } else if (level == 2) {
            result = "Art. " + number + ". ";
        } else if (level == 3) {
            result = number + ". ";
        } else {
            result = number + ") ";
        }
        return result + content;
    }

    public ArrayList<Node> getSubNodes() {
        return subNodes;
    }
}
