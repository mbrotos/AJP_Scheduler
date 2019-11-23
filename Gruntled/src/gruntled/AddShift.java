package gruntled;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*Adds a specified amount of shifts to the current jtable*/
public class AddShift
{
    int input = 0;//dialog input
    JOptionPane dialog = new JOptionPane();
    
    int create()
    {
        boolean digit = true;
        JPanel add = new JPanel();
        add.setLayout(new FlowLayout());

        add.add(new JLabel("Quantity:"));
        JTextField number = new JTextField(3);
        add.add(number);

        input = dialog.showConfirmDialog
                (null, add, "Add Shifts",
                        JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);//displays option dialog

        char[] shifts = number.getText().toCharArray();//stores number of shifts

            for(char c : shifts)
                if(!Character.isDigit(c))//checks if the input is a number
                {
                    digit = false;
                    break;
                }

            if(input == dialog.OK_OPTION && digit)
                return Integer.parseInt(number.getText());//returns the number of shifts
            else
                return 0;
    }
}