package com.gudenau.sm4sh.editor.save;

import java.io.File;
import java.io.RandomAccessFile;

import com.gudenau.sm4sh.editor.util.FileUtils;

public class Save {
	public Character characters;
	public long money;
	public EquipmentSave equipment;
	
	public static Save loadSave(File account, File system) throws Exception {
		Save save = new Save();
		
		RandomAccessFile file = new RandomAccessFile(system, "r");
		
		file.seek(0x20);
		save.characters = Character.load(FileUtils.readLong(file));
		
		file.seek(0xD98);
		save.money = ((long)FileUtils.readInt(file)) & 0x00000000FFFFFFFF;
		
		save.equipment = EquipmentSave.load(file);
		
		file.close();
		
		return save;
	}

	public void save(File account, File system) throws Exception {
		RandomAccessFile file = new RandomAccessFile(system, "rw");
		
		file.seek(0x20);
		FileUtils.writeLong(characters.save(), file);
		
		file.seek(0xD98);
		FileUtils.writeInt((int) money, file);
		
		equipment.save(file);
		
		file.close();
	}
}
