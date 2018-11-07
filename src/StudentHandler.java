import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class StudentHandler
{
    static {
        out("Ensuring folder exists...");
        new File("Students").mkdir();
    }
    
    ArrayList<Student> students;
    
    public StudentHandler() {
        out("Constructing student handler...");
        students = new ArrayList<Student>();
        
        readAll();
    }
    
    public void readAll() {
        out(Student.toStringInfo());
        out("Reading...");
        for( File f : new File("Students").listFiles() ) {
            out( "Found " +f.getName() );
            
            Student s = new Student();
            s.name = f.getName();
            
            try{
                Scanner in = new Scanner( f );
                
                s.competeDiv = in.nextLine();
                s.actualDiv = in.nextLine();
                s.school = in.nextLine();
                s.num = in.nextLine();
                
                in.close();
            }catch(Exception e) {
                out("Can't read " + s.name);
                continue;
            }
            
            out( s.toString() );
            
            students.add( s );
        }
        
        out("Successfully loaded " + students.size() + " student file(s)");
    }
    
    public void saveAll() {
        out("Saving...");
        
        for( Student s : students ) {
            
        }
    }
    
    public void pack(TestScores test) {
        for( Score s : test.scores.scoreList ) {
            
            for( Student student : students ) {
                
                if( s.studentNumber.equals( student.num ) ) {
                    student.scores.add(s);
                }
                
            }
            
        }
    }
    
    public static void out(String s) {
        Data.runner.debug("[STUDENT HANDLER] " + s);
    }
}