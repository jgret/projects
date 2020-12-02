/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of Greimel jgret
 *******************************************************/
package test;

import java.util.Random;

import net.client.GameClient;
import net.packet.PacketType;

public class GameClientTest {
	
	public static void main(String[] args) {
		
		PacketType.LOGIN.toString();
		
		GameClient client = new GameClient("127.0.0.1", 4000, 6000);
		
		Random random = new Random();
		int n = random.nextInt();
		
		while (true) {
			
			client.send(("Hello World" + n).getBytes());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
