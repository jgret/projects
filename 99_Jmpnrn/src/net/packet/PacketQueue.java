/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PacketQueue extends ArrayBlockingQueue<Packet>{
	
	private static final long serialVersionUID = 1L;
	private int capacity;
	
	public PacketQueue(int capacity) {
		super(capacity);
		this.capacity = capacity;
	}
	
	@Override
	public boolean add(Packet p) {
		while (size() >= capacity) {}
		return super.add(p);
	}
	
	public Packet next() {
		Packet p;
		do {
			p = poll();
		} while (p == null);
		
		return p;
	}
	
	public boolean full() {
		return remainingCapacity() < 0;
	}
	
}
