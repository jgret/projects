/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of Greimel jgret
 *******************************************************/
package net.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static net.packet.ByteStreamUtil.*;

public abstract class Packet {
	
	private byte id;
	private char length;
	protected ByteArrayOutputStream data;
	protected ByteArrayInputStream in;
	
	public Packet(int id, int length) {
		this.id = (byte) id;
		this.length = (char) length;
		this.data = new ByteArrayOutputStream(length);
		writeb(data, (byte) id);
		writec(data, (char) length);
	}
	
	public Packet(byte[] b) {
		in = new ByteArrayInputStream(b);
		id = getByte(in);
		length = getChar(in);
		
		System.out.println(b.length);
	}
	
	public byte[] getData() {
		return data.toByteArray();
	}
	
	public byte getId() {
		return id;
	}

	public char getLength() {
		return length;
	}
	
}
