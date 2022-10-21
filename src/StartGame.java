import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class StartGame {

    public Scanner inputScanner = new Scanner(System.in);

    public void execute() throws IOException {
        title();
        beginGame();
    }
    private void title() throws IOException {

        System.out.println();
        System.out.println("\033[35m" + Files.readString(Path.of("22.07.06-WizardAssassin\\resources/welcome.txt")) + "\033[0m");

        System.out.println();
        System.out.println("Wizard Assassin is a single-player game in which the objective is to defeat the evil wizard " +
                "and save the king in order to win.\nThe player needs to explore different rooms in the castle, collect items until it reach the evil wizard.");
        System.out.println();

    }

    private void beginGame(){
        String start;

        System.out.println("Do you want to start the game? yes/no");
        start = inputScanner.nextLine().trim();
        if (start.equals("yes")) {
            System.out.println("You have started the game");

        }else if (start.equals("no")){
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("please enter 'yes' to continue or 'no' to quit the game");
            beginGame();
        }
    }
}