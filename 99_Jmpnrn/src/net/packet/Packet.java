/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static net.packet.ByteStreamUtil.*;

public abstract class Packet {
	
	public static final int HEADER_SIZE = 3;
	
	public static final byte ID_LOGIN = 0;
	public static final byte ID_LOGOUT = 1;
	public static final byte ID_WELCOME = 2;
	public static final byte ID_PLAYER_MOVE = 40;
	
	public static final char LEN_LOGIN = 25;
	public static final char LEN_LOGOUT = 0;
	public static final char LEN_WELCOME = 100;
	public static final char LEN_PLAYER_MOVE = 16;
	
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
		data = new ByteArrayOutputStream(b.length);
		writeb(data, b);
		in = new ByteArrayInputStream(b);
		id = getByte(in);
		length = getChar(in);
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
	
	public abstract void handle();

}
