import java.io.IOException;
import java.util.ArrayList;

public class Start {
    public static void main(String[] args) {
        OptionsParser optionsParser = new OptionsParser();
        ArrayList<Option> options = optionsParser.parseOptions(args);
        OptionsChecker checker = new OptionsChecker();
        String fileName = checker.CheckOptions(options);
        if (fileName == null) {
            return;
        }
        Parser parser = new Parser();
        Node main;
        try {
            main = parser.openFile(fileName);
        } catch (IOException e) {
            System.out.println("Błąd otwarcia pliku");
            return;
        }
        Printer printer = new Printer();
        printer.printText(main, options);
    }
}
