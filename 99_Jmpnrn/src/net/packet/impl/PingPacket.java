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

public class PingPacket extends Packet {

	public long time;
	
	public PingPacket(long time) {
		super(ID_PING, LEN_PING);
		this.time = time;
		writel(data, time);
	}
	
	public PingPacket(byte[] b) {
		super(b);
		this.time = getLong(in);
	}
	
	@Override
	public void handle(Game game) {
		System.out.println(System.nanoTime() - time);
	}

	@Override
	public void handle(Server server) {
		// TODO Auto-generated method stub
		
	}

}
