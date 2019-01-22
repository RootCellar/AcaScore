/*
*   Reading the score input files
*/

import java.io.*;
import java.util.Scanner;
import Logging.*;

public class ScoreReader
{

    static { //When this class is loaded, ...
        out("Ensuring folders exist...");
        //new File("Scores").mkdir(); //We don't neet this
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

        String extension = ""; //Does actually have a use

        //If there is a file extension, remove it
        //(For keeping track of test names)
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
            //This is why we can't have nice things...
            e.printStackTrace();
            out("Can't read scores!");
            return null;
        }

        out("Reading...");

        boolean first = true;

        while( in.hasNext() ) {
            String input = in.nextLine();

            if(extension.equals(".csv")) { //If it's a CSV, read the normal way...
                Row r = new Row(input);

                //out(r.toString());

                if(first) { //The row base allows us to pick the data from the correct columns
                    Row.base = r;
                    out("Setting row base...");
                    first = false;
                    continue;
                }

                String sNum = r.getByName("GradeCam ID");
                String max = r.getByName("Possible");
                String got = r.getByName("Points");

                if(sNum.length() < 1) continue;

                //Fix for that weird bug...
                //Sometimes, the CSVs put extra 0's at the beginning of the ID
                //We'll just remove them...
                while(sNum.substring(0,1).equals("0")) sNum = sNum.substring(1);

                int possible, points = 0;

                //Attempt to parse the points possible and points earned
                try{
                    possible = Integer.parseInt(max);
                    points = Integer.parseInt(got);
                }catch(Exception e) { //If we can't, give up... move on
                    out("Failed to parse " + input);
                    //Score score = new Score(testName, 0, sNum);
                    continue;
                }

                //Basically, find the percent earned (0-1), multiply by 1000
                //All tests should be scored 0-1000
                int actScore = (int) ( ( ( (double) points ) / ( (double) possible ) ) * 1000 );

                //It's coming together!
                Score score = new Score(testName, actScore, sNum);

                scores.add(score);

            }
            else { //Otherwise, read it the old way (Should only be for debug)

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

    //REALLY not necessary, but I guess...
    //At least it ensures that the input folder exists,
    //even though it already should...
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
