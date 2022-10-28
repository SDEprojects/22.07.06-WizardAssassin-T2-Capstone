import com.google.gson.Gson;

//import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

class readJSON {

    public Data obj;

    public  Data  JSONFile() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/Location.json"));
        // https://stackoverflow.com/questions/19169754/parsing-nested-json-data-using-gson
        Data masterObj = gson.fromJson(reader, Data.class);
       // Location currentLocation = MasterObj.getLocations().get(0);

//        System.out.println("You see these items: " + Arrays.toString(currentLocation.getItem()));
//        System.out.println("From the " + currentLocation.getName() + " you can go to the:");
//        for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
//            System.out.println("     " + direction.getKey() + ": " + direction.getValue());

//        System.out.println(MasterObj);
        //return currentLocation;
        return masterObj;
    }


    List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Location getPickedLocation(String userInput){
        for (Location location : getLocations())
            if (location.getName().equals(userInput)) {
                return location;
            }
        return null; // ThisLocationNotFoundException()
    }

}



// https://stackoverflow.com/questions/19169754/parsing-nested-json-data-using-gson
class Data {
    List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Location getPickedLocation(String userInput){
        for (Location location : getLocations())
            if (location.getName().equals(userInput)) {
                return location;
            }
        return null; // ThisLocationNotFoundException()
    }
}
class Location {

    public String name;
    public String description;
    Map<String, String> directions;
    public String [] items;

    public Location(String name, String description, Map<String, String> directions, String[] items) {
        this.name = name;
        this.description = description;
        this.directions = directions;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public void setDirections(Map<String, String> directions) {
        this.directions = directions;
    }

    public String[] getItem() {
        return items;
    }

    public void setItem(String[] item) {
        this.items = item;
    }
}
