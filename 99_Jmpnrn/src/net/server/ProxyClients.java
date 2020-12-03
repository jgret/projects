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
import java.util.HashMap;
import java.util.Iterator;

import net.client.Client;
import net.packet.Packet;

public class ProxyClients {

	public static final int ID_REQUEST = -1;
	public static final int ID_NOAVAILABLE = -2;
	public static final int[] AVAILABLE_IDS = {99, 1, 2, 3, 4, 5, 6, 7};
	
	private HashMap<Integer, ProxyClient> clients;
	
	public ProxyClients() {
		this.clients = new HashMap<>();
	}

	public void connect(int id, ProxyClient p) {
		this.clients.put(id, p);
	}

	public void disconnect(int id) {
		this.clients.remove(id);
	}

	public void send(Packet p) {

		Iterator<Integer> iterator = clients.keySet().iterator();
		while (iterator.hasNext()) {
			clients.get(iterator.next()).send(p);
		}
	}

	public void sendWithout(int bannedid, Packet p) {

		Iterator<Integer> iterator = clients.keySet().iterator();
		while (iterator.hasNext()) {
			int clientid = iterator.next();
			Client client = clients.get(clientid);
			if (!(client.getClientId() == bannedid)) {
				
				System.out.println("Sending to " + client.getClientId());
				client.send(p);
			}

		}

	}
	
	public int getNewClientID() {
		for (int i : AVAILABLE_IDS) {
			if (clients.get(i) == null) {
				return i;
			}
		}
		
		return ID_NOAVAILABLE;
	}

}
