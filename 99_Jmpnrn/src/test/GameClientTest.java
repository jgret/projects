/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test;

import java.util.Scanner;

import game.Game;
import net.client.Client;
import net.packet.Packet;
import net.packet.impl.ChatMessagePacket;
import net.packet.impl.LoginPacket;
import net.packet.impl.PingPacket;
import net.packet.impl.WelcomePacket;

public class GameClientTest {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("Enter your Name");
		String sender = in.nextLine();
		
		Client client = new Client("localhost", 4444);
		client.send(new LoginPacket());
		Packet h = client.request();
		if (h instanceof LoginPacket) {
			LoginPacket login = (LoginPacket) h;
			client.setClientId(h.getClientID());
		}
		
		System.out.println("Client ID: " + client.getClientId());
		client.start();
		
		client.send(new PingPacket(System.nanoTime()));

		Thread t = new Thread(() -> {
			while (true) {
				client.send(new ChatMessagePacket(sender, in.nextLine()));
			}
		});
		t.start();

		double delta = 0;
		double last = System.currentTimeMillis() / 1000;
		
		while (true) {
			if (client.getPackets().available() > 0) {
				Packet p = client.getPackets().next();
				p.handle((Game)null);
			}
			
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
