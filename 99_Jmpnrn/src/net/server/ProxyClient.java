/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.server;

import java.net.Socket;

import net.client.Client;
import net.packet.PacketReaderService;

public class ProxyClient extends Client {
	
	private Server server;
	
	public ProxyClient(Server server, Socket socket) {
		super(socket);
		this.server = server;
		this.tcpReader = new PacketReaderService(socket, server.getPackets());
	}
	
}
