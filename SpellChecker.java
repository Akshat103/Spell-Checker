import java.util.ArrayList;
import java.util.List;

public class SpellChecker {
    private List<String> dictionary;
    private boolean caseSensitive;
    private List<String> customDictionary;

    public SpellChecker(List<String> dictionary, boolean caseSensitive) {
        this.dictionary = dictionary;
        this.caseSensitive = caseSensitive;
        this.customDictionary = new ArrayList<>();
    }

    public void addWordToCustomDictionary(String word) {
        customDictionary.add(word.toLowerCase());
    }

    public boolean isSpellingCorrect(String word) {
        if (caseSensitive) {
            return dictionary.contains(word.toLowerCase()) || customDictionary.contains(word.toLowerCase());
        } else {
            return dictionary.stream().anyMatch(dictWord -> dictWord.equalsIgnoreCase(word));
        }
    }

    public List<String> getSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();

        for (String dictWord : dictionary) {
            if (calculateLevenshteinDistance(word, dictWord) <= 2) {
                suggestions.add(dictWord);
            }
        }

        return suggestions;
    }

    private int calculateLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1] + costOfSubstitution(s1.charAt(i - 1), s2.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }
}
