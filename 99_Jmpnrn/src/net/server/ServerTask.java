/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.server;

public abstract class ServerTask implements Runnable {
	
	protected Server server;
	protected volatile boolean running;
	
	public ServerTask(Server server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		running = true;
		
		while (running) {
			task();
		}
		
	}
	
	public abstract void task();

}
