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

        String extension = "";

        if( testName.indexOf(".") != 0) {
            out("Removing file extension...");
            extension = testName.substring( testName.indexOf(".") );
            testName = testName.substring( 0, testName.indexOf(".") );
        }
        else {
            out("No File Extension.");
        }

        TestScores scores = new TestScores( testName );

        out("Reading " + f.getName() + ", keeping as " + testName );
        out("Extension: " + extension);

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
        
        boolean first = true;

        while( in.hasNext() ) {
            String input = in.nextLine();

            if(extension.equals(".csv")) {
                Row r = new Row(input);
                
                //out(r.toString());
                
                if(first) {
                    Row.base = r;
                    out("Setting row base...");
                    first = false;
                    continue;
                }
                
                String sNum = r.getByName("GradeCam ID");
                String max = r.getByName("Possible");
                String got = r.getByName("Points");
                
                if(sNum.length() < 1) continue;
                
                while(sNum.substring(0,1).equals("0")) sNum = sNum.substring(1);
                
                int possible, points = 0;
                
                try{
                    possible = Integer.parseInt(max);
                    points = Integer.parseInt(got);
                }catch(Exception e) {
                    out("Failed to parse " + input);
                    //Score score = new Score(testName, 0, sNum);
                    continue;
                }
                
                int actScore = (int) ( ( ( (double) points ) / ( (double) possible ) ) * 1000 );
                
                Score score = new Score(testName, actScore, sNum);
                
                scores.add(score);
                
            }
            else {

                Score score = Score.parse(testName, input);
                if(score == null) {
                    out("Failed to parse: " + input);
                    continue;
                }

                scores.add(score);
            }
            
            first = false;
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