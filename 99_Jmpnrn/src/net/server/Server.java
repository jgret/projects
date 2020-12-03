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
import java.net.ServerSocket;

import net.packet.PacketQueue;

public class Server {
	
	private PacketQueue packets;
	private ProxyClients clients;
	private ServerConnection connections;
	private ServerSocket serverSocket;
	
	public Server(int port) {
		this.packets = new PacketQueue(100);
		this.clients = new ProxyClients();
		this.connections = new ServerConnection(this);
		this.serverSocket = createTCPSocket(port);
		Thread connectionThread = new Thread(connections, "Server Connection Handler");
		connectionThread.start();
		
		System.out.println("Startet Server on port " + port);
	}
	
	public ServerSocket createTCPSocket(int port) {
		try {
			System.out.println("Starting Server on port " + port);
			return new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Failed to create TCP Server on port " + port);
			System.exit(1);
		}
		return null;
	}
	
	public PacketQueue getPackets() {
		return packets;
	}

	public ProxyClients getClients() {
		return clients;
	}

	public ServerConnection getConnections() {
		return connections;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
}
