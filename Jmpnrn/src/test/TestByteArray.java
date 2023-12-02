/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.jupiter.api.Test;

import net.packet.ByteArray;

class TestByteArray {

	@Test
	void test_read() {
		
		
		byte[] a = new byte[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = (byte) 0xAA;
		}
		
		ByteArray b = new ByteArray(a);
		
		byte[] c = b.read();
		
		for (int i = 0; i < c.length; i++) {
			if (a[i] != c[i]) {
				assertTrue(false);
				return;
			}
		}

		assertTrue(true);
		
	}
	
	@Test
	void test_read_offset() {
		
		
		byte[] a = new byte[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = (byte) 0xAA;
		}
		
		ByteArray b = new ByteArray(a);
		
		byte[] c = b.read(2);
		
		for (int i = 0; i < c.length; i++) {
			if (a[i + 2] != c[i]) {
				assertTrue(false);
				return;
			}
		}

		assertTrue(true);
		
	}
	
	@Test
	void test_read_offset_length() {
		
		
		byte[] a = new byte[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = (byte) 0xAA;
		}
		
		ByteArray b = new ByteArray(a);
		
		byte[] c = b.read(2, 10);
		
		if (c.length != 10) {
			assertTrue(false);
			return;
		}
		
		for (int i = 0; i < c.length; i++) {
			if (a[i + 2] != c[i]) {
				assertTrue(false);
				return;
			}
		}

		assertTrue(true);
		
	}
	
	@Test
	void test_write() {
		
		byte[] a = {0, 0, 0, 0};
		byte[] w = {1, 1, 1, 1};
		
		ByteArray b = new ByteArray(a);
		b.write(w);
		
		byte[] r = b.read();
		
		for (int i = 0; i < w.length; i++) {
			if (w[i] != r[i]) {
				assertTrue(false);
				return;
			}
		}

		assertTrue(true);
		
	}
	
	@Test
	void test_write_offset() {
		
		byte[] a = {0, 0, 0, 0};
		byte[] w = {1, 1, 1, 1};
		
		ByteArray b = new ByteArray(a);
		b.write(w);
		
		byte[] r = b.read();
		
		for (int i = 0; i < w.length; i++) {
			if (w[i] != r[i]) {
				assertTrue(false);
				return;
			}
		}

		assertTrue(true);
		
	}
	
	@Test
	void test_write_offset_length() {
		
		byte[] a = {0, 0, 0, 0};
		byte[] w = {1, 1, 1, 1};
		
		ByteArray b = new ByteArray(a);
		b.write(w, 0, 2);
		
		byte[] c = {1, 1, 0, 0};
		byte[] r = b.read();
		
		for (int i = 0; i < r.length; i++) {
			if (c[i] != r[i]) {
				assertTrue(false);
				return;
			}
		}

		assertTrue(true);
		
	}
	
	
}
