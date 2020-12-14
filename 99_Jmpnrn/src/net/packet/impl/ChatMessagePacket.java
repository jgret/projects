/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet.impl;

import net.packet.Packet;
import net.server.Server;

import static net.packet.ByteStreamUtil.*;

import game.Game;

public class ChatMessagePacket extends Packet {

	private String sender;
	private String msg;
	
	public ChatMessagePacket(String sender, String msg) {
		super(ID_CHAT_MESSAGE, LEN_CHAT_MESSGE);
		this.sender = sender;
		this.msg = msg;
		writeS(data, sender, 24);
		writeS(data, msg, 512);
	}
	
	public ChatMessagePacket(byte[] b) {
		super(b);
		this.sender = getString(in, 24);
		this.msg = getString(in, 512);
	}

	public String getSender() {
		return sender;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public void handle(Game game) {
		System.out.println("<" + sender + "> " + msg);
	}

	@Override
	public void handle(Server server) {
		System.out.println("<" + sender + "> " + msg);
	}
	
}
