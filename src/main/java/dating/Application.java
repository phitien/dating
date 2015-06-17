package dating;

import java.io.Console;

public class Application {

	public static void main(String[] args) {
		Console console = System.console();
        if (console == null) {
            System.out.println("Unable to fetch console");
            return;
        }
		System.out.println("Dating service started!");
        String line = console.readLine();
        console.printf("I saw this line: %s", line);
	}

}
