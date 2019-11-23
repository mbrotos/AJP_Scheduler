package gruntled;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class About 
{
    JOptionPane dialog = new JOptionPane();//creates a new option dialog
    
    void create(JFrame main)
    {
        //creates a new window with a Jpanel
        JPanel frame = new JPanel();
        JPanel about = new JPanel();
        
        //sets the layout
        about.setLayout(new BoxLayout(about, BoxLayout.Y_AXIS));
        //adds developed by, developers and version information to the window
        about.add(Box.createRigidArea(new Dimension(0,5)));
        about.add(new JLabel("Developed by: Gruntled Inc."));
        about.add(new JLabel("Developed for: Eagle's Nest Golf Club"));
        about.add(Box.createRigidArea(new Dimension(0,20)));
        about.add(new JLabel("Developers:"));
        about.add(Box.createRigidArea(new Dimension(0,3)));
        about.add(new JLabel("Philip Cappello | philip.cappello18@ycdsbk12.ca"));
        about.add(new JLabel("Julian Kuyumcu | julian.kuyumcu18@ycdsbk12.ca"));
        about.add(new JLabel("Adam Sorrenti | adam.sorrenti18@ycdsbk12.ca"));
        about.add(Box.createRigidArea(new Dimension(0,7)));
        about.add(new JLabel("Version: 1.0.3"));
        //defines and sets the eagles image from the programs resources
        ImageIcon eagle = new ImageIcon(getClass().getResource("/resources/5295.png"));
        
        frame.add(about);//adds japnel
        frame.add(new JLabel(eagle));//dates the logo
        frame.setLayout(new FlowLayout());//sets main layout
        frame.setVisible(true);//sets visablity
        
        //displays the optionpane
        dialog.showConfirmDialog(null, frame, "About",
                                JOptionPane.PLAIN_MESSAGE,
                                        JOptionPane.DEFAULT_OPTION);
    }
}