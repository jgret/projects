/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
	
	private Socket tcp;
	private InetAddress serverHost;
	private DatagramSocket udp;
	private int tcpport;
	private int udpport;
	
	public GameClient(String ip, int tcpport, int udpport) {
		
		try {
			this.tcp = new Socket(InetAddress.getByName(ip), tcpport);
			this.serverHost = tcp.getInetAddress();
			System.out.println("UDP port?");
			Scanner in = new Scanner(System.in);
			this.udp = new DatagramSocket(in.nextInt());
			this.tcpport = tcpport;
			this.udpport = udpport;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void send(byte[] msg) {

		try {
			System.out.println("Send");
			DatagramPacket p = new DatagramPacket(msg, msg.length, serverHost, udpport);
			udp.send(p);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
