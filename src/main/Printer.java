import java.util.ArrayList;

public class Printer {
    public void printText(Node root, ArrayList<Option> options) {
        if (options.size() == 1) {
            switch (options.get(0).optionType) {
                case ToC: {
                    read(root, 2);
                }
                break;
                case Section: {
                    Node node = search(root, 0, options.get(0).value);
                    if (node != null) {
                        read(node, 2);
                    } else {
                        System.out.println("Nie znaleziono takiego działu");
                    }
                }
                break;
                case Chapter: {
                    Node node = search(root, 1, options.get(0).value);
                    if (node != null) {
                        read(node, 6);
                    } else {
                        System.out.println("Nie znaleziono takiego rozdziału");
                    }
                }
                break;
                case Article: {
                    Node node = search(root, 3, options.get(0).value);
                    if (node != null) {
                        read(node, 6);
                    } else {
                        System.out.println("Nie znaleziono takiego artykułu");
                    }
                }
                break;
                case ArticleRange: {
                    String[] range = options.get(0).value.split("\\s");
                    String start = range[0];
                    String end = range[1];
                    Node startNode = search(root, 3, start);
                    Node endNode = search(root, 3, end);
                    if (startNode != null && endNode != null) {
                        readRange(root, startNode, endNode);
                    } else {
                        System.out.println("Nie znaleziono początku lub końca zakresu artykułów");
                    }
                }
                break;
            }
        } else {
            String article = null;
            String paragraph = null;
            String point = null;
            String letter = null;
            String section = null;
            String chapter = null;
            for (Option option : options) {
                switch (option.optionType) {
                    case Article:
                        article = option.value;
                        break;
                    case Paragraph:
                        paragraph = option.value;
                        break;
                    case Point:
                        point = option.value;
                        break;
                    case Letter:
                        letter = option.value;
                        break;
                    case Section:
                        section = option.value;
                        break;
                    case Chapter:
                        chapter = option.value;
                        break;
                }
            }
            if (section != null && chapter != null) {
                Node sectionNode = search(root, 0, section);
                Node chapterNode = search(sectionNode, 1, chapter);
                if (chapterNode != null) {
                    read(chapterNode, 6);
                } else {
                    System.out.println("Nie znaleziono takiego rozdziału");
                }
            } else {
                Node articleNode = search(root, 3, article);
                Node paragraphNode = null;
                Node pointNode = null;
                Node letterNode = null;
                if (paragraph != null) {
                    paragraphNode = search(articleNode, 4, paragraph);
                }
                if (point != null) {
                    if (paragraphNode != null) {
                        pointNode = search(paragraphNode, 5, point);
                    } else {
                        pointNode = search(articleNode, 5, point);
                    }
                }
                if (letter != null) {
                    if (pointNode != null) {
                        letterNode = search(pointNode, 6, letter);
                    } else if (paragraphNode != null) {
                        letterNode = search(paragraphNode, 6, letter);
                    } else {
                        letterNode = search(articleNode, 6, letter);
                    }
                }
                if (letter != null) {
                    if (letterNode != null) {
                        read(letterNode, 6);
                    } else {
                        System.out.println("Nie znaleziono takiej litery");
                    }
                } else if (point != null) {
                    if (pointNode != null) {
                        read(pointNode, 6);
                    } else {
                        System.out.println("Nie znaleziono takiego punktu");
                    }
                } else if (paragraph != null) {
                    if (paragraphNode != null) {
                        read(paragraphNode, 6);
                    } else {
                        System.out.println("Nie znaleziono takiego ustępu");
                    }
                }
            }
        }
    }
    private boolean started = false;
    private void readRange(Node node, Node startNode, Node endNode) {
        if (node != null) {
            if (node == startNode) {
                started = true;
            }
            if (started && node.getLevel() > 2) {
                System.out.println(node.toString());
            }
            if (node == endNode) {
                started = false;
            }
            for (Node subNode : node.getSubNodes()) {
                readRange(subNode, startNode, endNode);
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
                for (Node subNode : node.getSubNodes()) {
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
