public class Student
{
    Scores scores;
    
    String name = "NULL NULL";
    String competeDiv = "NUL";
    String actualDiv = "NUL";
    String school = "NUL";
    String num = "NUL";
    
    public Student() {
        out("Constructing a student...");
        scores = new Scores();
    }
    
    public int getScoreByName(String n) {
        for( Score s : scores.scoreList ) {
            if( n.equals(s.name) ) {
                return s.getScore();
            }
        }
        
        return -1;
    }
    
    public Scores getScores() {
        if(scores == null) return null;
        
        return scores;
    }
    
    public void out(String s) {
        Data.runner.debug("[STUDENT] " + s);
    }
    
    public String toString() {
        return name + ", " + competeDiv + ", " + actualDiv + ", " + school + ", " + num ; //+ ", " + scores.toString();
    }
    
    public String toStringShort() {
        return name + ", " + competeDiv + ", " + actualDiv + ", " + school + ", " + num;
    }
    
    public static String toStringInfo() {
        return "name, competeDiv, actualDiv, school, num";
    }
}