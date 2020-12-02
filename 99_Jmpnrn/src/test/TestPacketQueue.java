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
import net.packet.PacketQueue;
import net.packet.impl.LoginPacket;
import net.packet.impl.PlayerMovePacket;

public class TestPacketQueue {
	
	public static void main(String[] args) {
		
		PacketQueue queue = new PacketQueue(1000);
		
		Runnable collector = new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					Packet p = queue.next();
					
					//simulate the data coming in
					byte[] data = p.getData();
					byte id = data[0];
					
					if (id == Packet.ID_LOGIN) {
						LoginPacket l = new LoginPacket(data);
						System.out.println(l.getName());
					}
					
					if (id == Packet.ID_PLAYER_MOVE) {
						PlayerMovePacket m = new PlayerMovePacket(data);
						System.out.println("Values: " + m.getX() + ", " + m.getY());
					}
				}
			}
		};
		
		Thread collectorThread = new Thread(collector);
		collectorThread.start();
		
		queue.add(new LoginPacket("GreT"));
		
		
	}

}
