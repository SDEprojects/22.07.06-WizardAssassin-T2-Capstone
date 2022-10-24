import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws IOException {
        Game app = new Game();
        app.execute();

    }
}