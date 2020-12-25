/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net;

import net.packet.Packet;
import net.server.Server;

public class GameServer {

	public static void main(String[] args) {
		
		Server server = new Server(4444);
		
		while (true) {
			Packet p = server.getPackets().next();
			
		}
		
		
	}
	
}
