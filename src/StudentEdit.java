import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentEdit implements ActionListener
{
    public JLabel label = new JLabel("Student Edit");
    public JTextField name = new JTextField();
    public JTextField competeDiv = new JTextField();
    public JTextField actualDiv = new JTextField();
    public JTextField school = new JTextField();
    public JTextField num = new JTextField();

    public JButton doneButton = new JButton("Done");

    public JFrame frame = new JFrame();

    boolean editing;
    
    public StudentEdit(boolean edit) {
        
        frame.setLocationRelativeTo(null);

        editing = edit;
        
        if(editing) label.setText("Student Edit");
        else label.setText("Student Creation");

        frame.setMinimumSize( new Dimension( 400, 400 ) );

        name.setText("Name");
        competeDiv.setText("Compete Division");
        actualDiv.setText("Actual Division");
        school.setText("School");
        num.setText("Number");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(7, 1));

        panel.add(label);

        panel.add(name);
        panel.add(competeDiv);
        panel.add(actualDiv);
        panel.add(school);
        panel.add(num);

        panel.add(doneButton);

        doneButton.addActionListener(this);
        doneButton.setActionCommand("Done");

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(Data.runner != null) {
            if(!editing) {
                String sName = name.getText();
                String sComp = competeDiv.getText();
                String sAct = actualDiv.getText();
                String sSch = school.getText();
                String sNum = num.getText();

                Student newS = new Student();
                newS.name = sName;
                newS.competeDiv = sComp;
                newS.actualDiv = sAct;
                newS.school = sSch;
                newS.num = sNum;

                Data.runner.sHandler.addStudent(newS);
                out(sName + " Added Successfully");
            } else {
                String sName = name.getText();
                String sComp = competeDiv.getText();
                String sAct = actualDiv.getText();
                String sSch = school.getText();
                String sNum = num.getText();
                
                Student newS = Data.runner.sHandler.getByName(sName);
                
                if(newS == null) {
                    out("Student not found");
                    return;
                }
                
                newS.competeDiv = sComp;
                newS.actualDiv = sAct;
                newS.school = sSch;
                newS.num = sNum;
                
                out(sName + " Modified successfully");
            }
        } else {
            System.out.println("[GUI] RUNNER NOT FOUND");
        }
    }
    
    public void out(String s) { Data.runner.out(s); }
}
