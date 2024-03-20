import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WordsProvider {
    private static final String WORDS_LOCATION_URL =
            "https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt";

    static Map<Integer, Set<String>> fetchAllWordsShorterThanTenLetters() {
        var wordsUrl = getWordsUrl();
        var startReadingWords = System.currentTimeMillis();

        try (var reader = new BufferedReader(new InputStreamReader(wordsUrl.openStream()))) {

            var numberOfLettersToWords = reader.lines()
                    .skip(2)
                    .filter(word -> word.length() <= 9)
                    .collect(Collectors.groupingBy(
                            String::length,
                            Collectors.toSet()));

            numberOfLettersToWords.put(1, Set.of("I", "A"));
            return numberOfLettersToWords;

        } catch (IOException e) {
            var errorMessage = "Unexpected error while reading from url: %s, %s"
                    .formatted(wordsUrl, e.getMessage());

            throw new RuntimeException(errorMessage, e);
        } finally {
            System.out.printf("Read all words in %sms%n", System.currentTimeMillis() - startReadingWords);
        }
    }

    private static URL getWordsUrl() {
        try {
            return URL.of(
                    URI.create(WORDS_LOCATION_URL),
                    null);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
