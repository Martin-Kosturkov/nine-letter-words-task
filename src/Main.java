import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        var numberOfLettersToWords = WordsProvider.fetchAllWordsShorterThanTenLetters();
        var startSearchingForWords = System.currentTimeMillis();

        var result = numberOfLettersToWords.get(9).stream()
                .filter(nineLetterWord -> isStillWordAfterRemovingLetters(nineLetterWord, numberOfLettersToWords))
                .toList();

        System.out.printf("Finished searching for words in %sms%n", System.currentTimeMillis() - startSearchingForWords);
        System.out.printf("Result words: %s, %s%n", result.size(), result);
    }

    private static boolean isStillWordAfterRemovingLetters(
            String word,
            Map<Integer, Set<String>> numberOfLettersToWords) {

        if (word.length() == 1 && numberOfLettersToWords.get(1).contains(word)) {
            return true;
        } else if (word.length() == 1) {
            return false;
        }

        // Removing letters starting from the last one
        for (var i = word.length() - 1; i >= 0; i--) {
            var newWord = removeLetterFromWord(word, i);

            if (numberOfLettersToWords.get(newWord.length()).contains(newWord)) {
                return isStillWordAfterRemovingLetters(newWord, numberOfLettersToWords);
            }
        }

//        Removing letters starting from the first one.
//        for (var i = 0; i < word.length(); i++) {
//            var newWord = removeLetterFromWord(word, i);
//
//            if (numberOfLettersToWords.get(newWord.length()).contains(newWord)) {
//                return isStillWordAfterRemovingLetters(newWord, numberOfLettersToWords);
//            }
//        }

        return false;
    }

    private static String removeLetterFromWord(String word, int letterIndex) {
        return new StringBuilder(word)
                .deleteCharAt(letterIndex)
                .toString();
    }
}