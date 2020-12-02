/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

import main.Time;
import net.packet.LoginPacket;
import net.packet.LogoutPacket;
import net.packet.Packet;
import net.packet.PacketQueue;
import net.packet.PacketType;
import net.packet.PlayerMovePacket;

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
					
					if (id == PacketType.LOGIN.getId()) {
						LoginPacket l = new LoginPacket(data);
						System.out.println(l.getName());
					}
					
					if (id == PacketType.PLAYER_MOVE.getId()) {
						PlayerMovePacket m = new PlayerMovePacket(data);
						System.out.println("Values: " + m.getX() + ", " + m.getY());
					}
					
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread collectorThread = new Thread(collector);
		collectorThread.start();
		
		queue.add(new LoginPacket("GreT"));
		
		
	}

}
