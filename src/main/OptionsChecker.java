import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class OptionsChecker {
    public String CheckOptions(ArrayList<Option> options) {
        boolean file = false;
        String fileName = null;
        Iterator<Option> iter = options.iterator();
        while (iter.hasNext()) {
            Option option = iter.next();
            if (option.optionType == OptionType.Unknown) {
                System.out.println(OptionsParser.badOptions);
                System.out.print(OptionsParser.help);
                return null;
            }
            if (option.optionType == OptionType.Help) {
                System.out.print(OptionsParser.help);
                return null;
            }
            if (option.optionType == OptionType.FileName) {
                if (file) {
                    System.out.println("Podano kilka plików do wczytania");
                    return null;
                } else {
                    file = true;
                    fileName = option.value;
                }
                iter.remove();
            }
        }
        if (!file) {
            System.out.println("Nie podano pliku do wczytania");
            System.out.print(OptionsParser.help);
            return null;
        }
        if (options.isEmpty()) {
            System.out.println("Nie podano elementu do wyświetlenia");
            System.out.print(OptionsParser.help);
            return null;
        } else if (options.size() == 1) {
            OptionType optionType = options.get(0).optionType;
            if (!(optionType == OptionType.Article || optionType == OptionType.ArticleRange ||
                    optionType == OptionType.Section || optionType == OptionType.ToC ||
                    optionType == OptionType.Chapter)) {
                System.out.println(OptionsParser.badOptions);
                System.out.print(OptionsParser.help);
                return null;
            }
        } else {
            boolean article = false;
            boolean section = false;
            boolean chapter = false;
            for (Option option: options) {
                switch (option.optionType){
                    case Article:
                        article = true;
                        break;
                    case Paragraph:
                    case Point:
                    case Letter:
                        break;
                    case Section:
                        section = true;
                        break;
                    case Chapter:
                        chapter = true;
                        break;
                    default:
                        System.out.println(OptionsParser.badOptions);
                        System.out.print(OptionsParser.help);
                        return null;
                }
            }
            if (!article) {
                if (!section || !chapter) {
                    System.out.println(OptionsParser.badOptions);
                    System.out.print(OptionsParser.help);
                    return null;
                }
            }
        }
        return fileName;
    }
}
