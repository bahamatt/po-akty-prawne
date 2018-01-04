import java.io.IOException;
import java.util.ArrayList;

public class OptionsChecker {
    public void CheckOptions(ArrayList<Option> options) {
        boolean file = false;
        String fileName = null;
        for (Option option : options) {
            if (option.optionType == OptionType.Unknown) {
                System.out.println(OptionsParser.badOptions);
                System.out.print(OptionsParser.help);
                return;
            }
            if (option.optionType == OptionType.Help) {
                System.out.print(OptionsParser.help);
                return;
            }
            if (option.optionType == OptionType.FileName) {
                if (file) {
                    System.out.println("Podano kilka plików do wczytania");
                    return;
                } else {
                    file = true;
                    fileName = option.value;
                }
                options.remove(option);
            }
        }
        if (!file) {
            System.out.println("Nie podano pliku do wczytania");
            System.out.print(OptionsParser.help);
            return;
        }
        if (options.isEmpty()) {
            System.out.println("Nie podano elementu do wyświetlenia");
            System.out.print(OptionsParser.help);
            return;
        } else if (options.size() == 1) {
            OptionType optionType = options.get(0).optionType;
            if (!(optionType == OptionType.Article || optionType == OptionType.ArticleRange ||
                    optionType == OptionType.Section || optionType == OptionType.ToC ||
                    optionType == OptionType.Chapter)) {
                System.out.println(OptionsParser.badOptions);
                System.out.print(OptionsParser.help);
                return;
            }
        } else {
            // TODO: Zmienić sprawdzanie opcji bo punkt nie musi być pod ustępem
            boolean article = false;
            boolean paragraph = false;
            boolean point = false;
            boolean letter = false;
            for (Option option: options) {
                switch (option.optionType){
                    case Article:
                        article = true;
                        break;
                    case Paragraph:
                        paragraph = true;
                        break;
                    case Point:
                        point = true;
                        break;
                    case Letter:
                        letter = true;
                        break;
                    default:
                        System.out.println(OptionsParser.badOptions);
                        System.out.print(OptionsParser.help);
                        return;
                }
            }
            if (!article || !paragraph || (options.size() > 2 && !point) || (options.size() == 4 && !letter)) {
                System.out.println(OptionsParser.badOptions);
                System.out.print(OptionsParser.help);
                return;
            }
        }
        Parser parser = new Parser();
        try {
            parser.openFile(fileName, options);
        } catch (IOException e) {
            System.out.println("Błąd otwarcia pliku");
        }
    }
}
