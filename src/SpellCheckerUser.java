import java.io.FileNotFoundException;

public class SpellCheckerUser {

    Input input = new Input();
    SpellChecker spellChecker = new SpellChecker();

    // Called from the Main class and repeatedly runs the program until termination
    public void go() {
        StringArray validEntries = new StringArray("1", "2", "quit");
        while (true) {
            displayEntryOptions();
            String entry = input.getString("Enter '1' or '2', or 'quit' to close the program: ");
            if (entry.equalsIgnoreCase("quit")) { // Checks if the user wants to quit the program
                System.out.println("Thank you for using Spell Checker.");
                break;
            }
            if (validateStringInput(entry, validEntries)) { // Runs the user entered option if valid
                doOption(entry.toLowerCase());
            } else {
                System.out.println("Please enter '1', '2' or 'quit' ");
            }
        }
    }

    private void displayEntryOptions() {
        System.out.println("Would you like to spell check your text via:");
        System.out.println("1. Keyboard entry");
        System.out.println("2. A text file");
    }

    // Checks in an inputted string is in a StringArray of valid inputs
    private boolean validateStringInput(String input, StringArray validInputs) {
        return validInputs.contains(input);
    }

    // Returns a string of values of the given StringArray separated by commas
    private String removeBrackets(StringArray stringArray) {
        return stringArray.toString().replaceAll("[\\[\\]]", "");
    }

    // Gets the user's text and outputs misspelled words
    private void spellCheckString() {
        String text = input.getString("Enter the text you want to spell check: ");
        outputMisspelledWords(text);
    }

    // Gets the user's text from a file given a file name, and outputs misspelled words
    private void spellCheckFile() {
        String filename = input.getString("Enter the name of the file whose text you want to spell check: ");
        try {
            UserFile userFile = new UserFile(filename);
            outputMisspelledWords(userFile.getString());
        } catch (FileNotFoundException err) {
            System.out.println("\n" + filename + " does not exist\n");
        }
    }

    private void outputMisspelledWords(String text) {
        spellChecker.setText(text);
        StringArray misspelledWords = spellChecker.getMisspelledWords();
        if (misspelledWords.size() > 0 && !text.equals("")) {
            System.out.println("\nThe misspelled words are:");
            System.out.println(removeBrackets(misspelledWords)+"\n");
        } else {
            System.out.println("\nThe text did not contain any misspelled words.\n");
        }
    }

    private void doOption(String option) {
        switch (option) {
            case "1" -> spellCheckString();
            case "2" -> spellCheckFile();
        }
    }
}
