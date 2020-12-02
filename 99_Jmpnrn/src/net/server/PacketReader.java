/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import net.packet.LoginPacket;
import net.packet.LogoutPacket;
import net.packet.Packet;
import net.packet.PacketQueue;
import net.packet.PlayerMovePacket;
import net.packet.WelcomePacket;

import static net.packet.ByteStreamUtil.*;

public class PacketReader implements Runnable {

	private PacketQueue packets;
	private Socket socket;
	private InputStream in;
	private volatile boolean running;
	
	public PacketReader(Socket socket) {
		this.socket = socket;
		this.packets = new PacketQueue(100);
	}
	
	public PacketReader(Socket socket, PacketQueue queue) {
		this.socket = socket;
		this.packets = queue;
	}

	@Override
	public void run() {
		
		running = true;
		
		try {
			in = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (running) {
	
			byte[] header = read(3);
			ByteArrayInputStream headerIn = new ByteArrayInputStream(header);
			byte id = getByte(headerIn);
			char length = getChar(headerIn);
			
			
			byte[] data = read(length);
			byte[] packet = merge(header, data);
			
			switch (id) {
			
			case (Packet.ID_LOGIN): {
				LoginPacket p = new LoginPacket(packet);
				packets.add(p);
				break;
			}
			
			case (Packet.ID_LOGOUT): {
				LogoutPacket p = new LogoutPacket(packet);
				packets.add(p);
				break;
			}
			
			case (Packet.ID_WELCOME): {
				WelcomePacket p = new WelcomePacket(packet);
				packets.add(p);
				break;
			}

			case (Packet.ID_PLAYER_MOVE): {
				PlayerMovePacket p = new PlayerMovePacket(packet);
				packets.add(p);
				break;
			}
			
			}
			
		}
		
	}
	
	private byte[] merge(byte[] a, byte[] b) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(a.length + b.length);
		writeb(out, a);
		writeb(out, b);
		return out.toByteArray();
	}
	
	private byte[] read(int n) {
		
		if (n < 1) {
			//TODO
			return null;
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

	public PacketQueue getPackets() {
		return packets;
	}

	public Socket getSocket() {
		return socket;
	}

}