/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.server;

import java.util.ArrayList;

import net.packet.Packet;

public class ProxyClients {

	private ArrayList<ProxyClient> clients;
	
	public ProxyClients() {
		this.clients = new ArrayList<>();
	}
	
	public void add(ProxyClient p) {
		this.clients.add(p);
	}
	
	public void remove(ProxyClient p) {
		this.clients.remove(p);
	}
	
	public void send(Packet p) {
		for (ProxyClient client : clients) {
			client.send(p);
		}
	}
	
	public void sendWithout(ProxyClient c, Packet p) {
		for (ProxyClient client : clients) {
			if (!client.equals(c)) {
				client.send(p);
			}
		}
	}
	
}
