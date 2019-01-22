public class Score
{
    String name;
    int score;

    String studentNumber;

    public Score(String n, int s, String num) {
        name = n;
        score = s;
        studentNumber = num;

    }

    public String getName() {
        return name;
    }

    public int getScore() {

        return score;
    }

    public String toString() {
        return "{ " + name + ", " + score + ", " +studentNumber + " }";
    }

    /*
    *   Parses a student score
    *   Only used for .txt files
    *   Should NEVER be used except when testing
    *   (Input files should be CSV)
    */
    public static Score parse(String testName, String n) {

        if(n.indexOf(",") < 0) {
            //out(testName + " test: Can't parse student score: " + n);
            return null;
        }

        if(n.indexOf( "," ) + 2 >= n.length()) {
            //out(testName + " test: Can't parse student score: " + n);
            return null;
        }

        String sNum = n.substring( 0, n.indexOf( "," ) );

        String sc = n.substring( n.indexOf( "," ) + 2 );

        int studSc;

        try{
            studSc = Integer.parseInt( sc );
            studSc *= 20; //We'll just assume a 50 question test...
        }catch(Exception e) {
            //out("Can't parse student score");
            return null;
        }

        Score toRet = new Score( testName, studSc, sNum );

        return toRet;
    }

    public void out(String s) {
        Data.runner.debug("[SCORE] " + s);
    }
}
