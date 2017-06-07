package com.wallethub.phrases;

import java.io.IOException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class for get the top x most frequent phrases
 *
 * @author Adrián Martín Sánchez
 */
public class TopPhrases extends Thread {

    private String fileName;
    private String path;
    private String delimiter;
    private final String fileNameTmp = "TMP";
    private final String pathTmp = "TMP/";
    private final String extension = ".txt";
    private static int numTmpFile = 0;
    private final Logger logger = Logger.getLogger(TopPhrases.class.getName());

    // Constructor
    public TopPhrases(String path, String fileName, String delimiter) {
        this.path = path;
        this.fileName = fileName;
        this.delimiter = delimiter;
    }

    /**
     * Get a map with the top (topNumber) most frequent phrases.
     *
     * @param topNumber Number of most frequent phrases
     * @return A map with most frequent phrases
     */
    public Map<String, Integer> getTopPhrases(int topNumber) {

        prepareTmpDir();
        generateTemporalFiles();

        return getMergeMaps(topNumber);
    }

    /**
     * Clear temporary directory
     *
     */
    private void prepareTmpDir() {
        File tmpDir = null;
        try {
            tmpDir = new File(path.concat(pathTmp));
            String files[] = tmpDir.list();
            if (files != null) {
                for (String file : files) {
                    Files.delete(new File(tmpDir, file).toPath());
                }
            }
            Files.deleteIfExists(tmpDir.toPath());
            tmpDir.mkdir();
        } catch (IOException e) {
            final String error = String.format("Error deleting folder: %s", tmpDir.getPath());
            logger.severe(error);
        }
    }

    /**
     * Get a map with the top (topNumber) most frequent phrases.
     *
     * @param topNumber Number of most frequent phrases
     * @return A map with most frequent phrases
     */
    private Map<String, Integer> getMergeMaps(int topNumber) {
        List<Map<String, Integer>> maps = getMapsFromTemporalFiles(getTemporalFiles());

        Map<String, Integer> mergeMap = maps.stream()
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topNumber)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b) -> a, LinkedHashMap::new));

        return mergeMap;
    }

    /**
     * Get a list of maps with the temporary maps.
     *
     * @param files List of files where temporary maps was saved
     * @return List of temporary maps
     */
    private List<Map<String, Integer>> getMapsFromTemporalFiles(List<File> files) {
        List<Map<String, Integer>> maps = new ArrayList<>();
        ObjectInputStream in = null;

        try {
            for (File file : files) {
                in = new ObjectInputStream(new FileInputStream(file));
                maps.add((Map<String, Integer>)in.readObject());
            }
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            final String error = "Error creating file";
            logger.severe(error);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                final String error = "Error closing input stream";
                logger.severe(error);
            }
        }
        return maps;
    }

    /**
     * Get a list of files with the temporary maps.
     *
     * @return List of temporary files
     */
    private List<File> getTemporalFiles() {
        List<File> files = new ArrayList<>();

        File tmpDir = new File(path.concat(pathTmp));
        String filesName[] = tmpDir.list();
        for (String file: filesName) {
            files.add(new File(tmpDir,file));
        }
        return files;
    }

    /**
     * Generate temporal files with temporal maps.
     *
     */
    private void generateTemporalFiles() {

        BufferedReader br = null;
        FileReader fr = null;
        String fileNameComplete = path.concat(fileName);

        try {

            InputStream is      = new FileInputStream(fileNameComplete);
            byte[] b            = new byte[1024*1024]; // Read only 1MG for avoid java heap space
            String lastPhrase   = "";
            String line;

            int read;
            while((read = is.read(b)) != -1){
                // Concat last phrase to actual line
                line = lastPhrase.concat(new String(b));
                // Save the last phrase, may not be completed
                lastPhrase = line.substring(line.lastIndexOf(delimiter) + 1);
                processLine(line);
            }
        } catch (IOException ioe){
            final String error = String.format("Error opening file: %s", fileNameComplete);
            logger.severe(error);
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {
                final String error = "Error closing reader";
                logger.severe(error);
            }
        }
    }

    /**
     * Process each input file line.
     *
     * @param line String with the line value
     */
    private void processLine(String line) {

        Map<String, Integer> topPhrases = new HashMap<>();
        String lineSplit[] = line.split(delimiter);
        for (String phrase: lineSplit) {
            phrase = phrase.trim();
            if (!phrase.isEmpty()) {
                if (topPhrases.containsKey(phrase)) {
                    topPhrases.put(phrase, topPhrases.get(phrase).intValue() + 1);
                } else {
                    try {
                        topPhrases.put(phrase, 1);
                    } catch (OutOfMemoryError E) {
                        // Save actual hashmap into file and clear
                        saveTemporalFile(topPhrases);
                        topPhrases.clear();
                        topPhrases.put(phrase, 1);
                    }
                }
            }
        }
        saveTemporalFile(topPhrases);
    }

    /**
     * Process each input file line.
     *
     * @param topPhrases Temporary map with the most frequent phrases
     */
    private void saveTemporalFile(Map<String, Integer> topPhrases) {
        String fileTemporalName = path.concat(pathTmp).concat(fileNameTmp)
                .concat(Integer.toString(numTmpFile).concat(extension));
        numTmpFile++;
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(new FileOutputStream(fileTemporalName));
            os.writeObject(topPhrases);
        } catch (IOException e) {
            final String error = String.format("Error opening file: %s", fileTemporalName);
            logger.severe(error);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                final String error = "Error closing output stream";
                logger.severe(error);
            }
        }
    }
}



