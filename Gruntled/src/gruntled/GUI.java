package gruntled;

import au.com.bytecode.opencsv.CSVReader;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public final class GUI extends WindowAdapter implements ActionListener
{
    public JFrame main = null;//main JFrame
    JTabbedPane tab = null;//main tabs
    JPanel header = null;//JPanel for buttons
    //JButtons
    JButton create = null;
    JButton open = null;
    JButton add = null;
    JButton remove = null;
    JButton delete = null;
    JButton save = null;
    JButton print = null;
    //Menu Bar items
    MenuItem saveItem = null;
    MenuItem printItem = null;
    MenuItem exitItem = null;
    MenuItem addShiftsItem = null;
    MenuItem deleteItem = null;
    MenuItem removeItem = null;
    MenuItem about = null;
    //Used in delete funtion
    FileReader fr = null;
    CSVReader csvfr = null;
    
        void create()
        {
            //Main JFrame Setup
            main = new JFrame("AJP Scheduler");
            main.setIconImage(new ImageIcon(getClass().getResource("/resources/favicon.png")).getImage());
            main.setSize(1600,900);
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setLocationRelativeTo(null);
            main.addWindowListener(this);
            main.setLayout(new BoxLayout(main.getContentPane(), BoxLayout.Y_AXIS));
            main.setVisible(true);
            
                try //sets the look and feel of the programs interface
                {
                    UIManager.setLookAndFeel
                        (UIManager.getSystemLookAndFeelClassName());
                }catch (Exception ex) {}
            //creates a header panel to hold the main buttons 
            header = new JPanel();
            header.setLayout(new FlowLayout());
            //Creates the main JFrame menu bar and adds the three menus
            MenuBar menubar = new MenuBar();
            Menu file = new Menu("File");
            Menu edit = new Menu("Edit");
            Menu help = new Menu("Help");
            //setup for the file menu in the menubar
            saveItem = new MenuItem("Save Schedule");
            saveItem.addActionListener(this);
            deleteItem = new MenuItem("Delete Schedule");
            deleteItem.addActionListener(this);
            printItem = new MenuItem("Print");
            printItem.addActionListener(this);
            exitItem = new MenuItem("Exit");
            exitItem.addActionListener(this);
            
            file.add(saveItem);
            file.add(deleteItem);
            file.add(printItem);
            file.add(exitItem);
            //setup for the edit menu in the menu bar
            removeItem = new MenuItem("Remove Shifts");
            removeItem.addActionListener(this);
            addShiftsItem = new MenuItem("Add Shifts");
            addShiftsItem.addActionListener(this);
            
            edit.add(addShiftsItem);
            edit.add(removeItem);
            
            //setup for the about menun in the menu bar
            about = new MenuItem("About");
            about.addActionListener(this);
            
            help.add(about);
            
            menubar.add(file);
            menubar.add(edit);
            menubar.add(help);
            main.setMenuBar(menubar);//adds the menu bat to the main jframe
            
            //Button and action listener setup
            create = new JButton("Create Schedule");
            create.addActionListener(this);
            create.setFocusPainted(false);
            header.add(create);
            
            open = new JButton("Open Schedule");
            open.addActionListener(this);
            open.setFocusPainted(false);
            header.add(open);
            
            add = new JButton("Add Shifts");
            add.addActionListener(this);
            add.setFocusPainted(false);
            header.add(add);
            
            remove = new JButton("Remove Shifts");
            remove.addActionListener(this);
            remove.setFocusPainted(false);
            header.add(remove);
            
            delete = new JButton("Delete Schedule");
            delete.addActionListener(this);
            delete.setFocusPainted(false);
            header.add(delete);

            save = new JButton("Save Schedule");
            save.addActionListener(this);
            save.setFocusPainted(false);
            header.add(save);
            
            print = new JButton("Print");
            print.addActionListener(this);
            print.setFocusPainted(false);
            header.add(print);
 
            main.add(header);//adds the button panel to the jframe
            
            //setup for the main jtabbedpane
            tab = new JTabbedPane();
            tab.setRequestFocusEnabled(false);
            tab.setPreferredSize(new Dimension(1600,800));
            main.add(tab);
            
            //Runs the main JFrame invoke thread
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    SwingUtilities.updateComponentTreeUI(main);
                }
            });
        }
        JTable getTable()//gets the table component from the current tab selected
        {
            JScrollPane scrollPane = (JScrollPane)tab.getSelectedComponent(); 
            JViewport viewport = scrollPane.getViewport(); 
            return (JTable)viewport.getView();
        }
        String openDir()//returns the choosen .ajp file path 
        {
            String pathToExportTo = null;
                try
                {   //opens in the saves folder
                    CodeSource codeSource = Delete.class.getProtectionDomain().getCodeSource();
                    File jarFile = new File(URLDecoder.decode(codeSource.getLocation().getPath(), "UTF-8"));
                    pathToExportTo = jarFile.getParentFile().getPath();
                }
                catch(Exception ex){}   
            return pathToExportTo;
        }
        //opens the choosen .ajp file in the scheduler
        void openAJP(File selected) throws FileNotFoundException, IOException
        {
            Table table = new Table();
            Object[] headerCSV;
            fr = new FileReader(selected.getPath());
            csvfr = new CSVReader(fr);//reads the csv file 
            ArrayList myEntries = (ArrayList) csvfr.readAll();//stores in arraylist
            headerCSV = (String[]) myEntries.get(0);
            DefaultTableModel tableModel;
            tableModel = new DefaultTableModel(headerCSV, myEntries.size()-1);
            int rowcount = tableModel.getRowCount();
                //cycles array list and adds each cell to a table
                for (int x = 0; x<rowcount+1; x++)
                {
                    int columnnumber = 0;
                    // if x = 0 this is the first row...skip it... data used for columnnames
                    if (x>0)
                    {
                        for (String thiscellvalue : (String[])myEntries.get(x))
                        {
                            tableModel.setValueAt(thiscellvalue, x-1, columnnumber);
                            columnnumber++;
                        }
                    }
                }
            //calls onto the table class adding the tab to a new tab in the main jframe
            tab.add(table.create(tableModel), selected.getName().substring(0, selected.getName().lastIndexOf('.')));
        }
        void print()//opens a print dialog for the current table which is selected
        {
            JTable mytable = getTable(); //gets the current table
                try 
                {
                    /* print the table */
                    boolean complete = mytable.print(JTable.PrintMode.FIT_WIDTH, 
                            null, null, true, null, false, null);//opens print dialog 

                        /* if printing completes */
                        if (complete)
                            /* show a success message */
                            JOptionPane.showMessageDialog(main,
                                    "Printing Complete", "Printing Result",
                                    JOptionPane.INFORMATION_MESSAGE);
                        else 
                            //show a message indicating that printing was cancelled
                            JOptionPane.showMessageDialog(main,
                                    "Printing Cancelled","Printing Result",
                                    JOptionPane.INFORMATION_MESSAGE);
                } 
                catch (PrinterException pe) 
                {
                    /* Printing failed, report to the user */
                    JOptionPane.showMessageDialog(main,
                            "Printing Failed: " + pe.getMessage(),
                            "Printing Result", JOptionPane.ERROR_MESSAGE);
                }
        }
        //handles all the actionPerformed listeners for the main JFrame
        @Override
        public void actionPerformed(ActionEvent e)
        {            
            Object action = e.getSource();//stores the component which the action was performed on   
            FileFilter filter = new FileNameExtensionFilter("AJP File", "ajp");//file chooser setup
            JFileChooser chooser = new JFileChooser(openDir()+"\\saves\\");
                
                chooser.addChoosableFileFilter(filter);
                chooser.setFileFilter(filter);
            
                    if(action==this.create)//handles the create schedule action listener
                    {
                        CreateSchedule createSchedule = new CreateSchedule();
                        createSchedule.create();
                            if(createSchedule.input == createSchedule.dialog.OK_OPTION)
                            {
                                tab.addTab(createSchedule.tab, createSchedule.table);
                            }
                    }
                    else if(action==this.open)//handles the open schedule action listener
                    {
                        int input = chooser.showOpenDialog(main);
                            try 
                            {
                                if(input == JFileChooser.APPROVE_OPTION)
                                    this.openAJP(chooser.getSelectedFile());
                            } 
                            catch (IOException ex) {}
                    }    
                    //handles the add shifts action listener
                    else if(action==this.add || action==this.addShiftsItem) 
                    {
                        if(tab.getTabCount() != 0)
                        {
                            AddShift addShift = new AddShift();
                            int rows = addShift.create();
                            JTable myTable = getTable();
                            DefaultTableModel model = (DefaultTableModel)myTable.getModel();

                                for(int row = 0; row < rows; row++)
                                    model.addRow(new String[8]);
                        }
                    }  
                    //handles the remove shifts action listener
                    else if(action==this.remove || action==this.removeItem)
                    { 
                        try{
                            JTable table = getTable();
                            DefaultTableModel model = (DefaultTableModel)table.getModel();
                            int startRow = table.getSelectedRow();
                            int endRow = table.getSelectedRowCount();

                                for(int row = startRow; row < startRow + endRow; row++)
                                    model.removeRow(startRow);
                        }catch(NullPointerException ex){}
                    }
                    //handles the delete schedule action listener
                    else if(action==this.delete || action==this.deleteItem)
                    {
                        try{
                            Delete del = new Delete();
                            del.delete(tab, tab.getSelectedIndex(), fr, csvfr);
                        }catch(Exception ex){}
                    }
                    //handles the save schedule action listener
                    else if(action==this.save || action==this.saveItem)
                    {
                        ExportToAJP run = new ExportToAJP();
                        try
                        {
                            JTable mytable = getTable();
                                try
                                {
                                    run.exportToAJP(mytable, tab.getTitleAt(tab.getSelectedIndex()), null);
                                }
                            catch(IOException ex){} 
                        }catch(NullPointerException ex){} 
                    }
                    //handles the save schedule action listener
                    else if(action==this.print || action==this.printItem)
                        try{
                            print();
                        }catch(NullPointerException ex){}
                    //handles the about action listener
                    else if(action == this.about)
                    {
                        About info = new About();
                        info.create(main);
                    }     
                    //handles the exit action listener
                    else if(action == this.exitItem)
                        System.exit(0);
                      
        }
}