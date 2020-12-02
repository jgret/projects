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
import java.io.IOException;
import java.nio.ByteBuffer;

import static net.packet.ByteStreamUtil.*;

public class PlayerMovePacket extends Packet {

	public static final byte ID = PacketType.PLAYER_MOVE.getId();
	public static final char LENGTH = PacketType.PLAYER_MOVE.getLength();
	
	private double x;
	private double y;

	public PlayerMovePacket(double x, double y) {
		super(ID, LENGTH);
		
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
	

}
