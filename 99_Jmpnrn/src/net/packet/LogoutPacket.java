/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet;

public class LogoutPacket extends Packet {
	
	public static final byte ID = PacketType.LOGOUT.getId();
	public static final char LENGTH = PacketType.LOGOUT.getLength();

	public LogoutPacket() {
		super(ID, LENGTH);
	}
	
}
