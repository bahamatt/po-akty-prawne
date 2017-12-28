import java.util.ArrayList;

public class OptionsChecker {
    public int CheckOptions(ArrayList<Option> options) {
        boolean file = false;
        for (Option option : options) {
            if (option.optionType == OptionType.Unknown) {
                System.out.println(OptionsParser.badOptions);
                System.out.print(OptionsParser.help);
                return 1;
            }
            if (option.optionType == OptionType.Help) {
                System.out.print(OptionsParser.help);
                return 2;
            }
            if (option.optionType == OptionType.FileName) {
                file = true;
            }
        }
        if (!file) {
            System.out.println("Nie podano pliku do wczytania");
            System.out.print(OptionsParser.help);
            return 1;
        }
        return 0;
    }
}
