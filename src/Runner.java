import java.io.*;
import java.util.*;

import Logging.*;
import Util.*;

public class Runner implements InputUser, Runnable
{

    //Constants
    static int PRINT_SLEEP_TIME = 5;

    //Booleans
    boolean DEBUG = true;
    boolean going = false;

    //Useful Objects
    ScoreReader scoreReader;
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
    ArrayList<String> commands = new ArrayList<String>();

    public static void main(String[] args) {
        Runner r = new Runner();

        for(String s : args) {
            r.out("Using auto command: " + s);
            r.inputText(s);
        }
    }

    public Runner() {

        debug("Starting...");

        Data.runner = this;

        outAll("Initializing...");

        /*
        outAll("Deleting previous results...");
        delete( new File("Sorted") );
        outAll("Done. Creating objects...");
         */

        //Create useful objects
        scoreReader = new ScoreReader();
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
            debug("Packing " + f.getName());
            sHandler.pack( testScores.get( testScores.size() - 1 ) );
        }

        out("Students: ");

        for( Student s : sHandler.students ) {
            //out(s.toString());
            out(s.name);
        }
        
        for(TestScores t : testScores) {
            out(t.name + ": " + t.scores.count());
            //out(t.toString());
        }

        outAll("Finished read");

        start();
    }

    public void combine() {
        delete( new File("Sorted/Combine.txt") );

        try {
            //add( new File("Sorted") );
            
            add( new File("Sorted/Total") );
            add( new File("Sorted/CompDiv") );
            add( new File("Sorted/Special") );
            add( new File("Sorted/Test") );
            add( new File("Sorted/Team") );
            
            //add( new File("Sorted/ActDiv") );

        }catch(Exception e) {
            out("Trouble while combining files");
            e.printStackTrace();
        }
    }

    public void add(File f) throws Exception {
        if( f.isDirectory() ) {
            debug("Entering " + f.getName() + "/");
            File[] files = f.listFiles();

            for(File file : files)  {
                try{
                    add(file);
                }catch(Exception e) {
                    
                }
            }
            
            return;
        }
        
        debug("Combining " + f.getName());

        Scanner scanny;
        FileWriter writer;

        scanny = new Scanner(f);
        writer = new FileWriter( new File( "Sorted/Combine.txt" ) , true );

        while(scanny.hasNextLine()) {
            String cp = scanny.nextLine();
            writer.write(cp);
            writer.write( System.getProperty("line.separator") );
        }

        writer.write( System.getProperty("line.separator") );
        writer.write( "-------------------------------" );
        writer.write( System.getProperty("line.separator") );

        scanny.close();
        writer.close();

    }

    public void delete( File f ) {
        if( f.isDirectory() ) {

            for( File file : f.listFiles() ) {
                delete(file);
            }

        }
        else {

            try {
                f.delete();
                debug("Deleted " + f.getName());
            }catch(Exception e) {
                out("Can't delete " + f.getName() );
            }

        }
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

        out("Type \"help\" for a list of commands, or use the buttons!");

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
                
                e.printStackTrace();

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

        sleep(1000);

        System.exit(0);
    }

    public void tick() {
        while(commands.size() > 0) doCommand(commands.remove(0));
    }

    public synchronized void inputText(String s) {
        commands.add(s);
    }

    public void doCommand(String s) {
        debug("COMMAND: " + s);

        if( Command.is( "help", s ) ) {
            out("stop - stop program");
            out("students - list students");
            out("sort - sorts scores");
            out("save - save students");
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
            /*
            out("Deleting old results...");
            delete( new File("Sorted") );
            out("Done. Sorting...");
             */
            sorter.sort();
            out("Combining sorted output...");
            combine();
            out("Done");
        }

        if( Command.is( "save", s ) ) {
            sHandler.saveAll();
        }

        if( Command.is( "NEWS", s ) ) {
            new StudentEdit(false);
        }

        if( Command.is( "MODS", s ) ) {
            new StudentEdit(true);
        }

        if( Command.is( "DELS", s ) ) {
            new StudentDelete();
        }
    }

    public void out(String s) {
        gui.out("[RUNNER] " + s);
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