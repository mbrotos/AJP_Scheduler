package gruntled;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import au.com.bytecode.opencsv.CSVReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.CodeSource;

public class Delete
{
    int input = 0;//dialog input
    JOptionPane dialog = new JOptionPane();
    
    //requires the main tab and the readers if its an opened schedule
    void delete(JTabbedPane tab, int index, FileReader fr, CSVReader csvfr) throws ArrayIndexOutOfBoundsException, UnsupportedEncodingException
    {
        final String title;
            try
            {
                 title = tab.getTitleAt(index);//gets the selected tables name
            }catch(ArrayIndexOutOfBoundsException ex){
                return;
            } 

        JPanel delete = new JPanel();//creates dialog 
        delete.add(new JLabel("Delete source file?"));

        input = dialog.showConfirmDialog
                (null, delete, "Delete Schedule",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);//displays and handles options

            if(input != dialog.CANCEL_OPTION)
            {
                tab.remove(tab.getSelectedComponent());//deltes tab if sources is wanted
            }

            if(input == dialog.YES_OPTION)//deletes source file and tabl
            {
                CodeSource codeSource = Delete.class.getProtectionDomain().getCodeSource();//get .jar location on drive
                File jarFile = new File(URLDecoder.decode(codeSource.getLocation().getPath(), "UTF-8"));
                String pathToExportTo = jarFile.getParentFile().getPath();
                File file = new File(pathToExportTo+"\\saves\\"+ title + ".ajp");//grabs file from the saves dir
                    try //trys to close opened schedule upon deletion 
                    {
                        if(fr != null)
                            fr.close();
                        if(csvfr != null)
                            csvfr.close();
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
                    }
                file.delete();//deleltes source file
            }
    }
}
