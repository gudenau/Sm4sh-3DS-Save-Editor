package com.gudenau.sm4sh.editor;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Equipment {

	public static Bonus noBounus;
	public static Item[] equipment;
	public static Bonus[] bonus;
	public static BufferedImage otherImage;
	public static ImageIcon other;
	public static Item invalid;
	
	public static void loadResources() throws Throwable {
		otherImage = ImageIO.read(Equipment.class.getResource("/res/images/equipment/other.png"));
		other = new ImageIcon(otherImage);
		
		invalid = new Item(-1, 0, "Invalid", "Invalid", "Invalid", "Invalid");
		noBounus = new Bonus(-1, "None", "");
		
		equipment = new Item[0xFF];
		bonus = new Bonus[0xFF];
		
		BufferedReader in = new BufferedReader(new InputStreamReader(Equipment.class.getResourceAsStream("/res/text/equipment.txt")));
		
		for(String line = in.readLine(); line != null; line = in.readLine()){
			if(line.isEmpty() || line.startsWith("#")){
				continue;
			}
			
			String[] split = line.split(":");
			
			if(split.length != 6){
				throw new Exception();
			}
			
			int type = Integer.parseInt(split[0], 16);
			int id = Integer.parseInt(split[1], 16);
			
			Item item = new Item(type, id, split[2], split[3], split[4], split[5]);
			equipment[id] = item;
		}
		
		in.close();
		
		in = new BufferedReader(new InputStreamReader(Equipment.class.getResourceAsStream("/res/text/bounus.txt")));
		
		for(String line = in.readLine(); line != null; line = in.readLine()){
			if(line.isEmpty() || line.startsWith("#")){
				continue;
			}
			
			String[] split = line.split(":");
			
			if(split.length != 3){
				throw new Exception();
			}
			
			int id = Integer.parseInt(split[0], 16);
			
			bonus[id] = new Bonus(id, split[1], split[2]);
		}
	}
	
	public static class Bonus implements Comparable<Bonus> {
		public final int id;
		public final String name;
		public final String effect;
		
		public Bonus(int id, String name, String effect) {
			this.id = id;
			this.name = name;
			this.effect = effect;
		}
		
		@Override
		public String toString(){
			return name;
		}

		@Override
		public int compareTo(Bonus o) {
			if(o == null){
				return 0;
			}
			
			if(o == noBounus){
				return 1;
			}
			if(this == noBounus){
				return -1;
			}
			
			return name.compareTo(o.name);
		}
		
	}

	public static class Item implements Comparable<Item> {

		public final int type;
		public final int id;
		public final String name;
		public final String level1;
		public final String level2;
		public final String level3;
		
		public final BufferedImage image;
		public final ImageIcon icon;
		
		public Item(int type, int id, String name, String level1, String level2, String level3) throws Throwable {
			this.type = type;
			this.id = id;
			this.name = name;
			this.level1 = level1;
			this.level2 = level2;
			this.level3 = level3;
			
			if(id != -1){
				image = ImageIO.read(Item.class.getResourceAsStream("/res/images/equipment/" + Integer.toHexString(id).toLowerCase() + ".png"));
				icon = new ImageIcon(image);
			}else{
				image = otherImage;
				icon = other;
			}
		}
		
		@Override
		public String toString(){
			return name;
		}

		@Override
		public int compareTo(Item o) {
			if(o == null){
				return 0;
			}
			
			if(o == invalid){
				return 1;
			}
			if(this == invalid){
				return -1;
			}
			
			if(o.type < type){
				return 1;
			}
			if(o.type > type){
				return -1;
			}
			
			return name.compareTo(o.name);
		}
	}
}
