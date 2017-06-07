package com.wallethub.phrases;

import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * IT for TopPhrases class
 *
 * @author Adrián Martín Sánchez
 */
public class TopPhrasesIT {

    private TopPhrases topPhrases;
    private String path             = "C:/Users/Golden/Desktop/WalletHub Test/Phrases/";
    private String fileName         = "PhrasesTest.txt";
    private String delimiter        = "\\|";
    private String resultExpected   = "{CNET=4, Microsoft   Bing=2, PGA=2}";
    private String checking         = "Checking file ";
    private int topNumber           = 3;
    private final Logger logger     = Logger.getLogger(TopPhrases.class.getName());
    private final String text       = " Foobar   Candy   |   Olympics   2012   |   PGA   |" +
            "   CNET   |   Microsoft   Bing | CNET |   CNET   |   Microsoft   Bing | CNET  |   PGA   ";

    @Before
    public void initialize() {
        topPhrases = new TopPhrases(path, fileName, delimiter);
        PrintStream os = null;
       try {
            Files.deleteIfExists(Paths.get(path.concat(fileName)));
            os = new PrintStream(new FileOutputStream(path.concat(fileName)));
            os.print(text);
        } catch (IOException e) {
            final String error = String.format("Error opening file: %s", path.concat(fileName));
            logger.severe(error);
        } finally {
                os.close();
        }
    }

    @Test
    public void getTopPhrasesTest() {
        assertEquals(checking.concat(fileName),resultExpected,topPhrases.getTopPhrases(topNumber).toString());
    }


}
