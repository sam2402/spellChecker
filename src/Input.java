import java.util.Scanner;

public class Input {

    public String getString(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

}
