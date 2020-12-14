/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import game.Game;
import net.packet.Packet;
import net.server.Server;

import static net.packet.ByteStreamUtil.*;

public class LoginPacket extends Packet {
	
	public LoginPacket() {
		super(ID_LOGIN, LEN_LOGIN);
	}
	
	public LoginPacket(byte[] b) {
		super(b);
	}

	@Override
	public void handle(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle(Server server) {
		// TODO Auto-generated method stub
		
	}
	
}
