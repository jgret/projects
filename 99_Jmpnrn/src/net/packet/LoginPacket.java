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
import java.io.IOException;

import static net.packet.ByteStreamUtil.*;

public class LoginPacket extends Packet {
	
	private String name;
	
	public LoginPacket(String user) {
		super(ID_LOGIN, LEN_LOGIN);
		writeS(data, user, 25);
	}
	
	public LoginPacket(byte[] b) {
		super(b);
		this.name = getString(in, 25);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void handle() {
		System.out.println(name);
	}
	
}
