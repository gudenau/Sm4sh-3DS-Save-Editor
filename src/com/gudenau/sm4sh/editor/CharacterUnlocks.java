package com.gudenau.sm4sh.editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gudenau.sm4sh.editor.save.Character;

public class CharacterUnlocks implements WindowListener, ActionListener {

	private final Character character;
	private final MainFrame main;
	
	private JFrame frame;
	
	private JCheckBox ness;
	private JCheckBox falco;
	private JCheckBox wario;
	private JCheckBox lucina;
	private JCheckBox darkPit;
	private JCheckBox drMario;
	private JCheckBox rob;
	private JCheckBox ganondorf;
	private JCheckBox gameAndWatch;
	private JCheckBox bowserJr;
	private JCheckBox duckHunt;
	private JCheckBox jigglypuff;
	
	public CharacterUnlocks(Character character, MainFrame main) {
		this.character = character;
		this.main = main;
		
		frame = new JFrame("Characters");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		
		ness			= new JCheckBox("Ness"					, character.ness);
		falco			= new JCheckBox("Falco"					, character.falco);
		wario			= new JCheckBox("Waio"					, character.wario);
		lucina			= new JCheckBox("Lucina"				, character.lucina);
		darkPit			= new JCheckBox("Dark Pit"				, character.darkPit);
		drMario			= new JCheckBox("Dr. Mario"				, character.drMario);
		rob				= new JCheckBox("R.O.B."				, character.rob);
		ganondorf		= new JCheckBox("Ganondorf"				, character.ganondorf);
		gameAndWatch	= new JCheckBox("Mr. Game And Watch"	, character.gameAndWatch);
		bowserJr		= new JCheckBox("Bowser Jr."			, character.bowserJr);
		duckHunt		= new JCheckBox("Duck Hunt"				, character.duckHunt);
		jigglypuff		= new JCheckBox("Jiggypuff"				, character.jigglypuff);
		
		ness.addActionListener(this);
		falco.addActionListener(this);
		wario.addActionListener(this);
		lucina.addActionListener(this);
		darkPit.addActionListener(this);
		drMario.addActionListener(this);
		rob.addActionListener(this);
		ganondorf.addActionListener(this);
		gameAndWatch.addActionListener(this);
		bowserJr.addActionListener(this);
		duckHunt.addActionListener(this);
		jigglypuff.addActionListener(this);
		
		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(ness);
		panel.add(falco);
		panel.add(wario);
		panel.add(lucina);
		panel.add(darkPit);
		panel.add(drMario);
		panel.add(rob);
		panel.add(ganondorf);
		panel.add(gameAndWatch);
		panel.add(bowserJr);
		panel.add(duckHunt);
		panel.add(jigglypuff);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		MainFrame.frames.character = null;
		main.character.setEnabled(true);
		frame.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		character.ness			= ness.isSelected();
		character.falco			= falco.isSelected();
		character.wario			= wario.isSelected();
		character.lucina		= lucina.isSelected();
		character.darkPit		= darkPit.isSelected();
		character.drMario		= drMario.isSelected();
		character.rob			= rob.isSelected();
		character.ganondorf		= ganondorf.isSelected();
		character.gameAndWatch	= gameAndWatch.isSelected();
		character.bowserJr		= bowserJr.isSelected();
		character.duckHunt		= duckHunt.isSelected();
		character.jigglypuff	= jigglypuff.isSelected();
	}
}
