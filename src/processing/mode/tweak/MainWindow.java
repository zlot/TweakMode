package processing.mode.tweak;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import oscP5.*;
import netP5.*;


@SuppressWarnings("serial")
public class MainWindow extends JPanel
                          implements ActionListener, ItemListener {
	
	
	JFrame frame;
	
	JLabel creatureBodyLabel;
	JLabel creatureLimbManagerLabel;
	JLabel creatureLabel;
	
	JLabel behaviourLabelOSCTest;
	
	
	JComboBox creatureList;
	JPanel checkBoxPanel;
	
	MyOSCReceiver myOSCReceiver;
	
	
	private static MainWindow singleton = new MainWindow(); // thread-safe.
	
	public static MainWindow getInstance() {
		return singleton;
	}
	
	
	public void initOSC() {
		myOSCReceiver = new MyOSCReceiver();
	}
	
	
/***** INNER CLASS OSC RECEIVER *****/	
	public class MyOSCReceiver {

		OscP5 oscP5;
		NetAddress myRemoteLocation;

		MyOSCReceiver() {
	       oscP5 = new OscP5(this,12001);
		}
		
		/* incoming osc message are forwarded to the oscEvent method. */
		public void oscEvent(OscMessage msg) {
			String type = msg.addrPattern();
			
			if(type.contains("/behaviour_list")) {
				String[] allBehaviours = new String[msg.arguments().length];
				
				for(int i=0;i<msg.arguments().length;i++) {
					allBehaviours[i] = msg.get(i).stringValue();
				}
				// recreate checkbox panel
				createCheckBoxPanel(allBehaviours);
			}
			
			
			/* sent by sketch when asked to provide all behaviours a creature is using */
			if(type.contains("/behaviours_of_creature")) {
				
				String[] creatureBehaviours = new String[msg.arguments().length];
				
				for(int i=0;i<msg.arguments().length;i++) {
					creatureBehaviours[i] = msg.get(i).stringValue();
				}
				
				for(String behaviourOfCreature : creatureBehaviours) {
					System.out.println(behaviourOfCreature);
				}
				
				// TODO::
				// tick the checkboxes that correlate to the name in creatureBehaviours[i].
				// and uncheck the ones that dont!
				// note: maybe uncheck ALL, then go through and tick as needed.
			}
			
		}	
		
	}
	
	
    private MainWindow() {
    	//Create and set up the window.
    	setOpaque(true); // content panes must be opaque.    	
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	createAndAddComponents();
    	
    	
        frame = new JFrame("ComboBoxDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        initOSC();
    }
    
    
    private void createAndAddComponents() {
        ////* creature dropdown *////
        String[] creatureStrings = {
        		"creature.virus.Virus",
        		"creature.bacteria.Bacteria",
        		"creature.millipede.Millipede",
        		"creature.squarething.SquareThing",
        		"creature.trianglething.TriangleThing",
        		"creature.worm.Worm",
        		"worldofcreatures$Car"
        	};
        
        creatureList = new JComboBox(creatureStrings);
        creatureList.setName("creatureList");
        creatureList.setSelectedIndex(0);
        creatureList.addActionListener(this);
        
        //Set up the label (note: just used for spacing.);
        creatureLabel = new JLabel();
        creatureLabel.setPreferredSize(new Dimension(165, 40));
        
        
        ////* creature body dropdown *////
        String[] creatureBodyStrings = {
        		"creature.virus.VirusBody",
        		"creature.bacteria.BacteriaBody",
        		"creature.millipede.MillipedeBody",
        		"creature.squarething.SquareThingBody",
        		"creature.trianglething.TriangleThingBody",
        		"creature.worm.WormBody"
    		};
        
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
        String[] creatureLimbManagerStrings = {
        		"creature.virus.TentacleManager",
        		"creature.bacteria.FeelerManager",
        		"null"
        		};
        
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

        //Layoutsss
        add(creatureList);
        add(creatureLabel);
        add(creatureBodyList);
        add(creatureBodyLabel);
        add(creatureLimbManagerList);
        add(creatureLimbManagerLabel);
        
        
        /* create checkBoxPanel, ready to be updated with behaviours in createCheckBoxPanel() */
        checkBoxPanel = new JPanel(new GridLayout(0, 1));
        // checkboxes
        JLabel behaviourLabel = new JLabel();
        behaviourLabel.setPreferredSize(new Dimension(100, 50));
        behaviourLabel.setText("Behaviours for: " + creatureList.getSelectedItem().toString());
        checkBoxPanel.add(behaviourLabel);
    	JCheckBox checkBox = new JCheckBox("temp begin thing only");
    	checkBox.setSelected(true);
    	checkBoxPanel.add(checkBox);
        add(checkBoxPanel);

    }
    
    
    
    private void createCheckBoxPanel(String[] allBehaviours) {
    	
    	remove(checkBoxPanel);
    	validate();
    	repaint();
    	
        checkBoxPanel = new JPanel(new GridLayout(0, 1));
        // checkboxes
        JLabel behaviourLabel = new JLabel();
        behaviourLabel.setPreferredSize(new Dimension(100, 50));
        behaviourLabel.setText("Behaviours for: " + creatureList.getSelectedItem().toString());
        //TODO:: must update this when creature change selected.
        checkBoxPanel.add(behaviourLabel);

        behaviourLabelOSCTest = new JLabel();
        behaviourLabelOSCTest.setText("This is label3");
        checkBoxPanel.add(behaviourLabelOSCTest);
        
 //////////////////// IM HERE.
  //////////////// WHAT NEEDS TO DO, is 
        ///////// on creature selection change on this end, send a msg to sketch,
        ///////// which will send a msg back here that contains the string names 
        ///////// of all behaviours on that creature!
        
        for(int i=0;i<allBehaviours.length;i++) {
        	JCheckBox checkBox = new JCheckBox(allBehaviours[i]);
        	checkBox.setSelected(true);
        	checkBox.addItemListener(this);
        	checkBoxPanel.add(checkBox);
        }
        
        add(checkBoxPanel);
        
        revalidate();
        repaint();
        frame.pack();
    }
    
    /** Listens to the check boxes. */
    public void itemStateChanged(ItemEvent e) {
        JCheckBox source = (JCheckBox) e.getItemSelectable();
        
        // get value of selected item for creatureList.
        String creatureString = (String) creatureList.getSelectedItem();
        
        // get string of behaviour from source
        String behaviourString = source.getText();

        
        //Now that we know which button was pushed, find out
        //whether it was selected or deselected.
        if (e.getStateChange() == ItemEvent.DESELECTED) {
        	// send osc msg to remove this behaviour
            try {
    			OSCSender.sendBehaviour(creatureString, behaviourString, false, 9999);
    		} catch (Exception e1) { System.out.println("error sending OSC message!"); }
        } else {
        	// WAS CHECKED.
        	// send osc msg to add this behaviour
            try {
    			OSCSender.sendBehaviour(creatureString, behaviourString, true, 9999);
    		} catch (Exception e1) { System.out.println("error sending OSC message!"); }
        }
        

    }
    

    /** Listens to the combo box. */
    public void actionPerformed(ActionEvent e) {
    	
        JComboBox selectedComboBox = (JComboBox)e.getSource();
        
        // get value of selected item for creatureList.
        String creatureString = (String) creatureList.getSelectedItem();
        
        
        /* used to send a msg to sketch requesting the behaviours a creature is using */
        if(selectedComboBox.getName().equals("creatureList")) {
        	try {
        		OSCSender.sendCreatureBehavioursRequest(creatureString, 9999);
        	} catch (Exception e1) {
        		System.out.println("error sending OSC message!");
        	}
        	
        }
        
        
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
    protected void updateLabel3(String name) {
    	behaviourLabelOSCTest.setText(name);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainWindow.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}