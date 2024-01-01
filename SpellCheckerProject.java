import java.util.List;
import java.util.ArrayList;

public class SpellCheckerProject {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java SpellCheckerProject <dictionaryFilePath> <textFilePath> <caseSensitive>");
            return;
        }

        String dictionaryFilePath = args[0];
        String textFilePath = args[1];
        boolean caseSensitive = Boolean.parseBoolean(args[2]);

        List<String> dictionary = FileIO.loadWords(dictionaryFilePath);

        if (dictionary == null) {
            System.out.println("Failed to load the dictionary. Exiting.");
            return;
        }

        List<String> textContent = FileIO.loadWords(textFilePath);

        if (textContent == null) {
            System.out.println("Failed to load the text file. Exiting.");
            return;
        }

        SpellChecker spellChecker = new SpellChecker(dictionary, caseSensitive);

        // Load or update the custom dictionary (if needed)
        spellChecker.addWordToCustomDictionary("akshat");
        spellChecker.addWordToCustomDictionary("tamrakar");

        ArrayList<String> correctedText = new ArrayList<>();

        for (String word : textContent) {
            if (!spellChecker.isSpellingCorrect(word)) {
                List<String> suggestions = spellChecker.getSuggestions(word);
                if (!suggestions.isEmpty()) {
                    String correctedWord = suggestions.get(0);
                    System.out.println("Correcting: " + word + " -> " + correctedWord);
                    correctedText.add(correctedWord);
                    continue;
                }
            }
            correctedText.add(word);
        }

        FileIO.saveLines(textFilePath, correctedText);
        System.out.println("Spell checking and correction completed. Corrected text saved.");
    }
}
