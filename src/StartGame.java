import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class StartGame {

    public void execute() throws IOException {
        title();
    }
    private void title() throws IOException {

        System.out.println();
        System.out.println("\033[35m" + Files.readString(Path.of("22.07.06-WizardAssassin\\resources/welcome.txt")) + "\033[0m");

        System.out.println();
        System.out.println("Wizard Assassin is a single-player game in which the objective is to defeat the evil wizard " +
                "and save the king.\nThe player needs to explore different rooms, collect items until it reach the evil wizard.");
        System.out.println();

    }
}