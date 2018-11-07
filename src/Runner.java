import java.io.*;
import java.util.*;
import Logging.*;
import Util.*;

public class Runner implements InputUser, Runnable
{

    //Constants
    static final int PRINT_SLEEP_TIME = 5;

    //Booleans
    boolean DEBUG = true;
    boolean going = false;

    //Useful Objects
    ScoreReader scoreReader;
    ScoreWriter scoreWriter;
    StudentHandler sHandler;
    Sorter sorter;

    //Logging
    Logger logger = new Logger("Runner", "Log");
    Logger debugLog = new Logger("Runner", "Debug");

    //GUI
    //Terminal term = new Terminal();
    MainGUI gui = new MainGUI();
    
    //Stuff
    ArrayList<TestScores> testScores = new ArrayList<TestScores>();

    public static void main(String[] args) {
        new Runner();
    }

    public Runner() {

        debug("Starting...");

        Data.runner = this;

        outAll("Initializing...");

        //Create useful objects
        scoreReader = new ScoreReader();
        scoreWriter = new ScoreWriter();
        sHandler = new StudentHandler();
        sorter = new Sorter();

        gui.r = this;
        gui.term.setUser(this);

        debug("Finished setup");

        outAll("Starting read...");

        for(File f : scoreReader.listFiles()) {
            //if(DEBUG) out( scoreReader.read( f ) + "\n" );
            outAll("Reading " + f.getName() );
            //sHandler.pack( scoreReader.read( f ) );
            testScores.add( scoreReader.read( f ) );
            sHandler.pack( testScores.get( testScores.size() - 1 ) );
        }
        
        out("Students: ");

        for( Student s : sHandler.students ) {
            //out(s.toString());
            out(s.name);
        }

        outAll("Finished read");

        start();
    }

    public void start() {
        if(!going) {
            going = true;
            new Thread(this).start();
        }

    }

    public void stop() {
        going = false;
        outAll("Stopping...");
    }

    public void run() {
        debug("Starting Loop...");

        //setup
        int interInRow = 0; //Keep track of how many exceptions happen in a row
        long timeSave = System.nanoTime(); //Keep track of time, save info at good intervals

        debug("Entering Loop...");

        outAll("Ready!");

        out("Type \"help\" for a list of commands");

        while(going) {
            try{
                Thread.sleep(100); //We really don't need to loop that fast, do we?
            }catch(Exception e) {
                debug("Weird exception while sleeping in main loop");
            }

            try{
                tick(); //Do Stuff

                if(interInRow != 0) {
                    outAll("Resetting counter after " + interInRow + " exceptions in a row.");
                    interInRow = 0; //yay, successful tick. Reset counter
                }

            }catch(Exception e) {

                outAll("Exception in main loop");
                interInRow++;
                outAll(interInRow + " exceptions in a row");

                if(interInRow > 9 ) {
                    going = false;
                    outAll("Giving up, too many exceptions in a row");
                }

            }

        }

        outAll("Stopping...");

        //Handle shutdown
        sHandler.saveAll();

        outAll("Done");
        outAll("Exit");
        System.exit(0);
    }

    public void tick() {

    }

    public void inputText(String s) {
        debug("COMMAND: " + s);
        
        if( Command.is( "help", s ) ) {
            out("stop - stop program");
            out("students - list students");
        }

        if( Command.is( "stop", s ) ) {
            stop();
        }

        if( Command.is( "students", s ) ) {
            out(Student.toStringInfo());
            for( Student student : sHandler.students ) {
                out(student.toString());
            }
        }
        
        if( Command.is( "sort", s ) ) {
            sorter.sort();
        }
    }

    public void out(String s) {
        gui.out(s);
        logger.log(s);

        System.out.println("[STANDARD] " + s );

        sleep(PRINT_SLEEP_TIME);
    }

    public void debug(String s) {
        gui.debug(s);
        debugLog.log(s);

        System.out.println("[DEBUG] " + s );

        sleep(PRINT_SLEEP_TIME);
    }

    public void outAll(String s) {
        out(s);
        debug(s);
    }

    public void sleep(int a) {
        try {
            Thread.sleep(a);
        }catch(Exception e) {

        }
    }
}