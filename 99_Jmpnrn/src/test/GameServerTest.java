/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test;

import net.packet.Packet;
import net.packet.impl.ChatMessagePacket;
import net.packet.impl.PingPacket;
import net.packet.impl.WelcomePacket;
import net.server.Server;

public class GameServerTest {
	
	public static void main(String[] args) {
		
		Server server = new Server(5555);
		
		while (true) {
			Packet p = server.getPackets().next();
			p.handle(server);
			
			if (p instanceof PingPacket) {
				server.getClients().send(p);
			}
			
			if (p instanceof WelcomePacket) {
				WelcomePacket msg = (WelcomePacket) p;
				server.getClients().sendWithout(p.getClientID(), p);
			}
			
			if (p instanceof ChatMessagePacket) {
				ChatMessagePacket msg = (ChatMessagePacket) p;
				server.getClients().sendWithout(p.getClientID(), p);
			}

		}
		
	}

}
