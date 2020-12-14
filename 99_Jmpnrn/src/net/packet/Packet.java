/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet;

import static net.packet.ByteStreamUtil.getByte;
import static net.packet.ByteStreamUtil.getChar;
import static net.packet.ByteStreamUtil.writeb;
import static net.packet.ByteStreamUtil.writec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import game.Game;
import net.server.Server;

public abstract class Packet {
	
	public static final int HEADER_SIZE = 4;
	
	public static final byte ID_LOGIN = 0;
	public static final byte ID_LOGOUT = 1;
	public static final byte ID_WELCOME = 2;
	public static final byte ID_PING = 3;
	public static final byte ID_CHAT_MESSAGE = 20;
	public static final byte ID_PLAYER_MOVE = 40;
	
	public static final char LEN_LOGIN = 0;
	public static final char LEN_LOGOUT = 0;
	public static final char LEN_WELCOME = 100;
	public static final char LEN_PING = 8;
	public static final char LEN_CHAT_MESSGE = 536;
	public static final char LEN_PLAYER_MOVE = 16;
	
	private byte id;
	private byte clientId;
	private char length;
	protected ByteArrayOutputStream data;
	protected ByteArrayInputStream in;
	
	public Packet(int id, int length) {
		this.id = (byte) id;
		this.clientId = -1;
		this.length = (char) length;
		this.data = new ByteArrayOutputStream(length);
		writeb(data, (byte) id);
		writeb(data, (byte) -1);
		writec(data, (char) length);
	}
	
	public Packet(byte[] b) {
		data = new ByteArrayOutputStream(b.length);
		writeb(data, b);
		in = new ByteArrayInputStream(b);
		id = getByte(in);
		clientId = getByte(in);
		length = getChar(in);
		
	}
	
	public void setClientId(int clientId) {
		this.clientId = (byte) clientId;
	}

	public byte[] getData() {
		byte[] b = data.toByteArray();
		b[1] = clientId;
		return b;
	}
	
	public byte getId() {
		return id;
	}
	
	public byte getClientID() {
		return clientId;
	}

	public char getLength() {
		return length;
	}
	
	public abstract void handle(Game game);
	public abstract void handle(Server server);

}
