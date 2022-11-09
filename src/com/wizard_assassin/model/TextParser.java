package com.wizard_assassin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class TextParser {

    private ArrayList<String> synonymList;

    public TextParser() {
    }


    public ArrayList<String> textParser(String input) {

        generateSynonymList();

        ArrayList<String> result = new ArrayList<>(List.of("verb", "noun"));
        // split string by no space
        String[] strSplit = input.split(" ");

        // Now convert string into ArrayList
        ArrayList<String> words = new ArrayList<>(Arrays.asList(strSplit));
        ArrayList<String> synonyms = getSynonymList();

        ObjectMapper mapper = new ObjectMapper();

        try {
            Vocabulary vocabulary = mapper.readValue(getClass().getClassLoader().getResource("textParse.json"), Vocabulary.class);

            List<String> verbs = vocabulary.getVerbs();
            for (String word : words) {
                if (verbs.contains(word)) {
                    if (synonyms.contains(word)) {
                        result.set(0, synonym(word));
                    } else {
                        result.set(0, word);
                    }
                }
            }

            List<String> nouns = vocabulary.getNouns();
            for (String word : words) {
                if (nouns.contains(word)) {
                    if (synonyms.contains(word)) {
                        result.set(1, synonym(word));
                    } else {
                        result.set(1, word);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String synonym(String word) {
        String newWord = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            Synonyms synonymJson = mapper.readValue(getClass().getClassLoader().getResource("synonym.json"), Synonyms.class);

            Map<String,String> synMap = synonymJson.getSynonyms();

            if (synMap.containsKey(word)){
                newWord = synMap.get(word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newWord;
    }

    public void generateSynonymList() {

        ArrayList<String> synonyms = new ArrayList<>(0);
        ObjectMapper mapper = new ObjectMapper();

        try {
            Synonyms synonymJson = mapper.readValue(getClass().getClassLoader().getResource("synonym.json"), Synonyms.class);

            Map<String,String> synMap = synonymJson.getSynonyms();

            synMap.forEach((k,v) -> synonyms.add(k));

            setSynonymList(synonyms);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getSynonymList() {
        return synonymList;
    }

    public void setSynonymList(ArrayList<String> synonymList) {
        this.synonymList = synonymList;
    }

    private static class Synonyms{
        @JsonProperty("synonyms")
        private Map<String,String> synonyms = new HashMap<>();

        public Map<String, String> getSynonyms() {
            return synonyms;
        }

        public void setSynonyms(Map<String, String> synonyms) {
            this.synonyms = synonyms;
        }
    }

    private static class Vocabulary {
        @JsonProperty("verb")
        private List<String> verbs;
        @JsonProperty("noun")
        private List<String> nouns;

        public List<String> getVerbs() {
            return verbs;
        }

        public void setVerbs(List<String> verbs) {
            this.verbs = verbs;
        }

        public List<String> getNouns() {
            return nouns;
        }

        public void setNouns(List<String> nouns) {
            this.nouns = nouns;
        }
    }

}