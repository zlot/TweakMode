package processing.mode.tweak;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class JComboBox_Example extends JPanel
                          implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	JLabel creatureBodyLabel;
	JLabel creatureLimbManagerLabel;
	JLabel creatureLabel;
	
	
	JComboBox creatureList;
	
    public JComboBox_Example() {
        
        ////* creature dropdown *////
        String[] creatureStrings = { "creature.virus.Virus", "creature.bacteria.Bacteria" };
        
        creatureList = new JComboBox(creatureStrings);
        creatureList.setName("creatureList");
        creatureList.setSelectedIndex(0);
        creatureList.addActionListener(this);
        
        //Set up the label (note: just used for spacing.);
        creatureLabel = new JLabel();
        creatureLabel.setPreferredSize(new Dimension(165, 40));
        
        
        
        ////* creature body dropdown *////
        String[] creatureBodyStrings = { "creature.virus.VirusBody", "creature.millipede.MillipedeBody", "creature.bacteria.BacteriaBody" };
        
        JComboBox creatureBodyList = new JComboBox(creatureBodyStrings);
        creatureBodyList.setName("creatureBodyList");
        creatureBodyList.setSelectedIndex(0);
        creatureBodyList.addActionListener(this);
        
        //Set up the label.
        creatureBodyLabel = new JLabel();
        creatureBodyLabel.setFont(creatureBodyLabel.getFont().deriveFont(Font.ITALIC));
        creatureBodyLabel.setHorizontalAlignment(JLabel.CENTER);
        updateLabel(creatureBodyStrings[creatureBodyList.getSelectedIndex()]);
        
        creatureBodyLabel.setPreferredSize(new Dimension(165, 40));
        
        ////* creature limbManager dropdown *////
        String[] creatureLimbManagerStrings = {"creature.virus.TentacleManager", "creature.bacteria.FeelerManager"};
        
        JComboBox creatureLimbManagerList = new JComboBox(creatureLimbManagerStrings);
        creatureLimbManagerList.setName("creatureLimbManagerList");
        creatureLimbManagerList.setSelectedIndex(0);
        creatureLimbManagerList.addActionListener(this);
        
        //Set up the label.
        creatureLimbManagerLabel = new JLabel();
        creatureLimbManagerLabel.setFont(creatureLimbManagerLabel.getFont().deriveFont(Font.ITALIC));
        creatureLimbManagerLabel.setHorizontalAlignment(JLabel.CENTER);
        updateLabel2(creatureLimbManagerStrings[creatureLimbManagerList.getSelectedIndex()]);
        
        //The preferred size is hard-coded to be the width of the
        //widest image and the height of the tallest image + the border.
        //A real program would compute this.
        creatureLimbManagerLabel.setPreferredSize(new Dimension(165, 40));

        //Layout
        add(creatureList);
        add(creatureLabel);
        add(creatureBodyList);
        add(creatureBodyLabel);
        add(creatureLimbManagerList);
        add(creatureLimbManagerLabel);
        
    }

    /** Listens to the combo box. */
    public void actionPerformed(ActionEvent e) {
    	
        JComboBox selectedComboBox = (JComboBox)e.getSource();
        
        // get value of selected item for creatureList.
        String creatureString = (String) creatureList.getSelectedItem();
        
        if(selectedComboBox.getName().equals("creatureBodyList")) {
	        String creatureBodyString = (String) selectedComboBox.getSelectedItem();
	        updateLabel(creatureBodyString);
	        // send osc message!
	        try {
				OSCSender.sendCreatureBody(creatureString, creatureBodyString, 9999);
			} catch (Exception e1) {
				System.out.println("error sending OSC message!");
			}
        
        }
        
        if(selectedComboBox.getName().equals("creatureLimbManagerList")) {
	        String creatureLimbManagerString = (String) selectedComboBox.getSelectedItem();
	        updateLabel2(creatureLimbManagerString);
	        
	        // send osc message!
	        try {
				OSCSender.sendCreatureLimbManager(creatureString,creatureLimbManagerString, 9999);
			} catch (Exception e1) {
				System.out.println("error sending OSC message!");
			}
        }
    }

    protected void updateLabel(String name) {
//        ImageIcon icon = createImageIcon("images/" + name + ".gif");
//        creatureBodyLabel.setIcon(icon);
//        creatureBodyLabel.setToolTipText("A drawing of a " + name.toLowerCase());
//        if (icon != null) {
            creatureBodyLabel.setText(name);
//        } 
            
    }
    protected void updateLabel2(String name) {
    	creatureLimbManagerLabel.setText(name);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = JComboBox_Example.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ComboBoxDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new JComboBox_Example();
        newContentPane.setOpaque(true); //content panes must be opaque
        
        newContentPane.setLayout(new BoxLayout(newContentPane, BoxLayout.Y_AXIS));
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}