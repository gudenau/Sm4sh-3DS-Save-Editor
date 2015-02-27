package com.gudenau.sm4sh.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.gudenau.sm4sh.editor.save.Save;
import com.gudenau.sm4sh.editor.util.FrameUtils;

public class MainFrame implements ActionListener {
	private JFrame frame;
	
	private JLabel currentFile;
	
	public JButton character;
	public JButton equipment;
	public JButton fix;
	
	private JMenuBar menuBar;
	
	private JMenu file;
	
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem close;
	
	private Save gameSave;
	private File currentAccount;
	private File currentSystem;
	
	public static BufferedImage icon;
	
	private JSpinner money;
	
	public MainFrame(LoadingScreen screen){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Throwable t){}
		
		try {
			Equipment.loadResources();
		} catch (Throwable e) {
			displayError("Error loading!");
			e.printStackTrace();
			return;
		}
		
		frame = new JFrame("Sm4sh 3DS Save Editor");
		
		frame.setIconImage(FrameUtils.icon);
		
		{
			menuBar = new JMenuBar();
			
			{
				file = new JMenu("File");
				
				open = new JMenuItem("Open");
				open.addActionListener(this);
				
				save = new JMenuItem("Save");
				save.addActionListener(this);
				
				close = new JMenuItem("Close");
				close.addActionListener(this);
				
				file.add(open);
				file.add(save);
				file.add(close);
			}
			
			menuBar.add(file);
		}
		
		{
			JPanel panel = new JPanel(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.insets = new Insets(2, 2, 2, 2);
			
			panel.add(new JLabel("Current save: "), constraints);
			
			constraints.gridx = 1;
			constraints.gridwidth = 3;
			currentFile = new JLabel("None");
			panel.add(currentFile, constraints);
			constraints.gridwidth = 1;
			
			constraints.gridx = 0;
			constraints.gridy = 1;
			character = new JButton("Characters");
			character.addActionListener(this);
			panel.add(character, constraints);
			
			constraints.gridx = 1;
			panel.add(new JLabel("Gold:"), constraints);
			
			constraints.gridx = 2;
			money = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
			panel.add(money, constraints);
			
			constraints.gridx = 3;
			equipment = new JButton("Equipment");
			equipment.addActionListener(this);
			panel.add(equipment, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 2;
			fix = new JButton("Fix save");
			fix.addActionListener(this);
			panel.add(fix, constraints);
			
			frame.add(panel);
		}
		
		updateCurrent(null);
		
		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		FrameUtils.center(frame);
		screen.close();
		frame.setVisible(true);
		
		displayError("This is an alpha, use at your own risk!\nThis means MAKE BACKUPS, this is as-is.");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == open){
			openFile();
		}else if(source == close){
			updateCurrent(null);
		}else if(source == save){
			saveFile();
		}else if(source == character){
			displayCharacterUnlocks();
		}else if(source == equipment){
			displayEquipment();
		}else if(source == fix){
			fixSave();
		}
	}

	private void saveFile() {
		if(gameSave != null && currentAccount != null && currentSystem != null){
			try{
				gameSave.save(currentAccount, currentSystem);
			}catch(Exception e){
				displayError("Saving", e);
			}
		}
	}

	private void fixSave() {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		
		fileChooser.setFileFilter(new FileFilter(){
			@Override
			public boolean accept(File file) {
				File parent = file.getParentFile();
				if(file.isDirectory()){
					return true;
				}
				
				switch(file.getName()){
				case "account_data.bin":
					return new File(parent, "system_data.bin").isFile() && file.isFile();
				case "system_data.bin":
					return new File(parent, "account_data.bin").isFile() && file.isFile();
				}
				
				return false;
			}

			@Override
			public String getDescription() {
				return "Sm4sh 3DS Save";
			}
		});
		
		switch(fileChooser.showOpenDialog(frame)){
		case JFileChooser.APPROVE_OPTION:
			try{
				RandomAccessFile source = new RandomAccessFile(fileChooser.getSelectedFile(), "r");
				source.seek(0x12);
				byte[] data = new byte[4];
				source.read(data);
				source.close();
				
				RandomAccessFile dest = new RandomAccessFile(currentAccount, "w");
				dest.seek(0x12);
				dest.write(data, 0, 4);
				dest.close();
				
				dest = new RandomAccessFile(currentSystem, "w");
				dest.seek(0x12);
				dest.write(data, 0, 4);
				dest.close();
			}catch(Throwable t){}
			return;
		}
	}

	private void displayEquipment() {
		frames.equipment = new EquipmentEditor(gameSave.equipment, this);
		equipment.setEnabled(false);
	}

	private void displayCharacterUnlocks() {
		frames.character = new CharacterUnlocks(gameSave.characters, this);
		character.setEnabled(false);
	}

	private void openFile() {
		if(frames.character != null || frames.equipment != null){
			displayError("You must close all other windows before loading a new save!");
			return;
		}
		
		JFileChooser fileChooser = new JFileChooser(new File("."));
		
		fileChooser.setFileFilter(new FileFilter(){
			@Override
			public boolean accept(File file) {
				File parent = file.getParentFile();
				if(file.isDirectory()){
					return true;
				}
				
				switch(file.getName()){
				case "account_data.bin":
					return new File(parent, "system_data.bin").isFile() && file.isFile();
				case "system_data.bin":
					return new File(parent, "account_data.bin").isFile() && file.isFile();
				}
				
				return false;
			}

			@Override
			public String getDescription() {
				return "Sm4sh 3DS Save";
			}
		});
		
		switch(fileChooser.showOpenDialog(frame)){
		case JFileChooser.APPROVE_OPTION:
			try{
				updateCurrent(fileChooser.getSelectedFile());
			}catch(Throwable t){
				updateCurrent(null);
			}
			return;
		}
	}

	private void updateCurrent(File file) {
		if(file == null){
			gameSave = null;
			currentAccount = null;
			currentSystem = null;
			currentFile.setText("none");
			money.setValue(0);
			money.setEnabled(false);
			equipment.setEnabled(false);
			
			character.setEnabled(false);
		}else{
			try{
				currentAccount = new File(file.getParentFile(), "account_data.bin");
				currentSystem = new File(file.getParentFile(), "system_data.bin");
				gameSave = Save.loadSave(currentAccount, currentSystem);
				currentFile.setText(file.getParentFile().getName());
				money.setValue(gameSave.money);
				money.setEnabled(true);
				equipment.setEnabled(true);
			
				character.setEnabled(true);
			}catch(Exception e){
				displayError("loading", e);
				updateCurrent(null);
			}
		}
	}
	
	public static class frames {
		public static EquipmentEditor equipment;
		public static CharacterUnlocks character;		
	}
	
	private void displayError(String string, Throwable caught) {
		try{
			File file = new File(new File("."), Long.toString(System.currentTimeMillis()) + ".log");
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			caught.printStackTrace(new PrintStream(out));
			out.close();
			
			displayError("There was an error while " + string + " please report it! The stack dump is in " + file.getAbsolutePath());
		}catch(Throwable t){
			displayError("There was an error displaying an error. :-(");
		}
	}
	
	private void displayError(String string) {
		JOptionPane.showMessageDialog(frame, string, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
}
