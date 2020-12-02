/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of Greimel jgret
 *******************************************************/
package net.packet;

public enum PacketType {
	
	LOGIN(1, 512),
	LOGOUT(2, 0),
	PLAYER_MOVE(40, 16)
	;
	
	private byte id;
	private char length;
	
	private PacketType(int id, int length) {
		if (id < Byte.MIN_VALUE || id > Byte.MAX_VALUE) {
			throw new IllegalStateException("Id is out of Bounds: min: -128 max 127");
		}
		if (length < 0 || length > Character.MAX_VALUE) {
			throw new IllegalStateException("Id is out of Bounds: min: 0 max: 65536");
		}
		
		this.id = (byte) id;
		this.length = (char) length;
	}

	public byte getId() {
		return id;
	}

	public char getLength() {
		return length;
	}
	
}
