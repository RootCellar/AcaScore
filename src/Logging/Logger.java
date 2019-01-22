/*
*   Darian Marvel - Logger.java
*   1/20/2019 v1.0
*   Nifty class that can be used just about anywhere to log information
*
*   Also has static methods for getting a string representation of the
*   time and date, if that's more appealing....
*
*/

package Logging; //Exists in folder called "Logging"

import java.io.*;
import java.util.Date;

public class Logger
{
    //File Objects
    private File f;
    private FileWriter toLog;

    //Strings
    private String fileName = "";
    private String fileFolder = "";

    //Booleans
    public boolean canWrite=true;
    private boolean namedFile = false;

    //Default to naming the file by the date it is opened
    public Logger() {
        open();
    }

    //Specify folder name and file name
    //Useful for larger programs where organized output is helpful
    public Logger(String f, String n) {
        fileName = n;
        fileFolder = f;
        namedFile = true;
        open();
    }

    //Simple but effective
    public void reopen() {
        close();
        open();
    }

    //The magic
    public void open() {
        new File("Logs").mkdir();
        if(namedFile && !fileFolder.equals("") ) new File("Logs/" + fileFolder).mkdir();

        if(!namedFile) f = new File( "Logs/" + getNameByDate( new Date() ) + ".txt" );
        else if( fileFolder.equals("") ) f = new File("Logs/" + fileName + ".txt");
        else f = new File("Logs/" + fileFolder + "/" + fileName + ".txt");

        try{
            toLog = new FileWriter(f,true);
        }catch(Exception e) {
            canWrite=false;
        }

        log("Opened Log!");
    }

    public void close() {
        try{
            toLog.close();
        }catch(Exception e) {

        }
    }

    //Write the given string to the log file
    //Also puts the date, time, and a newline
    public void log(String s) {
        if(!canWrite) return;

        s.replaceAll( "\n", System.getProperty("line.separator") ); //Should work

        try{
            toLog.write( getTimeAsString( new Date() )+" : "+s ); //Write time and data
            toLog.write( System.getProperty("line.separator") ); //New line
            toLog.flush(); //Push to file
        }catch(Exception e) {
            //Uh-oh! Try re-opening...
            canWrite=false;
            reopen();
        }
    }

    public static String getNameByDate(Date date) {
        return (date.getMonth()+1)+"-"+date.getDate()+"-"+(date.getYear()+1900);
    }

    public static String getTimeAsString(Date date) {
        String ind = "AM";
        int h = date.getHours();
        if(h>12) {
            h-=12;
            ind= "PM";
        }

        int m2 = date.getMinutes();
        String m = m2+"";
        if(m2<10) m="0"+m2;

        int s2 = date.getSeconds();
        String s = s2+"";
        if(s2<10) s="0"+s2;

        return (date.getMonth()+1)+"/"+date.getDate()+"/"+(date.getYear()+1900)+" "+h+":"+m+":"+s+" "+ind;
    }

    public static String getTimeAsString() {
        return getTimeAsString( new Date() );
    }

    public static String getDayByInt(int n) {
        String toReturn = "";

        if(n==0) toReturn = "Sunday";
        if(n==1) toReturn = "Monday";
        if(n==2) toReturn = "Tuesday";
        if(n==3) toReturn = "Wednesday";
        if(n==4) toReturn = "Thursday";
        if(n==5) toReturn = "Friday";
        if(n==6) toReturn = "Saturday";

        return toReturn;
    }

    //Returns a string representing a given date
    public static String getTimeAndDate(Date date) {
        int yy = date.getYear()+1900;
        int mm = date.getMonth()+1;
        int d = date.getDate();
        int day = date.getDay();
        int h = date.getHours();
        int m = date.getMinutes();
        int s = date.getSeconds();

        String sString = s+"";
        if(s<10) sString = "0"+sString;
        String mString = m+"";
        if(m<10) mString = "0"+mString;

        String ret = "";
        ret+=getDayByInt(day)+", ";
        ret+=mm+"/"+d+"/"+yy+" ";
        ret+=h+":"+mString+":"+sString;

        return ret;
    }

    public static String getTimeAndDate() {
        return getTimeAndDate( new Date() );
    }
}
