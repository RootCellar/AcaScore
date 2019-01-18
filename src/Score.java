public class Score
{
    String name;
    int score;
    //int calcScore;

    String studentNumber;

    public Score(String n, int s, String num) {
        name = n;
        score = s;
        studentNumber = num;

        //calcScore();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        //calcScore();

        return score;
    }

    public void calcScore() {
        //calcScore = score * 20;
    }
    
    public String toString() {
        return "{ " + name + ", " + score + ", " +studentNumber + " }";
    }

    public static Score parse(String testName, String n) {
        String sNum = n.substring( 0, n.indexOf( "," ) );

        String sc = n.substring( n.indexOf( "," ) + 2, n.length() );
        
        int studSc;
        
        try{
            studSc = Integer.parseInt( sc );
        }catch(Exception e) {
            System.out.println("Can't parse student score");
            return null;
        }
        
        Score toRet = new Score( testName, studSc, sNum );
        
        return toRet;
    }
    
    public void out(String s) {
        Data.runner.debug("[SCORE] " + s);
    }
}