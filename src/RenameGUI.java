
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

public class RenameGUI extends JFrame{

	RenameFile r;
	ArrayList<File> list;

	JPanel filesPanel;
	JButton fileSelect;
	JButton destination;
	JButton start;

	JPanel controlPanel;
	String[]extensions = {".jpg", ".png", ".mp4", ".mov"};
	JComboBox exList;
	JTextField newName;
	JTextField number;
	JTextField directory;

	JPanel emptyRight;
	JPanel emptyBottom;

	JTextArea textScreen;
	JScrollPane scroll;

	JMenuItem addFiles;
	JMenuItem clear;
	JMenu file;
	JMenu edit;
	JMenuBar menuBar;




	public RenameGUI() {
		super("Rename App");

		r = new RenameFile();
		list = new ArrayList<File>();


		//menu items unders files
		addFiles = new JMenuItem ("New File(s)");
		clear = new JMenuItem ("Clear");
		clear.setMnemonic(KeyEvent.VK_C);

		//files menu setup
		file = new JMenu("File");
		file.add(addFiles);
		//file.setMnemonic(KeyEvent.VK_F);

		//edit menu setup
		edit = new JMenu("Edit");
		edit.add(clear);
		edit.setMnemonic(KeyEvent.VK_C);

		//toolbar setup
		menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(edit);


		//filesPanel setup
		filesPanel = new JPanel();
		//destination = new JButton("Direc.");
		//fileSelect = new JButton("File(s)");


		filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.PAGE_AXIS));
		//filesPanel.add(destination);
		//filesPanel.add(fileSelect);
		//filesPanel.add(start);

		//controlPanel setup
		controlPanel = new JPanel();

		start = new JButton("Start");

		directory = new JTextField(15);
		directory.setEditable(false);	
		destination = new JButton("...");

		newName = new JTextField(15);
		number = new JTextField(3);
		exList = new JComboBox(extensions);
		r.setExtension((String)exList.getSelectedItem());

		controlPanel.add(start);
		controlPanel.add(directory);
		controlPanel.add(destination);
		controlPanel.add(newName);
		controlPanel.add(number);
		controlPanel.add(exList);

		//Button Functions
		ButtonListener b = new ButtonListener();
		//fileSelect.addActionListener(b);
		destination.addActionListener(b);
		newName.addActionListener(b);
		number.addActionListener(b);
		exList.addActionListener(b);
		start.addActionListener(b);
		addFiles.addActionListener(b);
		clear.addActionListener(b);

		//Something to fill the right side and bottom
		//emptyRight = new JPanel();
		emptyBottom = new JPanel();
	
		//textScreen setup
		textScreen = new JTextArea();
		textScreen.setLineWrap(true);
		textScreen.setEditable(false);
		
		//allows scrolling in textScreen
		scroll = new JScrollPane(textScreen,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	

		//Sets the size and location of app on starup
		setSize(700,500);
		super.setLocationRelativeTo(null);
		setResizable(false);

		//Layout Management
		this.setLayout(new BorderLayout());

		this.setJMenuBar(menuBar);
		this.add(controlPanel, BorderLayout.NORTH);
//		this.add(emptyBottom, BorderLayout.SOUTH);
		this.add(filesPanel, BorderLayout.WEST);
//		this.add(textScreen, BorderLayout.CENTER);
		this.add(scroll, BorderLayout.CENTER);
		//this.add(empty, BorderLayout.SOUTH);


		//Makes x button work
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}

	//To make all the buttons work
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addFiles) {
				r.fileChooser();
				//Adds selected files into the text screen
				if(r.getList() != null){
					textScreen.append("Files to be renamed: \n");
					for(File f:r.getList()) {
						textScreen.append(f.getName() + "\n");
					}
					textScreen.append("========================================\n");
				}

			}
			else if (e.getSource() == destination) {
				r.destChooser();
				//Adds destination into the text screen
				if(r.getDesti() != null) {
					textScreen.append("Destination: " + r.getDesti() + 
							"\n========================================\n");
					directory.setText(r.getDesti());

				}
			}
			else if (e.getSource() == exList) {
				r.setExtension((String)exList.getSelectedItem());
				//System.out.println((String)exList.getSelectedItem());
			}
//			else if(e.getSource() == newName) {
//				r.setNewName(newName.getText());
//				//Adds newName into the text screen
//				textScreen.append("Setting new name to: " + r.getNewName() + 
//						"\n========================================\n");
//				//clears the newName box
//				//newName.setText("");
//			}
//			else if (e.getSource() == number) {
//				if(number.getText().length() > 0) {
//					r.setCount(Integer.parseInt(number.getText()));
//					textScreen.append("Setting starting number to: " + r.getCount() +
//							"\n========================================\n");
//				}
//			}
			else if(e.getSource() == start) {
				//sets the count
				if(number.getText().length() > 0) {
					r.setCount(Integer.parseInt(number.getText()));
				}
				int count = r.getCount();
				//sets new name
				r.setNewName(newName.getText());
				//Rename if possible
				try {
					//Adds "Renaming Old File to New File" into the textscreen
					for(File f:r.getList()) {
						textScreen.append("Renaming " + f.getName() + " to " + r.getNewName() + 
								" " + count + " " + r.getExtension() + "\n");
						count++;
					}
					r.rename();
					newName.setText("");
					number.setText("");

					r.setCount(1);
					textScreen.append("========================================\n Reseting count to: 1" + 
							"\n========================================\n");

				} 
				//Stops running rename if a an exception occurs
				catch(Exception ex) {
					javax.swing.JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
				}
			}

			else if (e.getSource() == clear) {
				//clears the textScreen
				textScreen.setText("");
			}
		}
	}

	public static void main(String[] args) {
		RenameGUI start = new RenameGUI();
	}


}
