import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
// video:
// https://www.youtube.com/watch?v=HSuVtkdej8Q&t=377s
class ParseJSON {

    public void execute() throws IOException {
        ParseJSON();
    }

    public void ParseJSON(){
        File input = new File ("22.07.06-WizardAssassin\\resources/locationsSimple.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Extract
            String loc = fileObject.get("Name").getAsString();
            String desc = fileObject.get("Description").getAsString();
            System.out.println("Name/location: ");
            System.out.println(loc);
            System.out.println("Description: ");
            System.out.println(desc);
            System.out.println("***********");

        }
        catch (FileNotFoundException e) {
            System.err.println("Error input file not found!");
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Error processing input file!");
            e.printStackTrace();
        }
    }
}