package com.wizard_assassin;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

//TODO: 1, need "" for welcome.txt; 2, I only use sout to test, I haven't modified other class yet.
class FileReading {
    JSONParser parser = new JSONParser();

    static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = FileReading.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("Can not read the file! " + fileName);
        } else {
            return inputStream;
        }
    }

    public String dataReader(String filename) {
        String data = null;
        try {
            InputStream fileData = FileReading.getFileFromResourceAsStream(filename);
            String result = new BufferedReader(new InputStreamReader(fileData))
                    .lines().collect(Collectors.joining("\n"));
            Object obj = parser.parse(result);
            data = obj.toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

//    public static void main(String[] args) {
//        FileReading fileReading = new FileReading();
//
//        String charactersString = fileReading.dataReader("characters.json");
//        System.out.println(charactersString);
//
//        String introductionString = fileReading.dataReader("introduction.json");
//        System.out.println(introductionString);
//
//        String itemsString= fileReading.dataReader("Items.json");
//        System.out.println(itemsString);
//
//        String locationString = fileReading.dataReader("Location.json");
//        System.out.println(locationString);
//
//        String locationsString = fileReading.dataReader("Locations.json");
//        System.out.println(locationsString);
//
//        String welcomeString = fileReading.dataReader("welcome.txt");
//        System.out.println(welcomeString);
//
//    }

}