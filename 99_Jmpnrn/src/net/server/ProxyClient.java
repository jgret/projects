/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;

import net.packet.Packet;

public class ProxyClient {
	
	private Server server;
	private Socket socket;
	private DatagramSocket udp;
	private PacketReader tcpReader;
	private OutputStream out;
	
	public ProxyClient(Server server, Socket socket) {
		this.socket = socket;
		try {
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tcpReader = new PacketReader(socket, server.getPackets());
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
	
}
