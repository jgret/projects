/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet;

import java.net.Socket;

public class PacketReaderService implements Runnable {

	private volatile boolean running;
	private PacketReader reader;
	private PacketQueue packets;

	public PacketReaderService(Socket socket, PacketQueue packets) {
		this.reader = new PacketReader(socket);
		this.packets = packets;
	}

	public PacketReaderService(Socket socket) {
		this(socket, new PacketQueue(100));
	}

	@Override
	public void run() {
		running = true;
		
		while (running) {
			Packet p = reader.nextPacket();
			packets.add(p);
		}

	}
	
	public PacketQueue getPackets() {
		return packets;
	}

}
