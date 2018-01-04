import java.util.ArrayList;

public class Printer {
    public void printText(Node root, ArrayList<Option> options) {
        switch (options.get(0).optionType) {
            case ToC: {
                read(root, 1);
            }
            case Section: {
                Node node = search(root, 0, options.get(0).value);
                if (node != null) {
                    read(node, 1);
                } else {
                    System.out.println("Nie znaleziono takiego działu");
                }
            }
            case Chapter: {
                Node node = search(root, 1, options.get(0).value);
                if (node != null) {
                    read(node, 6);
                } else {
                    System.out.println("Nie znaleziono takiego rozdziału");
                }
            }
        }
    }

    private void read(Node node, int level) {
        if (node != null && node.getLevel() <= level) {
            System.out.println(node.toString());
            for (Node subNode : node.getSubNodes()) {
                read(subNode, level);
            }
        }
    }

    private Node search(Node node, int level, String number) {
        if (node != null) {
           if (node.getLevel() == level && node.getNumber().equals(number)) {
               return node;
           } else {
               Node foundNode = null;
               for (Node subNode: node.getSubNodes()) {
                   if (foundNode == null) {
                       foundNode = search(subNode, level, number);
                   }
               }
               return foundNode;
           }
        } else {
            return null;
        }
    }
}
