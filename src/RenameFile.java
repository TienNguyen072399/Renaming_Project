
import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class RenameFile {
	private String desti;
	private String extension;
	private String newName;
	private int count = 1;
	private File[] aList;
	private ArrayList<File> list;

	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public ArrayList<File> getList() {
		return list;
	}
	
	public String getDesti() {
		return desti;
	}
	
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	public String getNewName() {
		return newName;
	}
	
	public void setCount(int number) {
		count = number;
	}
	public int getCount() {
		return count;
	}
	
	public void fileChooser() {

		JFrame cFrame = new JFrame();
		
		//Makes sure chooser does everything right
		System.setProperty("apple.awt.fileDialogForDirectories", "false");

		//Creates the fileChooser
		FileDialog chooser = new FileDialog(cFrame, "Choose File(s)");
		chooser.setMultipleMode(true);
		chooser.setVisible(true);


		//Functions
		if(chooser.getFiles() != null) {
			//If Files are chosen turn, turn array into arraylist
			aList = chooser.getFiles();
			list = new ArrayList<File>(Arrays.asList(aList));
			}
		

		for(File f: list) {
			sop(f.getAbsolutePath());
		}
		
	}

	public void destChooser() {
		
		JFrame cFrame = new JFrame();
		
		//Makes sure chooser does everything right
		System.setProperty("apple.awt.fileDialogForDirectories", "true");

		//Creates the destinationChooser
		FileDialog chooser = new FileDialog(cFrame, "Choose File(s)");
		chooser.setMultipleMode(false);
		chooser.setVisible(true);
		
		//function
		if(chooser != null) {
		desti = chooser.getDirectory() + chooser.getFile() + "/";
		}
	}
	
	public void rename() throws FormatException, NoFilesException, NoNameException{
		//throws exception if there is no destination, no new name or no files to rename
		if(desti == null) {
			throw new FormatException("There is no destination for the files");
		}
		else if(newName == null) {
			throw new NoNameException("No new name to rename with");
		}
		else if(list == null) {
			throw new NoFilesException("There are no files to rename");
		}
		//No exceptions are found
		else {
			
			for(File f:list) {
				sop(count);
				File renamed = new File(desti + newName + " " + count + extension);
				f.renameTo(renamed);
				count++;
				sop(renamed);
			}
		}
	}

	public static void sop(Object x) {
		System.out.println(x);
	}

	public static void main(String[] args) {
		RenameFile r = new RenameFile();
		
		sop("START");
		if(r.list == null) {
			sop("List is empty");
		}
		sop("DONE");
	}
}
