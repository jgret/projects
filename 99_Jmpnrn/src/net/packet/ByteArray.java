/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet;

public class ByteArray {

	public static final int SPACE_FREE = 0;
	public static final int SPACE_FULL = -1;
	public static final int OVERFLOW = 1;
	
	
	private byte[] data;
	private int pos;
	
	public ByteArray(int length) {
		this.data = new byte[length];
	}

	public ByteArray(byte[] b) {
		this.data = b;
	}

	public void write(byte[] b) {
		write(b, 0);
	}

	public void write(byte[] b, int offset) {
		write(b, offset, b.length);
	}

	public void write(byte[] b, int offset, int length) {
		write(b, 0, offset, length);
	}

	public void write(byte[] b, int boffset, int offset, int length) {
		copy(b, boffset, data, offset, length);
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public int getPos() {
		return this.pos;
	}
	
	public int hasSpace(int length) {
		int status;
		int nextPos = pos + length;
		
		if (nextPos < data.length) {
			
			if (nextPos == data.length - 1) {
				status = SPACE_FULL;
			} else {
				status = SPACE_FREE;
			}
			
		} else {
			status = OVERFLOW;
		}
		
		return status;
	}
	
	public boolean append(byte[] b, int length) {
		
		//TODO
		if (pos < data.length) {
			
		}
		
		return false;
		
	}

	public byte[] read() {
		byte[] b = new byte[data.length];
		copy(data, 0, b, 0, data.length);
		return b;
	}

	private void copy(Object src, int srcPos, Object dest, int destPos, int length) {
		System.arraycopy(src, srcPos, dest, destPos, length);
	}

	public byte[] read(int offset) {
		byte[] b = new byte[data.length - offset];
		read(b, offset);
		return b;
	}

	public byte[] read(int offset, int length) {
		byte[] b = new byte[length];
		read(b, offset);
		return b;
	}

	public void read(byte[] b) {
		read(b, 0);
	}

	public void read(byte[] b, int offset) {
		read(b, offset, b.length);
	}
	
	public void read(byte[] b, int offset, int length) {
		copy(data, offset, b, 0, length);
	}

}
