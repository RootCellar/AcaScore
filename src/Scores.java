import java.util.ArrayList;

public class Scores
{
    ArrayList<Score> scoreList = new ArrayList<Score>();

    public Scores() {
        out("Creating new instance...");
    }
    
    public ArrayList<Score> getList() {
        return scoreList;
    }
    
    public int count() {
        return scoreList.size();
    }
    
    public Score get(int i) {
        return scoreList.get(i);
    }

    public void add(Score s) {
        out("Adding " + s.toString() );
        
        scoreList.add(s);
    }

    public String toString() {
        String toRet = "ScoreList: { ";
            
        if(scoreList.size() < 1) return toRet + "}";
        
        for(Score s: scoreList) {
            toRet += "\n" + s.toString();
        }
        
        return toRet + "\n }";
    }
    
    public void out(String s) {
        Data.runner.debug("[SCORES] " + s);
    }
}