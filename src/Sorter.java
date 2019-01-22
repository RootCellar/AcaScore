/*
*   Sorting the scores in the many different ways that
*   they need to be sorted
*/

import java.io.*;
import java.util.ArrayList;

public class Sorter
{

    //Setup files
    static { //When this class is loaded, ...

        //Make all of these folders
        new File("Sorted").mkdir();

        new File("Sorted/Test").mkdir();
        new File("Sorted/Team").mkdir();
        new File("Sorted/Total").mkdir();
        new File("Sorted/CompDiv").mkdir();
        new File("Sorted/ActDiv").mkdir();
        new File("Sorted/Special").mkdir();
    }

    //Constants
    public static final int NAME_WIDTH = 25;
    public static final int SCHOOL_WIDTH = 10;
    public static final int COMPETE_WIDTH = 10;
    public static final int NUM_WIDTH = 5;
    public static final int SCORE_WIDTH = 6;

    /*
    *   Begin the many different sorting methods
    *   Steps:
    *       0. Start counting at 0
    *       1. Take the students we need right now, put them in a list
    *       2. Bubble sort by the score that matters right now
    *       3. Write to file
    */

    public void sortByTotal() {
        //debug("Sorting by total...");

        //debug("Making list...");

        ArrayList<Student> list = new ArrayList<Student>();

        //debug("Putting students into list...");

        for( Student s : Data.runner.sHandler.students ) {
            list.add(s);
        }

        //debug("Sorting...");

        for(int i=0; i < list.size(); i++) {

            for(int k=0; k < list.size() - 1; k++) {

                if( calcTotal(list.get(k)) < calcTotal(list.get(k + 1)) ) {
                    Student mv = list.remove(k);
                    list.add( k + 1, mv );
                }

            }

        }

        //debug("Sorted");

        for( Student s : list ) {
            //debug( s.name + " " + calcTotal(s) );
        }

        //debug("Writing...");

        try{
            File toWrite = new File("Sorted/Total/Total.txt");
            toWrite.createNewFile();

            FileWriter writer = new FileWriter( toWrite );

            writer.write( "AcaDeca Totals [" + list.size() + "]");
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            write(writer, "Name", NAME_WIDTH );
            writer.write( "|" );

            write(writer, "School", SCHOOL_WIDTH );
            writer.write( "|" );

            write(writer, "Compete", COMPETE_WIDTH );
            writer.write("|");

            write(writer, "Num", NUM_WIDTH );
            writer.write( "|" );

            write(writer, "Total", SCORE_WIDTH );
            writer.write( "|" );

            writer.write( System.getProperty("line.separator") );

            for( Student s : list ) {
                //writer.write( s.toStringShort() + " " + calcTotal( s ) );
                write(writer, s.name, NAME_WIDTH );
                writer.write( "|" );

                write(writer, s.school, SCHOOL_WIDTH );
                writer.write( "|" );

                write(writer, s.competeDiv, COMPETE_WIDTH );
                writer.write("|");

                write(writer, s.num, NUM_WIDTH );
                writer.write( "|" );

                write(writer, "" + calcTotal(s), SCORE_WIDTH );
                writer.write( "|" );

                writer.write( System.getProperty("line.separator") );
            }

            writer.flush();

            writer.close();

        }catch(Exception e) {
            e.printStackTrace();
            Data.runner.outAll("Can't write scores");
            return;
        }

        //debug("Done. Check out Sorted/Total/Total.txt");

    }

    public void sortByTest(String name) {
        //debug("Sorting by test " + name + "...");

        //debug("Making list...");

        ArrayList<Student> list = new ArrayList<Student>();

        //debug("Putting students into list...");

        for( Student s : Data.runner.sHandler.students ) {
            list.add(s);
        }

        //debug("Sorting...");

        for(int i=0; i < list.size(); i++) {

            for(int k=0; k < list.size() - 1; k++) {

                /*
                if( calcTotal(list.get(k)) < calcTotal(list.get(k + 1)) ) {
                Student mv = list.remove(k);
                list.add( k + 1, mv );
                }
                 */

                if( list.get(k).getScoreByName(name) < list.get(k + 1).getScoreByName(name) ) {
                    Student mv = list.remove(k);
                    list.add( k + 1, mv );
                }

            }

        }

        //debug("Sorted");

        for( Student s : list ) {
            //debug( s.name + " " + calcTotal(s) );
        }

        //debug("Writing...");

        try{
            File toWrite = new File("Sorted/Test/" + name + ".txt");
            toWrite.createNewFile();

            FileWriter writer = new FileWriter( toWrite );

            writer.write( "AcaDeca Totals - " + name + "[" + list.size() + "]");
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            write(writer, "Name", NAME_WIDTH );
            writer.write( "|" );

            write(writer, "School", SCHOOL_WIDTH );
            writer.write( "|" );

            write(writer, "Compete", COMPETE_WIDTH );
            writer.write("|");

            write(writer, "Num", NUM_WIDTH );
            writer.write( "|" );

            write(writer, "Total", SCORE_WIDTH );
            writer.write( "|" );

            writer.write( System.getProperty("line.separator") );

            for( Student s : list ) {
                //writer.write( s.toStringShort() + " " + calcTotal( s ) );
                write(writer, s.name, NAME_WIDTH );
                writer.write( "|" );

                write(writer, s.school, SCHOOL_WIDTH );
                writer.write( "|" );

                write(writer, s.competeDiv, COMPETE_WIDTH );
                writer.write("|");

                write(writer, s.num, NUM_WIDTH );
                writer.write( "|" );

                write(writer, "" + s.getScoreByName(name), SCORE_WIDTH );
                writer.write( "|" );

                writer.write( System.getProperty("line.separator") );
            }

            writer.flush();

            writer.close();

        }catch(Exception e) {
            e.printStackTrace();
            Data.runner.outAll("Can't write scores");
            return;
        }

        //debug("Done. Check out Sorted/Test/" + name + ".txt");

    }

    public void sortBy(String test, String div) {
        //debug("Sorting by " + test + " " + div + "...");

        //debug("Making list...");

        ArrayList<Student> list = new ArrayList<Student>();

        //debug("Putting students into list...");

        for( Student s : Data.runner.sHandler.students ) {
            //list.add(s);
            if(s.competeDiv.equals(div) ) list.add(s);
        }

        //debug("Sorting...");

        for(int i=0; i < list.size(); i++) {

            for(int k=0; k < list.size() - 1; k++) {

                /*
                if( calcTotal(list.get(k)) < calcTotal(list.get(k + 1)) ) {
                Student mv = list.remove(k);
                list.add( k + 1, mv );
                }
                 */

                if( list.get(k).getScoreByName(test) < list.get(k + 1).getScoreByName(test) ) {
                    Student mv = list.remove(k);
                    list.add( k + 1, mv );
                }

            }

        }

        //debug("Sorted");

        for( Student s : list ) {
            //debug( s.name + " " + calcTotal(s) );
        }

        //debug("Writing...");

        try{
            File toWrite = new File("Sorted/Special/" + test + "-" + div + ".txt");
            toWrite.createNewFile();

            FileWriter writer = new FileWriter( toWrite );

            writer.write( "AcaDeca Totals - " + test + " - " + div + "[" + list.size() + "]");
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            write(writer, "Name", NAME_WIDTH );
            writer.write( "|" );

            write(writer, "School", SCHOOL_WIDTH );
            writer.write( "|" );

            write(writer, "Compete", COMPETE_WIDTH );
            writer.write("|");

            write(writer, "Num", NUM_WIDTH );
            writer.write( "|" );

            write(writer, "Total", SCORE_WIDTH );
            writer.write( "|" );

            writer.write( System.getProperty("line.separator") );

            for( Student s : list ) {
                //writer.write( s.toStringShort() + " " + calcTotal( s ) );
                write(writer, s.name, NAME_WIDTH );
                writer.write( "|" );

                write(writer, s.school, SCHOOL_WIDTH );
                writer.write( "|" );

                write(writer, s.competeDiv, COMPETE_WIDTH );
                writer.write("|");

                write(writer, s.num, NUM_WIDTH );
                writer.write( "|" );

                write(writer, "" + s.getScoreByName(test), SCORE_WIDTH );
                writer.write( "|" );

                writer.write( System.getProperty("line.separator") );
            }

            writer.flush();

            writer.close();

        }catch(Exception e) {
            e.printStackTrace();
            Data.runner.outAll("Can't write scores");
            return;
        }

        //debug("Done. Check out Sorted/Special/" + test + "-" + div + ".txt");

    }

    public void sortBySchool(String school) {
        //debug("Sorting by " + test + " " + div + "...");

        //debug("Making list...");

        ArrayList<Student> list = new ArrayList<Student>();

        //debug("Putting students into list...");

        for( Student s : Data.runner.sHandler.students ) {
            //list.add(s);
            if(s.school.equals(school) ) list.add(s);
        }

        //debug("Sorting...");

        for(int i=0; i < list.size(); i++) {

            for(int k=0; k < list.size() - 1; k++) {

                /*
                if( calcTotal(list.get(k)) < calcTotal(list.get(k + 1)) ) {
                Student mv = list.remove(k);
                list.add( k + 1, mv );
                }
                */

                if( calcTotal( list.get(k) ) < calcTotal( list.get(k + 1) ) ) {
                    Student mv = list.remove(k);
                    list.add( k + 1, mv );
                }

            }

        }

        //debug("Sorted");

        for( Student s : list ) {
            //debug( s.name + " " + calcTotal(s) );
        }

        //debug("Writing...");

        int teamTotal = getTeamTotal(school);

        try {
            File toWrite = new File("Sorted/Team/" + school + ".txt");
            toWrite.createNewFile();

            FileWriter writer = new FileWriter( toWrite );

            writer.write( "AcaDeca Totals - " + school + "[" + list.size() + "] - " + teamTotal);
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            write(writer, "Name", NAME_WIDTH );
            writer.write( "|" );

            write(writer, "School", SCHOOL_WIDTH );
            writer.write( "|" );

            write(writer, "Compete", COMPETE_WIDTH );
            writer.write("|");

            write(writer, "Num", NUM_WIDTH );
            writer.write( "|" );

            write(writer, "Total", SCORE_WIDTH );
            writer.write( "|" );

            writer.write( System.getProperty("line.separator") );

            for( Student s : list ) {
                //writer.write( s.toStringShort() + " " + calcTotal( s ) );
                write(writer, s.name, NAME_WIDTH );
                writer.write( "|" );

                write(writer, s.school, SCHOOL_WIDTH );
                writer.write( "|" );

                write(writer, s.competeDiv, COMPETE_WIDTH );
                writer.write("|");

                write(writer, s.num, NUM_WIDTH );
                writer.write( "|" );

                write(writer, "" + calcTotal(s), SCORE_WIDTH );
                writer.write( "|" );

                if(isPartOfTeam(s)) writer.write("*");

                writer.write( System.getProperty("line.separator") );
            }

            writer.flush();

            writer.close();

        }catch(Exception e) {
            e.printStackTrace();
            Data.runner.outAll("Can't write scores");
            return;
        }
    }

    public void sortByDivision(String division) {
        //debug("Sorting by " + test + " " + div + "...");

        //debug("Making list...");

        ArrayList<Student> list = new ArrayList<Student>();

        //debug("Putting students into list...");

        for( Student s : Data.runner.sHandler.students ) {
            //list.add(s);
            if(s.competeDiv.equals(division) ) list.add(s);
        }

        //debug("Sorting...");

        for(int i=0; i < list.size(); i++) {

            for(int k=0; k < list.size() - 1; k++) {

                /*
                if( calcTotal(list.get(k)) < calcTotal(list.get(k + 1)) ) {
                Student mv = list.remove(k);
                list.add( k + 1, mv );
                }
                 */

                if( calcTotal( list.get(k) ) < calcTotal( list.get(k + 1) ) ) {
                    Student mv = list.remove(k);
                    list.add( k + 1, mv );
                }

            }

        }

        //debug("Sorted");

        for( Student s : list ) {
            //debug( s.name + " " + calcTotal(s) );
        }

        //debug("Writing...");

        try{
            File toWrite = new File("Sorted/CompDiv/" + division + ".txt");
            toWrite.createNewFile();

            FileWriter writer = new FileWriter( toWrite );

            writer.write( "AcaDeca Totals - " + division + "[" + list.size() + "]");
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            write(writer, "Name", NAME_WIDTH );
            writer.write( "|" );

            write(writer, "School", SCHOOL_WIDTH );
            writer.write( "|" );

            write(writer, "Compete", COMPETE_WIDTH );
            writer.write("|");

            write(writer, "Num", NUM_WIDTH );
            writer.write( "|" );

            write(writer, "Total", SCORE_WIDTH );
            writer.write( "|" );

            writer.write( System.getProperty("line.separator") );

            for( Student s : list ) {
                //writer.write( s.toStringShort() + " " + calcTotal( s ) );
                write(writer, s.name, NAME_WIDTH );
                writer.write( "|" );

                write(writer, s.school, SCHOOL_WIDTH );
                writer.write( "|" );

                write(writer, s.competeDiv, COMPETE_WIDTH );
                writer.write("|");

                write(writer, s.num, NUM_WIDTH );
                writer.write( "|" );

                write(writer, "" + calcTotal(s), SCORE_WIDTH );
                writer.write( "|" );

                writer.write( System.getProperty("line.separator") );
            }

            writer.flush();

            writer.close();

        }catch(Exception e) {
            e.printStackTrace();
            Data.runner.outAll("Can't write scores");
            return;
        }
    }

    /* End the many sorting methods */

    /*
    *   Tricky little method
    *   Given a filewriter, write the given string with the given length
    *   If the string is too short, put some spaces
    *   If the string is too long, strip the ending
    */
    public void write(FileWriter f, String s, int length) throws Exception {
        while(s.length() < length) s += " ";

        while(s.length() > length) s = s.substring( 0, s.length() - 1 );

        f.write(s);
    }

    /*
    *   Grand sorting method
    *   outputs some cool debug info, calls the actual sorting methods
    */
    public void sort() {
        Timer overall = new Timer();
        overall.start();

        out("Doing Sort...");

        out("Deleting previous results...");

        Timer delete = new Timer();
        delete.start(); //Delete previous results

        Data.runner.delete(new File("Sorted"));

        delete.stop();

        out("Sorting by total...");
        sortByTotal(); //Sort by total

        out("Sorting by test and division...");
        for( TestScores s : Data.runner.testScores ) { //Sort by test
            debug("Sorting " + s.name);
            sortByTest( s.name );

            for(String st : getDivisions() ) { //Sort by each division per test
                debug("Sorting " + s.name + " - " + st);
                sortBy( s.name, st );
            }
        }

        out("Sorting by team...");
        for(String s : getSchools()) { //Sort teams, find their totals
            debug("Sorting by " + s);
            sortBySchool(s);
        }

        out("Sorting by division...");
        for(String s : getDivisions()) { //Sort out the divisions (Honors, Scholastic, Varsity)
            debug("Sorting by " + s);
            sortByDivision(s);
        }

        out("Done Sorting");

        overall.stop();
        out("Took " + overall.takenInS() + " seconds");
        out("Deletion took " + delete.takenInS() + " seconds");
    }

    //Given a team name, find the students whose scores actually count,
    //and add them up
    public int getTeamTotal(String n) {
        ArrayList<Student> team = new ArrayList<Student>();

        for(Student s : Data.runner.sHandler.students) {
            if(s.school.equals(n)) team.add(s);
        }

        int total = 0;

        for(int i = 0; i<team.size(); i++) {
            Student s = team.get(i);
            if(!isPartOfTeam(s)) {
                team.remove(s);
                i--;
                debug(s.name + " is not part of a team: " + s.num);
            }
            else debug(s.name + " is part of a team: " + s.num);
        }

        for(Student s : team) {
            total += calcTotal(s);
        }

        return total;
    }

    //Useful little method
    //Does the given student's score count towards the team's total?
    public boolean isPartOfTeam(Student s) {
        String num = s.num;

        boolean parsed = false;
        int iNum = -1;
        while(!parsed) {
            if(num.length() < 1) return false;

            try{
                iNum = Integer.parseInt(num);
                parsed = true;
            }catch(Exception e) {
                num = num.substring(1);
            }
        }

        iNum %= 100;
        iNum /= 10;
        if(iNum == 0) {
            return true;
        }

        return false;
    }

    //Some other helper methods

    //Loop the students, build an arraylist of the schools
    //that are found
    public ArrayList<String> getSchools() {
        ArrayList<String> schools = new ArrayList<String>();

        studentLoop: for( Student s : Data.runner.sHandler.students ) {

            schLoop: for( String st : schools ) {

                if( st.equals( s.school) ) {

                    continue studentLoop;

                }

            }

            schools.add(s.school);

        }

        return schools;
    }

    //Loop students, build arraylist of found divisions
    public ArrayList<String> getDivisions() {
        ArrayList<String> divisions = new ArrayList<String>();

        studentLoop: for( Student s : Data.runner.sHandler.students ) {

            divLoop: for( String st : divisions ) {

                if( st.equals( s.competeDiv ) ) {

                    continue studentLoop;

                }

            }

            divisions.add(s.competeDiv);

        }

        return divisions;
    }

    //Helpful!
    public int calcTotal(Student s) {
        int toRet = 0;

        for( Score score : s.scores.scoreList ) {
            toRet += score.getScore();
        }

        return toRet;
    }

    public Sorter() {
        debug("Constructing...");
    }

    public static void out(String s) {
        Data.runner.out("[SORTER] " + s);
    }

    public static void debug(String s) {
        Data.runner.debug("[SORTER] " + s);
    }
}
