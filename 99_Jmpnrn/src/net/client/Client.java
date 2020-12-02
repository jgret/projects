/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import net.packet.Packet;
import net.packet.PacketQueue;
import net.server.PacketReader;

public class Client {
	
	private Socket socket;
	private PacketQueue packets;
	private PacketReader tcpReader;
	private OutputStream out;
	
	public Client(String host, int port) {
		try {
			this.socket = new Socket(host, port);
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			System.err.println("Couldn't connect to Server " + host + ":" + port);
			System.exit(1);
		}
		
		this.tcpReader = new PacketReader(socket);
		this.packets = tcpReader.getPackets();
		Thread tcpReaderThread = new Thread(tcpReader);
		tcpReaderThread.start();
	}
	
	public void send(Packet p) {
		try {
			
			int length = p.getLength() + Packet.HEADER_SIZE;
			int rawLength = p.getData().length;
			
			if (length != rawLength) {
				System.err.println("Length does not match raw length");
				System.err.println("PID: " + p.getId() + " Length: " + rawLength + "/" + length);
			}
			
			out.write(p.getData(), 0, length);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public PacketQueue getPackets() {
		return packets;
	}

	public PacketReader getTcpReader() {
		return tcpReader;
	}

}
