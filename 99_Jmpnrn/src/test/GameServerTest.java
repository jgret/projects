/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of Greimel jgret
 *******************************************************/
package test;

import net.server.GameServer;

public class GameServerTest {
	
	public static void main(String[] args) {
		
		GameServer server = new GameServer(4000, 6000);
		server.start();
		
	}

}
