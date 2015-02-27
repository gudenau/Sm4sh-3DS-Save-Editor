package com.gudenau.sm4sh.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.gudenau.sm4sh.editor.save.EquipmentSave;
import com.gudenau.sm4sh.editor.util.SortedComboBoxModel;

public class EquipmentEditor implements WindowListener, ListSelectionListener, ItemListener {
	private JFrame frame;
	private MainFrame main;
	
	private JComboBox<Equipment.Item> type;
	private JSpinner level;
	private JComboBox<Equipment.Bonus> bonus;
	
	private JList<EquipmentSave.Item> list;
	private JLabel icon;
	private JTextArea effect;
	
	private JSpinner attack;
	private JSpinner defence;
	private JSpinner speed;
	private JSpinner range;
	
	private int current = -1;
	
	public EquipmentEditor(EquipmentSave save, MainFrame main){
		frame = new JFrame("Equpment");
		frame.setIconImage(MainFrame.icon);
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.main = main;
		
		JPanel panel = new JPanel(new GridBagLayout());
		{
			GridBagConstraints constraints = new GridBagConstraints();
			
			list = new JList<EquipmentSave.Item>(save.equpment);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setSelectedIndex(0);
			list.addListSelectionListener(this);
			
			constraints.gridheight = 3;
			constraints.gridwidth = 1;
			constraints.gridx = 0;
			constraints.gridy = 0;
			JScrollPane jsp = new JScrollPane(list);
			jsp.setPreferredSize(new Dimension(290, 192));
			panel.add(jsp, constraints);
			
			{
				JPanel temp = new JPanel(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.anchor = GridBagConstraints.SOUTH;
				
				c.gridheight = 2;
				c.gridwidth = 1;
				c.gridx = 0;
				c.gridy = 0;
				
				icon = new JLabel();
				icon.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				temp.add(icon, c);
				
				c.gridheight = 1;
				c.gridx = 1;
				temp.add(new JLabel("Type"), c);
				c.gridx = 2;
				temp.add(new JLabel("Level"), c);
				
				c.gridy = 1;
				c.gridx = 1;
				c.anchor = GridBagConstraints.NORTH;
				type = new JComboBox<Equipment.Item>(new SortedComboBoxModel<Equipment.Item>());
				type.addItem(Equipment.invalid);
				for(Equipment.Item i : Equipment.equipment){
					if(i != null){
						type.addItem(i);
					}
				}
				
				temp.add(type, c);
				
				c.gridx = 2;
				level = new JSpinner(new SpinnerNumberModel(0, 0, 2, 1));
				temp.add(level, c);
				
				constraints.gridheight = 1;
				constraints.gridx = 1;
				panel.add(temp, constraints);
			}
			
			{
				JPanel temp = new JPanel(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				
				c.gridheight = 1;
				c.gridwidth = 1;
				c.gridx = 0;
				c.gridy = 0;
				
				temp.add(new JLabel("Attack"), c);
				
				c.gridx = 1;
				temp.add(new JLabel("Defence"), c);
				
				c.gridx = 2;
				temp.add(new JLabel("Speed"), c);
				
				c.gridx = 0;
				c.gridy = 1;
				
				attack = new JSpinner(new SpinnerNumberModel(0, Short.MIN_VALUE, Short.MAX_VALUE, 1));
				temp.add(attack, c);
				
				c.gridx = 1;
				defence= new JSpinner(new SpinnerNumberModel(0, Short.MIN_VALUE, Short.MAX_VALUE, 1));
				temp.add(defence, c);
				
				c.gridx = 2;
				speed = new JSpinner(new SpinnerNumberModel(0, Short.MIN_VALUE, Short.MAX_VALUE, 1));
				temp.add(speed, c);
				
				
				c.gridx = 0;
				c.gridy = 2;
				c.gridwidth = 2;
				temp.add(new JLabel("Bonus"), c);
				
				c.gridx = 2;
				c.gridwidth = 1;
				temp.add(new JLabel("Range"), c);
				
				c.gridx = 0;
				c.gridy = 3;
				c.gridwidth = 2;
				
				bonus = new JComboBox<Equipment.Bonus>(new SortedComboBoxModel<Equipment.Bonus>());
				bonus.addItem(Equipment.noBounus);
				bonus.addItemListener(this);
				
				for(Equipment.Bonus b : Equipment.bonus){
					if(b != null){
						bonus.addItem(b);
					}
				}
				temp.add(bonus, c);
				
				c.gridx = 2;
				c.gridwidth = 1;
				range = new JSpinner(new SpinnerNumberModel(0, 0, Byte.MAX_VALUE, Byte.MIN_VALUE));
				temp.add(range, c);
				
				constraints.gridy = 1;
				panel.add(temp, constraints);
			}
			
			{
				JPanel temp = new JPanel(new BorderLayout());
				temp.add(new JLabel("Bonus Effect", SwingConstants.CENTER), BorderLayout.NORTH);
				effect = new JTextArea("None");
				effect.setWrapStyleWord(true);
				effect.setLineWrap(true);
				effect.setOpaque(false);
				effect.setEditable(false);
				effect.setFocusable(false);
				effect.setBackground(UIManager.getColor("Label.background"));
				effect.setFont(UIManager.getFont("Label.font"));
				effect.setBorder(UIManager.getBorder("Label.border"));
				effect.setPreferredSize(new Dimension(230, 14*3));
				temp.add(effect, BorderLayout.CENTER);
				
				constraints.gridy = 2;
				panel.add(temp, constraints);
			}
		}
		
		refresh(0);
		
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		MainFrame.frames.equipment = null;
		main.equipment.setEnabled(true);
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
	public void valueChanged(ListSelectionEvent event) {
		refresh(list.getSelectedIndex());
	}

	private void refresh(int index) {
		try{
		if(current != -1){
			EquipmentSave.Item item = list.getModel().getElementAt(current);
			
			item.attack = (short) (((Number)attack.getValue()).shortValue());
			item.defence = (short) (((Number)defence.getValue()).shortValue());
			item.speed = (short) (((Number)speed.getValue()).shortValue());
			
			Equipment.Item item2 = ((Equipment.Item)type.getSelectedItem());
			
			item.category = (byte) (((Number)item2.type).intValue());
			item.id = (byte) (((Number)item2.id).intValue());
			
			item.range = (byte) (((Number)range.getValue()).intValue());
			item.bonus = (byte) ((Equipment.Bonus)bonus.getSelectedItem()).id;
			
			item.level = (byte) (((Number)level.getValue()).intValue());
		}
		
		current = index;
		
		EquipmentSave.Item item = list.getSelectedValue();
		
		if(item.id == -1){
			attack.setValue(0);
			defence.setValue(0);
			speed.setValue(0);
		
			type.setSelectedItem(Equipment.invalid);
		
			range.setValue(0);
			bonus.setSelectedItem(Equipment.noBounus);
			level.setValue(0);
		
			icon.setIcon(Equipment.other);
		}else{
			attack.setValue(item.attack);
			defence.setValue(item.defence);
			speed.setValue(item.speed);
		
			type.setSelectedItem(Equipment.equipment[item.id]);
		
			range.setValue(item.range);
			if(item.bonus == -1){
				bonus.setSelectedItem(Equipment.noBounus);
			}else{
				bonus.setSelectedItem(Equipment.bonus[item.bonus]);
			}
			
			level.setValue(item.level);
		
			icon.setIcon(Equipment.equipment[item.id].icon);
		}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Equipment.Bonus bonus = (Equipment.Bonus)this.bonus.getSelectedItem();
		
		if(bonus.id == -1){
			level.setEnabled(true);
			effect.setText("None");
		}else{
			level.setEnabled(false);
			level.setValue(2);
			effect.setText(bonus.effect);
		}
	}
}
