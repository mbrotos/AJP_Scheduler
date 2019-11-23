package gruntled;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table //Declares class Table
{
    JTable table = null; //Declares table variable
    JScrollPane bar = new JScrollPane(); //Scroll pane for table
    
        //Declares create method, takes in weekday, month, and day
        JScrollPane create(int weekday, int month, int day)
        {
            //Declares array of days in a month
            int[] days = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,
                    20,21,22,23,24,25,26,27,28,29,30,31};
            
            //Declares array of weekdays in a week
            String[] weekdays = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};
            
            //Declares array of months in a year
            String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug",
                    "Sept","Oct","Nov","Dec"};
       
            String[] header = new String[8]; //Array for header of table
            header[0] = "Worker"; //Sets first column for names of workers
            
            //Loop to set each column title
            for(int index = 1; index < header.length; index++)
            {
                header[index] = weekdays[weekday] + " " + 
                        months[month] + " " + days[day];
                
                //Reset weekday to Sunday if gone beyond Saturday
                weekday++;
                    if(weekday == weekdays.length)
                        weekday = 0;
                
                //Reset day to 1 if gone beyond max. days in certain month
                day++;
                    if((day == 27 && month == 1) || 
                       (day == days.length && month % 2 == 0) ||
                       (day == days.length - 1 && month % 2 == 1))
                    {
                        day = 0;
                        month++;
                    }
                    
                    if(month == months.length)
                        month = 0;
            }
            
            Object[][] data = new Object[0][8]; //Data, 47 rows, 8 columns
            //Table model to insert data and header
            DefaultTableModel model = new DefaultTableModel(data, header);
            table = new JTable(model); //Instantiates table with model
            table.setFont(new Font("Arial",Font.BOLD,17));
            
            setup(table);
            
            return bar; //Returns scrollbar pane
        }
        
        JScrollPane create(DefaultTableModel model)
        {
            table = new JTable(model);
            
            setup(table);
        
            return bar;
        }
        
        void setup(JTable table)
        {
            //Makes table's columns unable to be rearranged
            table.getTableHeader().setReorderingAllowed(false);
            //Colours every second row gray
            table.setDefaultRenderer(Object.class, 
                    new DefaultTableCellRenderer()
            {
                @Override
                public Component getTableCellRendererComponent(JTable table, 
                        Object value, boolean isSelected, boolean hasFocus, 
                        int row, int column)
                {
                    final Component c = super.getTableCellRendererComponent
                            (table, value, isSelected, hasFocus, row, column);
                    c.setBackground(row % 2 == 0 ? 
                            new Color(220,220,220) : Color.WHITE);
                    return c;
                }
            });
            //Keeps font black when row is selected
            table.setSelectionForeground(Color.BLUE);
            table.setSelectionBackground(Color.BLUE);
            bar.setViewportView(table); //Adds table to scrollbar pane
        }
}