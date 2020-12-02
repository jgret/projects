/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import net.packet.PacketQueue;

public class GameServer implements Runnable {
	
	private ServerSocket tcpServerSocket;
	private DatagramSocket udpServerSocket;
	private PacketQueue packets;
	private volatile boolean running;
	
	public GameServer(int tcpport, int udpport) {

		packets = new PacketQueue(100);
		
		try {
			this.tcpServerSocket = new ServerSocket(tcpport);
			this.udpServerSocket = new DatagramSocket(udpport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Startet TCP-Server on " + getIP().toString() + ":" + tcpport);
		System.out.println("Startet UDP-Server on " + getIP().toString() + ":" + udpport);
		
	}
	
	public void start() {
		Thread server = new Thread(this, "Server");
		server.start();
	}
	
	@Override
	public void run() {
		running = true;
		while (running) {

			DatagramPacket packet = new DatagramPacket(new byte[12], 12);
			
			try {
				udpServerSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println(new String(packet.getData()));
			
		}
	}
	
	public InetAddress getIP() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

}
