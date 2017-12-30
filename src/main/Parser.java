import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public void openFile(String fileName, ArrayList<Option> options) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null && !line.matches("Rozdział.*|DZIAŁ.*|Art.*")) {
                line = br.readLine(); // pomija wszystko do momentu trafienia na pierwszy dział, rozdział lub artykuł
            }
            while (line != null) {
                if (line.matches("©Kancelaria Sejmu.*")){
                    br.readLine();
                    line = br.readLine();
                    continue;
                }
                while (line.matches(".*-$")) {
                    line = line.substring(0, line.length() - 1) + br.readLine();
                }
                if (line.matches("Art\\. .*")) {

                }
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            System.out.print(everything);
        }
    }
}
