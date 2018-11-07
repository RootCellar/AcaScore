import java.io.*;
import java.util.ArrayList;

public class Sorter
{

    static {
        new File("Sorted/Test").mkdir();
        new File("Sorted/Team").mkdir();
        new File("Sorted/Total").mkdir();
        new File("Sorted/CompDiv").mkdir();
        new File("Sorted/ActDiv").mkdir();
    }

    public void sortByTotal() {
        out("Sorting by total...");

        out("Making list...");

        ArrayList<Student> list = new ArrayList<Student>();

        out("Putting students into list...");

        for( Student s : Data.runner.sHandler.students ) {
            list.add(s);
        }

        out("Sorting...");

        for(int i=0; i < list.size(); i++) {

            for(int k=0; k < list.size() - 1; k++) {

                if( calcTotal(list.get(k)) < calcTotal(list.get(k + 1)) ) {
                    Student mv = list.remove(k);
                    list.add( k + 1, mv );
                }

            }

        }

        out("Sorted");

        for( Student s : list ) {
            debug( s.name + " " + calcTotal(s) );
        }

        out("Writing...");

        try{
            File toWrite = new File("Sorted/Total/Total.txt");
            toWrite.createNewFile();

            FileWriter writer = new FileWriter( toWrite );

            writer.write( "AcaDeca Totals " );
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            writer.write("Totals: " + Student.toStringInfo() );
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            write(writer, "Name", 40 );
            writer.write( "|" );
            write(writer, "School", 15 );
            writer.write( "|" );
            write(writer, "Num", 5 );
            writer.write( "|" );
            write(writer, "Total", 10 );
            writer.write( "|" );
            writer.write( System.getProperty("line.separator") );

            for( Student s : list ) {
                //writer.write( s.toStringShort() + " " + calcTotal( s ) );
                write(writer, s.name, 40 );
                writer.write( "|" );

                write(writer, s.school, 15 );
                writer.write( "|" );

                write(writer, s.num, 5 );
                writer.write( "|" );

                write(writer, "" + calcTotal(s), 10 );
                writer.write( "|" );

                writer.write( System.getProperty("line.separator") );
            }

            writer.flush();

            writer.close();

        }catch(Exception e) {
            e.printStackTrace();
            out("Can't write scores");
            return;
        }

        out("Done. Check out Sorted/Total/Total.txt");

    }

    public void sortByTest(String name) {
        out("Sorting by test " + name + "...");

        out("Making list...");

        ArrayList<Student> list = new ArrayList<Student>();

        out("Putting students into list...");

        for( Student s : Data.runner.sHandler.students ) {
            list.add(s);
        }

        out("Sorting...");

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

        out("Sorted");

        for( Student s : list ) {
            debug( s.name + " " + calcTotal(s) );
        }

        out("Writing...");

        try{
            File toWrite = new File("Sorted/Test/" + name + ".txt");
            toWrite.createNewFile();

            FileWriter writer = new FileWriter( toWrite );

            writer.write( "AcaDeca Totals - " + name);
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            writer.write("Totals: " + Student.toStringInfo() );
            writer.write( System.getProperty("line.separator") );
            writer.write( System.getProperty("line.separator") );

            write(writer, "Name", 40 );
            writer.write( "|" );
            write(writer, "School", 15 );
            writer.write( "|" );
            write(writer, "Num", 5 );
            writer.write( "|" );
            write(writer, "Total", 10 );
            writer.write( "|" );
            writer.write( System.getProperty("line.separator") );

            for( Student s : list ) {
                //writer.write( s.toStringShort() + " " + calcTotal( s ) );
                write(writer, s.name, 40 );
                writer.write( "|" );

                write(writer, s.school, 15 );
                writer.write( "|" );

                write(writer, s.num, 5 );
                writer.write( "|" );

                write(writer, "" + s.getScoreByName(name), 10 );
                writer.write( "|" );

                writer.write( System.getProperty("line.separator") );
            }

            writer.flush();

            writer.close();

        }catch(Exception e) {
            e.printStackTrace();
            out("Can't write scores");
            return;
        }

        out("Done. Check out Sorted/Test/" + name + ".txt");

    }

    public void write(FileWriter f, String s, int length) throws Exception {
        while(s.length() < length) s += " ";

        f.write(s);
    }

    public void sort() {
        sortByTotal();
        
        for( TestScores s : Data.runner.testScores ) {
            sortByTest( s.name );
        }
    }

    public int calcTotal(Student s) {
        int toRet = 0;

        for( Score score : s.scores.scoreList ) {
            toRet += score.getScore();
        }

        return toRet;
    }

    public Sorter() {
        out("Constructing...");
    }

    public static void out(String s) {
        Data.runner.out("[SORTER] " + s);
    }

    public static void debug(String s) {
        Data.runner.debug("[SORTER] " + s);
    }
}