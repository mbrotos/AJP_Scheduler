package gruntled;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.CodeSource;
import javax.swing.JTable;
import javax.swing.table.TableModel;
    /*Exports the the current table opened into a comma separated filed with the 
      personalized exstension .AJP*/
public class ExportToAJP 
{   //requires the current jtable the current tab name and the path to export to
    void exportToAJP(JTable tableToExport, String tabName, String pathToExportTo) throws IOException 
        {
            BufferedWriter out;//defines a writer
            TableModel model = tableToExport.getModel();
            
            CodeSource codeSource = ExportToAJP.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(URLDecoder.decode(codeSource.getLocation().getPath(), "UTF-8"));
            pathToExportTo = jarFile.getParentFile().getPath();
            //creates new save in the install dir/saves folder
            out = new BufferedWriter(new FileWriter(pathToExportTo+"\\saves\\"+tabName+".ajp"));
            
                try 
                {       //writes the tabled headers to the .ajp
                        for(int i=0; i < model.getColumnCount(); i++) {
                            if (i!=model.getColumnCount()-1)
                                out.write(model.getColumnName(i) + ",");
                            else
                                out.write(model.getColumnName(i));
                        }
                    out.write("\n");//new line
                        //cycles each cell after the headers and writes to the csv file
                        for(int i=0; i< model.getRowCount(); i++) {
                                for(int j=0; j < model.getColumnCount(); j++) {
                                    if (model.getValueAt(i,j)!= null)//checks for empty cell
                                    {
                                        if (j!=model.getColumnCount()-1)
                                            out.write(model.getValueAt(i,j).toString()+",");
                                        else
                                            out.write(model.getValueAt(i,j).toString());
                                    }
                                    else if (j!=model.getColumnCount()-1)
                                        out.write(",");
                                }
                            out.write("\n");
                        }
                } catch (IOException e) {}
                finally //checks if the writter worked
                {
                    try {
                        if(out != null){
                            out.close();//closes file for deletion and other manipulation
                        } else {
                            System.out.println("Buffer has not been initialized!");
                        }
                    } catch (IOException e) {}
                }
        }
}
