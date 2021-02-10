import java.io.FileNotFoundException;

public class SpellChecker {

    private Dictionary dictionary;
    private StringArray text;

    public SpellChecker() {
        initialiseDictionary();
    }

    public StringArray getMisspelledWords() {
        StringArray misspelledWords = new StringArray();
        for (String word: text) {
            if (!dictionary.contains(word)) {
                misspelledWords.add(word);
            }
        }
        return misspelledWords;
    }

    public void setText(String enteredText) {
        String cleanText = cleanText(enteredText);
        String[] splitText = cleanText.split("\\W+");
        text = new StringArray(splitText);
    }

    private void initialiseDictionary() {
        try {
            StringArray dictionaryStrArr = new UserFile("dictionary.txt").getWords('\n');
            this.dictionary = new Dictionary(dictionaryStrArr);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
            System.exit(1);
        }
    }

    private String cleanText(String rawText) {
        StringBuilder stringBuilder = new StringBuilder(rawText);
        for (int i = 0; i < stringBuilder.length(); i++) {
            char character = stringBuilder.charAt(i);
            if (!Character.isLetter(character) && character != ' ' && character != '\n') { //
                stringBuilder.deleteCharAt(i);
            }
        }
        return stringBuilder.toString();
    }

}
