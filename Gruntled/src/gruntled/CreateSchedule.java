package gruntled;

import javax.swing.*;

public class CreateSchedule //Declares CreateSchedule class
{
    String tab = null; //Declares tab for schedule
    JScrollPane table = new JScrollPane(); //Declares scroll pane
    Table schedule = new Table(); //Declares table
    JOptionPane dialog = new JOptionPane();
    int input = 0;
    
        void create() //Declares create method
        {
            //Declares array of days in a month
            Object[] days = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,
                    20,21,22,23,24,25,26,27,28,29,30,31};
            
            //Declares array of weekdays in a week
            String[] weekdays = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};
            
            //Declares array of months in a year
            String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug",
                    "Sept","Oct","Nov","Dec"};

            JPanel create = new JPanel(); //Declares JPanel to hold dialog
            //Sets layout of panel to keep all components in middle
            create.setLayout(new BoxLayout(create, BoxLayout.Y_AXIS));
            
            JPanel date = new JPanel(); //Declares JPanel to hold date
            //Sets layout of panel to keep all components in middle
            date.setLayout(new BoxLayout(date, BoxLayout.Y_AXIS));
            
            JPanel name = new JPanel(); //Declares JPanel to hold name of tab
            name.add(new JLabel("Schedule Name:"));
            JTextField title = new JTextField(15); //Text field for name
            name.add(title);
            create.add(name);
            
            JPanel week = new JPanel(); //Declares JPanel to starting weekday
            week.add(new JLabel("Weekday:"));
            //Dropdown menu for weekdays
            JComboBox weekday = new JComboBox(weekdays);
            week.add(weekday);
            date.add(week);
            
            JPanel year = new JPanel(); //Declares JPanel for starting month
            year.add(new JLabel("Month:"));
            //Dropdown menu for months
            JComboBox month = new JComboBox(months);
            year.add(month);
            date.add(year);
            
            JPanel day = new JPanel(); //Declares JPanel for starting day
            day.add(new JLabel("Day:"));
            //Dropdown menu for days
            JComboBox number = new JComboBox(days);
            day.add(number);
            date.add(day);
            
            //Creates border around date parameters
            date.setBorder(BorderFactory.createTitledBorder("Start Date"));
            create.add(date);
            
            //Creates dialog window
            input = dialog.showConfirmDialog
                    (null, create, "Create Schedule", 
                            JOptionPane.OK_CANCEL_OPTION, 
                                    JOptionPane.PLAIN_MESSAGE);
            
                if(input == dialog.OK_OPTION)
                {
                    tab = title.getText(); //Gets inputted title
                    //Creates table with inputted date parameters
                    table = schedule.create(weekday.getSelectedIndex(),
                            month.getSelectedIndex(), number.getSelectedIndex());
                }
        }
}