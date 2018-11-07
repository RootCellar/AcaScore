import java.io.*;

public class ScoreWriter
{
    
    static {
        out("Ensuring folder exists...");
        new File("Sorted").mkdir();
    }
    
    public ScoreWriter() {
        out("Constructing...");
    }
    
    public static void out(String s) {
        Data.runner.debug("[SCORE WRITER] " + s);
    }
}