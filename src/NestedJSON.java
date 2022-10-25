import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

class NestedJSON {



    // video:
// https://www.youtube.com/watch?v=HSuVtkdej8Q&t=377s


    public void execute() throws IOException {
        ParseJSON2();
    }

    public void ParseJSON2(){
        File input = new File ("22.07.06-WizardAssassin\\resources/locations.json");
        System.out.println("loaded json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Process all locations
            JsonArray jsonArrayOfLocs = fileObject.get("Locations").getAsJsonArray();
            List<Locations> locz = new ArrayList<>();

            for (JsonElement locElement : jsonArrayOfLocs) {
                JsonObject locJsonObject = locElement.getAsJsonObject();

                String name = locJsonObject.get("name").getAsString();
                String description = locJsonObject.get("description").getAsString();

                Locations Loc = new Locations(name, description);
                locz.add(Loc);
                System.out.println(Loc);

            }
            System.out.println("Array of 2 locs: " + locz);
            System.out.println("get loc 1 of array: " + locz.get(1));


            System.out.println("*************** FROM ARRAY ***************");
            Locations c = locz.get(1);
            System.out.println("Pull the 2nd location in array: " + c.getName());
            System.out.println("the 2nd location description in array: " + c.getDescription());



        } catch (FileNotFoundException e) {
            System.err.println("Error input file not found!");
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Error processing input file!");
            e.printStackTrace();
        }
    }



}