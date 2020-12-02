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
import java.net.Socket;

import net.packet.WelcomePacket;

public class ServerConnection extends ServerTask {

	public ServerConnection(Server server) {
		super(server);
	}

	@Override
	public void task() {
		try {
			Socket socket = server.getServerSocket().accept();
			ProxyClient client = new ProxyClient(server, socket);
			System.out.println("New Client Logged in");
			server.getClients().add(client);
			
			client.send(new WelcomePacket("Hello World"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
