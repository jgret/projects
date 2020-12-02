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

import net.client.Client;
import net.packet.Packet;
import net.packet.impl.LoginPacket;
import net.packet.impl.WelcomePacket;

public class GameClientTest {

	public static void main(String[] args) {

		Client client = new Client("localhost", 4444);

		client.send(new LoginPacket("Thomas Greimel"));
		
		Thread t = new Thread(() -> {
			Scanner in = new Scanner(System.in);
			while (true) {
				client.send(new WelcomePacket(in.nextLine()));
			}
		});
		t.start();
		
		while (true) {
			Packet p = client.getPackets().next();
			p.handle();
			
		}
		
		


	}

}
