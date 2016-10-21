package com.buvi;

import java.io.*;
import java.util.*;

public class Sortable {

    private static final String FILE_NAME = System.getProperty("user.dir")+"/listings.txt";
    private static RandomAccessFile randomAccessFile = null;
    private static final Map<Integer, Long> LINE_NUMBER_BYTE_POSITION = new HashMap<>();
    private static final Map<String, List<Integer>> MANUFACTURERS = new HashMap<>();
    private static final Map<String, List<Integer>> MODELS = new HashMap<>();
    private static final Map<String, List<Integer>> FAMILY = new HashMap<>();


    public static void main(String[] args) {

        try {
            randomAccessFile = new RandomAccessFile(new File(FILE_NAME), "r");
            buildProductDetails(System.getProperty("user.dir")+"/products.txt");
            String line;
            int lineNumber = 1;
            long bytePosition = 0l;
            while ((line= randomAccessFile.readLine()) != null){
                feedMaps(line, lineNumber);
                LINE_NUMBER_BYTE_POSITION.put(lineNumber, bytePosition);
                bytePosition = bytePosition + line.length()+1;
                lineNumber++;
            }

            Set<Integer> lines = getResultForString(args[0]);
            writeResultsToFile(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeResultsToFile(Set<Integer> set){
        try (PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "/result.txt")) {
            for(Integer i : set){
                writer.println(getLineContent(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Integer> getResultForString(String query) {
        Set<Integer> result = new HashSet<>();
        List<Integer> manufacturerList;
        List<Integer> modelsList;
        List<Integer> familyList;
        for (String string : query.split(" ")) {
            String key = string.toLowerCase();
            if (MANUFACTURERS.containsKey(key)) {
                manufacturerList = MANUFACTURERS.get(key);
                result.addAll(manufacturerList);
            } else if (MODELS.containsKey(key)) {
                modelsList = MODELS.get(key);
                result.addAll(modelsList);
            } else if (FAMILY.containsKey(key)) {
                familyList = FAMILY.get(key);
                result.addAll(familyList);
            }
        }
        return result;
    }


    private static void feedMaps(String line, int lineNumber) {
        String[] content = line.split(",");
        for (int i = 0; i < content.length ; i++) {
            if (content[i].contains("title")){
                String titleContents = content[i].split(":")[1];
                String[] keyWords = titleContents.split(" ");
                for (int j = 0; j < keyWords.length; j++) {
                    String key = keyWords[j].replaceAll("\"", "").toLowerCase();
                    if (MANUFACTURERS.containsKey(key)){
                        MANUFACTURERS.get(key).add(lineNumber);
                    } else if (MODELS.containsKey(key)){
                        MODELS.get(key).add(lineNumber);
                    } else if (FAMILY.containsKey(key)){
                        FAMILY.get(key).add(lineNumber);
                    }
                }
                break;
            }

        }
    }

    private static String getLineContent(int lineNumber) throws IOException{
            randomAccessFile.seek(LINE_NUMBER_BYTE_POSITION.get(lineNumber));
            return randomAccessFile.readLine();
    }

    private static void buildProductDetails(String productList) {
        try (RandomAccessFile productFile = new RandomAccessFile(new File(productList), "r")) {
            String line;
            while ((line=productFile.readLine())!=null){
                String[] content = line.split(",");
                for (int i = 0; i < content.length; i++) {
                    String cont =content[i];
                    if (cont.contains("manufacturer")){
                        String[] manufacturer = cont.split(":");
                        String manufacturerName = manufacturer[1].replaceAll("\"","").toLowerCase();
                        if (!MANUFACTURERS.containsKey(manufacturerName)) {
                            MANUFACTURERS.put(manufacturerName, new ArrayList<Integer>());
                        }
                    } else if (cont.contains("model")){
                        String[] models = cont.split(":");
                        String modelName = models[1].replaceAll(" ","").replaceAll("\"","").toLowerCase();
                        if (! MODELS.containsKey(modelName)) {
                            MODELS.put(modelName, new ArrayList<Integer>());
                        }
                    } else if (cont.contains("family")){
                        String[] family = cont.split(":");
                        String familyName = family[1].replaceAll("\"","").toLowerCase();
                        if (! FAMILY.containsKey(familyName))
                        FAMILY.put(familyName, new ArrayList<Integer>());
                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
