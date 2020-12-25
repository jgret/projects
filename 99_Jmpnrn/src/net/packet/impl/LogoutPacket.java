/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet.impl;

import game.Game;
import net.packet.Packet;
import net.server.Server;

public class LogoutPacket extends Packet {
	
	public LogoutPacket() {
		super(ID_LOGOUT, LEN_LOGOUT);
	}
	
	public LogoutPacket(byte[] b) {
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
