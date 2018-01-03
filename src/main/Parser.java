import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private Node current;

    public void openFile(String fileName, ArrayList<Option> options) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null && !line.matches("Rozdział.*|DZIAŁ.*")) {
                line = br.readLine(); // pomija wszystko do momentu trafienia na pierwszy dział lub rozdział
            }
            Node main = new Node(null, -1, null);
            current = main;
            while (line != null) {
                if (line.matches("©Kancelaria Sejmu.*")) {
                    line = br.readLine();
                    continue;
                }
                if (line.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    line = br.readLine();
                    continue;
                }
                while (line.matches(".*-$")) {
                    line = line.substring(0, line.length() - 1) + br.readLine();
                }
                if (line.matches("DZIAŁ .*")) {
                    Node node = newNode(0, line.substring(6));
                    line = "";
                    current.addSubNode(node);
                    current = node;
                }
                if (line.matches("Rozdział .*")) {
                    Node node = newNode(1, line.substring(9));
                    line = "";
                    current.addSubNode(node);
                    current = node;
                }
                if (line.matches("Art\\. .*")) {
                    Node node = newNode(2, line.substring(5, line.indexOf(".", 5)));
                    current.addSubNode(node);
                    current = node;
                    int index = line.indexOf(".", 5) + 2;
                    if (index >= line.length()) {
                        line = "";
                    } else {
                        line = line.substring(line.indexOf(".", 5) + 2);
                    }
                }
                if (line.matches("\\d+[a-z]?\\..*")) {
                    Node node = newNode(3, line.substring(0, line.indexOf(".")));
                    current.addSubNode(node);
                    current = node;
                    line = line.substring(line.indexOf(".") + 2);
                }
                if (line.matches("\\d+[a-z]?\\).*")) {
                    Node node = newNode(4, line.substring(0, line.indexOf(")")));
                    current.addSubNode(node);
                    current = node;
                    line = line.substring(line.indexOf(")") + 2);
                }
                if (line.matches("[a-z]{1}\\).*")) {
                    Node node = newNode(5, line.substring(0, line.indexOf(")")));
                    current.addSubNode(node);
                    current = node;
                    line = line.substring(line.indexOf(")") + 2);
                }
                if (!line.isEmpty()) {
                    current.add(line);
                }
                line = br.readLine();
            }
            read(main);
        }
    }

    private Node newNode(int level, String number) {
        while (current.getLevel() > level - 1) {
            current = current.getParent();
        }
        return new Node(current, level, number);
    }

    private void read(Node node){
        if(node != null) {
            System.out.println(node.toString());
            for(Node subNode: node.getSubNodes()) {
                read(subNode);
            }
        }
    }
}
