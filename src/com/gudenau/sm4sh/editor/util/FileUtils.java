package com.gudenau.sm4sh.editor.util;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This *should* not be needed, but Java is silly
 * */
public class FileUtils {
	public static boolean readBoolen(RandomAccessFile file) throws IOException {
		int ch = file.read();
		if(ch < 0){
			throw new EOFException();
		}
		
		return ch != 0;
	}
	
	public static byte readByte(RandomAccessFile file) throws IOException {
		int ch = file.read();
		if(ch < 0){
			throw new EOFException();
		}
		return (byte)ch;
	}
	
	public static int readUnsignedByte(RandomAccessFile file) throws IOException {
		int ch = file.read();
		if(ch < 0){
			throw new EOFException();
		}
		return ch;
	}
	
	public static short readShort(RandomAccessFile file) throws IOException {
        int ch1 = file.read();
        int ch2 = file.read();
        if ((ch1 | ch2) < 0){
            throw new EOFException();
        }
        return (short)((ch1 << 0) + (ch2 << 8));
    }
	
	public static int readUnsignedShort(RandomAccessFile file) throws IOException {
        int ch1 = file.read();
        int ch2 = file.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch1 << 0) + (ch2 << 8);
    }
	
	public final char readChar(RandomAccessFile file) throws IOException {
        int ch1 = file.read();
        int ch2 = file.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (char)((ch1 << 0) + (ch2 << 8));
    }
	
	public static int readInt(RandomAccessFile file) throws IOException{
		int ch1 = file.read();
		int ch2 = file.read();
		int ch3 = file.read();
		int ch4 = file.read();
		
		if((ch1 | ch2 | ch3 | ch4) < 0){
			throw new EOFException();
		}
		
		return ch1 + (ch2 << 8) + (ch3 << 16) + (ch4 << 24);
	}
	
	public static long readLong(RandomAccessFile file) throws IOException {
		return (readInt(file) & 0xFFFFFFFFL) + ((long)readInt(file) << 32);
	}
	
	public final float readFloat(RandomAccessFile file) throws IOException {
        return Float.intBitsToFloat(readInt(file));
    }
	
	public static double readDouble(RandomAccessFile file) throws IOException {
        return Double.longBitsToDouble(readInt(file));
    }
	
	public final String readLine(RandomAccessFile file) throws IOException {
        StringBuffer input = new StringBuffer();
        int c = -1;
        boolean eol = false;

        while (!eol) {
            switch (c = file.read()) {
            case -1:
            case '\n':
                eol = true;
                break;
            case '\r':
                eol = true;
                long cur = file.getFilePointer();
                if ((file.read()) != '\n') {
                	file.seek(cur);
                }
                break;
            default:
                input.append((char)c);
                break;
            }
        }

        if ((c == -1) && (input.length() == 0)) {
            return null;
        }
        return input.toString();
    }
	
	public final String readUTF(RandomAccessFile file) throws IOException {
        return DataInputStream.readUTF(file);
    }
	
	public static void writeBoolean(boolean v, RandomAccessFile file) throws IOException {
		file.write(v ? 1 : 0);
    }

    public static void writeByte(int v, RandomAccessFile file) throws IOException {
    	file.write(v);
    }

    public static void writeShort(int v, RandomAccessFile file) throws IOException {
    	file.write((v >>> 0) & 0xFF);
        file.write((v >>> 8) & 0xFF);
    }

    public static void writeChar(int v, RandomAccessFile file) throws IOException {
    	file.write((v >>> 0) & 0xFF);
        file.write((v >>> 8) & 0xFF);
    }

    public static void writeInt(int v, RandomAccessFile file) throws IOException {
    	file.write((v >>>  0) & 0xFF);
        file.write((v >>>  8) & 0xFF);
        file.write((v >>> 16) & 0xFF);
        file.write((v >>> 24) & 0xFF);
    }

    public static void writeLong(long v, RandomAccessFile file) throws IOException {
    	file.write((int)(v >>>  0) & 0xFF);
        file.write((int)(v >>>  8) & 0xFF);
        file.write((int)(v >>> 16) & 0xFF);
        file.write((int)(v >>> 24) & 0xFF);
        file.write((int)(v >>> 32) & 0xFF);
        file.write((int)(v >>> 40) & 0xFF);
        file.write((int)(v >>> 48) & 0xFF);
        file.write((int)(v >>> 56) & 0xFF);
    }

    public static void writeFloat(float v, RandomAccessFile file) throws IOException {
    	file.writeInt(Float.floatToIntBits(v));
    }

    public static void writeDouble(double v, RandomAccessFile file) throws IOException {
    	file.writeLong(Double.doubleToLongBits(v));
    }
}
