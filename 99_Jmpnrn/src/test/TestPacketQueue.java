/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test;

import main.Game;
import net.packet.Packet;
import net.packet.PacketQueue;
import net.packet.impl.PingPacket;
import net.packet.impl.PlayerMovePacket;

public class TestPacketQueue {
	
	public static void main(String[] args) {
		
		PacketQueue queue = new PacketQueue(1000);
		
		Runnable collector = new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					Packet p = queue.next();
					System.out.println("Packet PID: " + p.getId());
					System.out.println("Packet LEN: " + (int) p.getLength());
					System.out.println("Packet RAW: " + p.getData().length);
					
					//simulate the data coming in
					byte[] data = p.getData();
					byte id = data[0];
					
					if (id == Packet.ID_PLAYER_MOVE) {
						PlayerMovePacket m = new PlayerMovePacket(data);
						System.out.println("Values: " + m.getX() + ", " + m.getY());
					}
					
					if (id == Packet.ID_PING) {
						PingPacket ping = new PingPacket(data);
						ping.handle((Game)null);
					}
				}
			}
		};
		
		Thread collectorThread = new Thread(collector);
		collectorThread.start();
		queue.add(new PingPacket(System.nanoTime()));
		
	}

}
