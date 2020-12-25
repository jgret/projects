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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import net.packet.impl.ChatMessagePacket;
import net.packet.impl.LoginPacket;
import net.packet.impl.LogoutPacket;
import net.packet.impl.PingPacket;
import net.packet.impl.PlayerMovePacket;
import net.packet.impl.WelcomePacket;

import static net.packet.ByteStreamUtil.*;

public class PacketReader {

	private Socket socket;
	private InputStream in;
	private volatile boolean running;

	public PacketReader(Socket socket) {
		this.socket = socket;
		try {
			in = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Packet nextPacket() {

		byte[] header = read(Packet.HEADER_SIZE);
		ByteArrayInputStream headerIn = new ByteArrayInputStream(header);

		byte id = getByte(headerIn);
		byte clientId = getByte(headerIn);
		char length = getChar(headerIn);

		byte[] data = read(length);
		byte[] packet = merge(header, data);

		switch (id) {

		case (Packet.ID_LOGIN): {
			LoginPacket p = new LoginPacket(packet);
			return p;
		}

		case (Packet.ID_LOGOUT): {
			LogoutPacket p = new LogoutPacket(packet);
			break;
		}

		case (Packet.ID_WELCOME): {
			WelcomePacket p = new WelcomePacket(packet);
			return p;
		}

		case (Packet.ID_PING): {
			PingPacket p = new PingPacket(packet);
			return p;
		}

		case (Packet.ID_PLAYER_MOVE): {
			PlayerMovePacket p = new PlayerMovePacket(packet);
			return p;
		}
		
		case (Packet.ID_CHAT_MESSAGE): {
			ChatMessagePacket p = new ChatMessagePacket(packet);
			return p;
		}
		
		}
		
		return nextPacket();
		
	}
	
	private byte[] merge(byte[] a, byte[] b) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(a.length + b.length);
		writeb(out, a);
		writeb(out, b);
		return out.toByteArray();
	}

	private byte[] read(int n) {

		if (n < 1) {
			return new byte[0];
		}

		byte[] data = null;
		try {
			while (in.available() < n) {
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			data = in.readNBytes(n);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;

	}

	public Socket getSocket() {
		return socket;
	}

}