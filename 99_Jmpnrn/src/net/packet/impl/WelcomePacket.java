/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet.impl;

import static net.packet.ByteStreamUtil.*;

import net.packet.Packet;

public class WelcomePacket extends Packet {

	private String msg;
	
	public WelcomePacket(String msg) {
		super(ID_WELCOME, LEN_WELCOME);
		writeS(data, msg, 100);
	}

	public WelcomePacket(byte[] b) {
		super(b);
		msg = getString(in, 100);
	}
	
	@Override
	public void handle() {
		System.out.println(msg);
	}
	
}
