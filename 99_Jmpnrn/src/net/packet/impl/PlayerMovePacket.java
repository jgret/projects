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
import java.nio.ByteBuffer;

import main.Game;
import net.packet.Packet;
import net.server.Server;

import static net.packet.ByteStreamUtil.*;

public class PlayerMovePacket extends Packet {

	private double x;
	private double y;

	public PlayerMovePacket(double x, double y) {
		super(ID_PLAYER_MOVE, LEN_PLAYER_MOVE);
		
		this.x = x;
		this.y = y;

		try {
			data.write(getBytes(x));
			data.write(getBytes(y));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public PlayerMovePacket(byte[] b) {
		super(b);
		this.x = getDouble(in);
		this.y = getDouble(in);
	}

	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
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
