import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDelete implements ActionListener
{
    public JLabel label = new JLabel("Student Deletion");
    public JTextField name = new JTextField();

    public JButton doneButton = new JButton("Done");

    public JFrame frame = new JFrame();

    public StudentDelete() {
        
        frame.setLocationRelativeTo(null);

        frame.setMinimumSize( new Dimension( 400, 400 ) );

        name.setText("Name");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(3, 1));

        panel.add(label);

        panel.add(name);
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
            String sName = name.getText();
            
            Student toDel = Data.runner.sHandler.getByName(sName);
            
            if(toDel == null) {
                out("Student not found");
                return;
            }
            
            Data.runner.sHandler.deleteStudent(toDel);
        } else {
            System.out.println("[GUI] RUNNER NOT FOUND");
        }
    }

    public void out(String s) { Data.runner.out(s); }
}
