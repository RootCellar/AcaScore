import java.io.*;
import java.util.Scanner;
import Logging.*;

public class ScoreReader
{
    
    static {
        out("Ensuring folders exist...");
        new File("Scores").mkdir();
        new File("Input").mkdir();
    }
    
    public ScoreReader() {
        out("Creating score reader...");
    }
    
    public TestScores read(File f) {
        
        out("Attempting to read " + f.getName() );
        
        if(!f.exists() ) {
            out("File can't be read: Does not exist");
            return null;
        }
        
        if(!f.canRead()) {
            out("File can't be read");
            return null;
        }
        
        String testName = f.getName();
        
        if( testName.indexOf(".") != 0) {
            out("Removing file extension...");
            testName = testName.substring( 0, testName.indexOf(".") );
        }
        else {
            out("No File Extension.");
        }
        
        TestScores scores = new TestScores( testName );
        
        out("Reading " + f.getName() + ", keeping as " + testName );
        
        Scanner in;
        
        out("Opening file...");
        
        try{
            in = new Scanner(f);
        }catch(Exception e) {
            e.printStackTrace();
            out("Can't read scores!");
            return null;
        }
        
        out("Reading...");
        
        while( in.hasNext() ) {
            String input = in.nextLine();
            
            Score score = Score.parse(testName, input);
            if(score == null) {
                out("Failed to parse: " + input);
                continue;
            }
            
            scores.add(score);
        }
        
        out("Done");
        
        try{
            in.close();
        }catch(Exception e) {
            e.printStackTrace();
            out("Can't close file!");
        }
        
        return scores;
    }
    
    public File[] listFiles() {
        File folder = new File("Input");
        folder.mkdir();
        
        return folder.listFiles();
    }
    
    public static void out(String o) {
        //System.out.println( "[Score Reader] " + o );
        
        Data.runner.debug("[Score Reader] " + o );
    }
}