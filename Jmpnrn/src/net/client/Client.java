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

import net.packet.ByteStreamUtil;
import net.packet.Packet;
import net.packet.PacketQueue;
import net.packet.PacketReader;
import net.packet.PacketReaderService;

public class Client {
	
	protected Socket socket;
	protected PacketQueue packets;
	protected PacketReaderService tcpReader;
	protected OutputStream out;
	protected byte clientId;
	
	protected Thread tcpReaderThread;
	
	public Client(String host, int port) {
		try {
			this.socket = new Socket(host, port);
			this.socket.setTcpNoDelay(true);
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			System.err.println("Couldn't connect to Server " + host + ":" + port);
			System.exit(1);
		}
		
		this.tcpReader = new PacketReaderService(socket);
		this.packets = tcpReader.getPackets();
		this.clientId = -1;
	}
	
	public Client(Socket socket) {
		this.socket = socket;
		try {
			this.socket.setTcpNoDelay(true);
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tcpReader = new PacketReaderService(socket);
		this.packets = tcpReader.getPackets();
	}
	
	public void start() {
		tcpReaderThread = new Thread(tcpReader);
		tcpReaderThread.start();
	}
	
	public void stop() {
		tcpReaderThread.interrupt();
	}
	
	public void send(Packet p) {
		p.setClientId(clientId);
		
		try {
			
			byte[] packet = p.getData();
			
			int length = p.getLength() + Packet.HEADER_SIZE;
			int rawLength = packet.length;

			if (length != rawLength) {
				System.err.println("Length does not match raw length");
				System.err.println("PID: " + p.getId() + " Length: (raw/should) " + rawLength + "/" + length);
			}
			
			out.write(packet, 0, length);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Packet request() {
		PacketReader reader = new PacketReader(socket);
		return reader.nextPacket();
		
	}

	public Socket getSocket() {
		return socket;
	}

	public PacketQueue getPackets() {
		return packets;
	}

	public void setClientId(int id) {
		this.clientId = (byte) id;
	}

	public byte getClientId() {
		return clientId;
	}
	
}
