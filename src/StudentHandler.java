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
    
    public Student getByName(String s) {
        for(Student student : students) {
            if(s.equals( student.name )) return student;
        }
        
        return null;
    }

    public void addStudent(Student newStudent) {
        out2("Adding a new Student: " + newStudent.toString());
        students.add(newStudent);
        try{
            save(newStudent);
        }catch(Exception e) {
            out("Exception when saving new student");
        }
        out2("It is recommended that you restart the program before sorting student scores");
    }
    
    public void deleteStudent(Student s) {
        students.remove(s);
        
        Data.runner.delete( new File("Students/" + s.name + ".txt") );
        
        out("Deleted " + s.name);
    }

    public void readAll() {
        out(Student.toStringInfo());
        out2("Reading...");
        for( File f : new File("Students").listFiles() ) {
            out( "Found " + f.getName() );
            
            Student s = new Student();
            s.name = f.getName();
            
            if( s.name.indexOf(".") > 0) {
                out("Removing file extension...");
                s.name = s.name.substring( 0, s.name.indexOf(".") );
            }
            
            try{
                Scanner in = new Scanner( f );
                
                s.competeDiv = in.nextLine();
                s.actualDiv = in.nextLine();
                s.school = in.nextLine();
                s.num = in.nextLine();
                
                in.close();
            }catch(Exception e) {
                out("Can't read " + s.name);
                e.printStackTrace();
                continue;
            }
            
            out( s.toString() );
            
            students.add( s );
        }
        
        out2("Successfully loaded " + students.size() + " student file(s)");
    }

    public void saveAll() {
        out2("Saving Students...");

        for( Student s : students ) {
            try{
                save(s);
            }catch(Exception e) {
                out("Can't save " + s.name);
            }
        }

        out2("Done Saving Students");
    }

    public void save(Student s) throws Exception {
        out("Saving " + s.name );

        File file = new File("Students/" + s.name + ".txt");

        FileWriter writer = new FileWriter(file);

        writer.write( s.competeDiv );
        writer.write( System.getProperty("line.separator") );

        writer.write( s.actualDiv );
        writer.write( System.getProperty("line.separator") );

        writer.write( s.school );
        writer.write( System.getProperty("line.separator") );

        writer.write( s.num );
        writer.write( System.getProperty("line.separator") );

        writer.flush();

        writer.close();

        //out("Done Saving");
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
    
    public static void out2(String s) {
        out(s);
        Data.runner.out("[STUDENT HANDLER] " + s);
    }
}