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

import net.packet.Packet;
import net.packet.impl.LoginPacket;
import net.packet.impl.WelcomePacket;

public class ServerConnection extends ServerTask {

	public ServerConnection(Server server) {
		super(server);
	}

	@Override
	public void task() {
		try {
			Socket socket = server.getServerSocket().accept();
			
			
			System.out.println("New Client Attempts to Connect: " + socket.getInetAddress().toString());
			ProxyClient client = new ProxyClient(server, socket);
			Packet p = client.request();
			if (p.getId() == Packet.ID_LOGIN) {
				client.start();
				int id = server.getClients().getNewClientID();
				client.setClientId(id);
				client.send(p);
				server.getClients().connect(id, client);
				System.out.println("Client Successful connected");
			} else {
				System.out.println("Client Failed to Connect");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
