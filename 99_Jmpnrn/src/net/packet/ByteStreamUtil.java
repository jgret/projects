/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package net.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteStreamUtil {

	public static byte[] getBytes(char c) {
		byte[] b = new byte[2];
		ByteBuffer.wrap(b).putChar(c);
		return b;
	}
	
	public static byte[] getBytes(int i) {
		byte[] b = new byte[4];
		ByteBuffer.wrap(b).putInt(i);
		return b;
	}

	public static byte[] getBytes(double d) {
		byte[] b = new byte[8];
		ByteBuffer.wrap(b).putDouble(d);
		return b;
	}

	public static byte[] getBytes(float f) {
		byte[] b = new byte[4];
		ByteBuffer.wrap(b).putFloat(f);
		return b;
	}

	public static byte[] getBytes(short s) {
		byte[] b = new byte[2];
		ByteBuffer.wrap(b).putShort(s);
		return b;
	}

	public static byte[] getBytes(long l) {
		byte[] b = new byte[8];
		ByteBuffer.wrap(b).putLong(l);
		return b;
	}

	public static byte[] getBytes(String s) {
		return s.getBytes();
	}

	public static byte getByte(ByteArrayInputStream in) {
		return (byte) in.read();
	}

	public static double getDouble(ByteArrayInputStream in) {
		try {
			return getDouble(in.readNBytes(8));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static double getDouble(byte[] bytes, int off) {
		return ByteBuffer.wrap(bytes, off, 8).getDouble();
	}

	public static double getDouble(byte[] bytes) {
		return getDouble(bytes, 0);
	}

	public static double getFloat(ByteArrayInputStream in) {
		try {
			return getFloat(in.readNBytes(4));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static double getFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getFloat();
	}

	public static long getInt(ByteArrayInputStream in) {
		try {
			return getInt(in.readNBytes(4));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int getInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static char getChar(ByteArrayInputStream in) {
		try {
			return getChar(in.readNBytes(2));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static char getChar(byte[] bytes, int off) {
		return ByteBuffer.wrap(bytes, off, 2).getChar();
	}
	
	public static char getChar(byte[] bytes) {
		return getChar(bytes, 0);
	}

	public static long getShort(ByteArrayInputStream in) {
		try {
			return getShort(in.readNBytes(2));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static short getShort(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getShort();
	}

	public static long getLong(ByteArrayInputStream in) {
		try {
			return getLong(in.readNBytes(8));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static long getLong(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}

	public static String getString(ByteArrayInputStream in, int len) {
		try {
			return getString(in.readNBytes(len));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getString(byte[] bytes) {
		return new String(bytes).trim();
	}

	public static boolean writeb(ByteArrayOutputStream out, int b) {
		out.write(b);
		return true;
	}

	public static boolean writeb(ByteArrayOutputStream out, byte[] b) {
		try {
			out.write(b);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writeb(ByteArrayOutputStream out, byte[] b, int len) {
		out.write(b, 0, len);
		return true;
	}

	public static boolean writec(ByteArrayOutputStream out, char c) {
		try {
			out.write(getBytes(c));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writes(ByteArrayOutputStream out, short s) {
		try {
			out.write(getBytes(s));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writei(ByteArrayOutputStream out, int i) {
		try {
			out.write(getBytes(i));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writef(ByteArrayOutputStream out, float f) {
		try {
			out.write(getBytes(f));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writed(ByteArrayOutputStream out, double d) {
		try {
			out.write(getBytes(d));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writel(ByteArrayOutputStream out, long l) {
		try {
			out.write(getBytes(l));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writeS(ByteArrayOutputStream out, String s, int len) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(50);
		if (s.length() > len) {
			writeb(buffer, s.getBytes(), len);
		} else {
			writeb(buffer, s.getBytes());
			writeb(buffer, new byte[len - s.length()]);
		}

		try {
			out.write(buffer.toByteArray());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) {

		ByteArrayOutputStream out = new ByteArrayOutputStream(100);
		String msg1 = "Hello World";
		String msg2 = "Goodby World";
		String msg3 = "This is a long Message";

		writeb(out, (byte) 0);
		writec(out, (char) 108);

		writeS(out, msg1, 25);
		writeS(out, msg2, 25);
		writeS(out, msg3, 50);
		writed(out, 3.141592653);

		byte[] send = out.toByteArray();
		System.out.println("Buffersize: " + send.length);

		ByteArrayInputStream in = new ByteArrayInputStream(send);

		System.out.println(getByte(in));
		System.out.println(getChar(in));

		msg1 = getString(in, 25);
		msg2 = getString(in, 25);
		msg3 = getString(in, 50);

		System.out.println("Message 1: " + msg1.trim());
		System.out.println("Message 2: " + msg2.trim());
		System.out.println("Message 3: " + msg3.trim());
		double pi = getDouble(in);
		System.out.println("Message 4: " + pi);

	}

}
