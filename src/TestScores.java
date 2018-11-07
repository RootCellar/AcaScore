public class TestScores
{
    String name;
    
    Scores scores;
    
    public TestScores(String n) {
        out("Creating new instance...");
        
        name = n;
        
        scores = new Scores();
    }
    
    public void add(Score s) {
        scores.add(s);
    }
    
    public String toString() {
        return "Test " + name + ": " + scores.toString();
    }
    
    public void out(String s) {
        Data.runner.debug("[TEST SCORES] " + s);
    }
}