package com.gudenau.sm4sh.editor.save;

import java.io.RandomAccessFile;

import com.gudenau.sm4sh.editor.Equipment;
import com.gudenau.sm4sh.editor.util.FileUtils;

public class EquipmentSave {
	public Item[] equpment;

	public static EquipmentSave load(RandomAccessFile file) throws Exception {
		EquipmentSave save = new EquipmentSave();

		file.seek(0xE830);

		save.equpment = new Item[3000];

		for (int i = 0; i < save.equpment.length; i++) {			
			long	owner =	FileUtils.readLong	(file);
			short	atk =	FileUtils.readShort	(file);
			short	def =	FileUtils.readShort	(file);
			short	speed =	FileUtils.readShort	(file);
			byte	range =	FileUtils.readByte	(file);
			byte	type =	FileUtils.readByte	(file);
			byte	bonus =	FileUtils.readByte	(file);
			byte	id =	FileUtils.readByte	(file);
			short	un1 =	FileUtils.readShort	(file);
			byte	un2 =	FileUtils.readByte	(file);
			byte	level =	FileUtils.readByte	(file);
			short	un3 =	FileUtils.readShort	(file);

			save.equpment[i] = new Item(owner, atk, def, speed, range, type,
					bonus, id, un1, un2, level, un3);
		}

		return save;
	}

	public void save(RandomAccessFile file) throws Exception {
		file.seek(0xE830);
		
		for(Item i : equpment){
			FileUtils.writeLong(i.owner, file);
			FileUtils.writeShort(i.attack, file);
			FileUtils.writeShort(i.defence, file);
			FileUtils.writeShort(i.speed, file);
			FileUtils.writeByte(i.range, file);
			FileUtils.writeByte(i.category, file);
			FileUtils.writeByte(i.bonus, file);
			FileUtils.writeByte(i.id, file);
			FileUtils.writeShort(i.unknown0, file);
			FileUtils.writeByte(i.unknown1, file);
			FileUtils.writeByte(i.level, file);
			FileUtils.writeShort(i.unknown2, file);
		}
	}

	public static class Item {
		public long owner;
		public short attack;
		public short defence;
		public short speed;
		public byte range;
		public byte category;
		public byte bonus;
		public byte id;
		public short unknown0;
		public byte unknown1;
		public byte level;
		public short unknown2;

		public boolean valid = false;
		
		public Item(long owner, short attack, short defence, short speed,
				byte range, byte category, byte bonus, byte id, short unknown0,
				byte unknown1, byte level, short unknown2) {
			this.owner = owner;
			this.attack = attack;
			this.defence = defence;
			this.speed = speed;
			this.range = range;
			this.category = category;
			this.bonus = bonus;
			this.id = id;
			this.unknown0 = unknown0;
			this.unknown1 = unknown1;
			this.level = level;
			this.unknown2 = unknown2;
			
			validate();
		}

		private void validate() {
			valid = (category != -1);
		}

		public String toString() {
			try {
				if(!valid){
					return "Empty";
				}
				
				switch (level) {
				case 0:
					return Equipment.equipment[id].level3;
				case 1:
					return Equipment.equipment[id].level2;
				default:
					
					if(bonus == -1){
						return Equipment.equipment[id].level1;
					}else{
						return Equipment.bonus[bonus].name + " " + Equipment.equipment[id].level1;
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
				return "Empty";
			}
		}
	}
}
