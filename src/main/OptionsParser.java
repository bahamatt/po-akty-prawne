import java.io.IOException;
import java.util.ArrayList;

public class OptionsParser {

    public static final String badOptions = "Podano błędne opcje";
    public static final String help = "-f file    nazwa pliku do wczytania\n" +
            "-a n       wyświetla artykuł\n" +
            "-az m n    wyświetla artykuły z zakresu\n" +
            "-u n       wyświetla ustęp\n" +
            "-p n       wyświetla punkt\n" +
            "-l n       wyświetla literę\n" +
            "-r n       wyświetla rozdział\n" +
            "-rd n      wybiera dział z którego wyświetlić rozdział, używany tylko razem z -r\n" +
            "-s         wyświetla spis treści\n" +
            "-d n       wyświetla spis treści działu\n" +
            "-h         wyświetla ten tekst\n";

    public ArrayList<Option> parseOptions(String[] args) {
        ArrayList<Option> options = new ArrayList<>();
        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-f":
                        options.add(new Option(OptionType.FileName, args[i + 1]));
                        i++;
                        break;
                    case "-a":
                        options.add(new Option(OptionType.Article, args[i + 1]));
                        i++;
                        break;
                    case "-az":
                        options.add(new Option(OptionType.ArticleRange, args[i + 1] + " " + args[i + 2]));
                        i += 2;
                        break;
                    case "-u":
                        options.add(new Option(OptionType.Paragraph, args[i + 1]));
                        i++;
                        break;
                    case "-p":
                        options.add(new Option(OptionType.Point, args[i + 1]));
                        i++;
                        break;
                    case "-l":
                        options.add(new Option(OptionType.Letter, args[i + 1]));
                        i++;
                        break;
                    case "-r":
                        options.add(new Option(OptionType.Chapter, args[i + 1]));
                        i++;
                        break;
                    case "-rd":
                        options.add(new Option(OptionType.Section, args[i + 1]));
                        i++;
                        break;
                    case "-s":
                        options.add(new Option(OptionType.ToC, ""));
                        break;
                    case "-d":
                        options.add(new Option(OptionType.Section, args[i + 1]));
                        i++;
                        break;
                    case "-h":
                        options.add(new Option(OptionType.Help, ""));
                        break;
                    default:
                        options.add(new Option(OptionType.Unknown, ""));
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(badOptions);
            System.out.print(help);
            return null;
        }
        return options;
    }
}
