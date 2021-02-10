import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// A utility class that allows the  caller to access various custom
// properties of a file given a file name
public class UserFile {

    private final File file;
    private final Scanner scanner;

    public UserFile(String filename) throws FileNotFoundException {
        this.file = new File(filename);
        if (file.exists()) {
            this.scanner = new Scanner(file);
        } else {
            throw new FileNotFoundException("Can't find file " + filename);
        }
    }

    public String getString() {
        String string = scanner.useDelimiter("\\A").next();
        scanner.close();
        return string;
    }

    public StringArray getWords(char separator) {
        scanner.useDelimiter("\\s*"+separator+"\\s*");
        StringArray stringArray = new StringArray();
        while (scanner.hasNext()) {
            String data = scanner.next();
            stringArray.add(data.toLowerCase());
        }
        return stringArray;
    }

    public String getFileName() {
        return file.getName();
    }

}
