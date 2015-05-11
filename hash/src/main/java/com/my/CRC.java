package com.my;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CRC {
	
	byte[] data;
	int crc8;
	
	public CRC(String filePath) {
		Path path = Paths.get(filePath);
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			System.err.println("Error: File not found.");
		}
		crc8 = hash(data);
	}
	
	public static int hash(byte[] data) {
		int crc = 0xFF;
		int len = data.length;
		int i = 0;
		while ((len--) > 0) {
			crc ^= data[i++];
			for (int j = 0; j < 8; j++) {
				crc = (crc & 0x80) != 0 ? (crc << 1) ^ 0x31 : crc << 1;
			}
		}
		return crc & 0b00000000_00000000_00000000_11111111;
	}
	
	public int getCrc8() {
		return crc8;
	}

    public static void main(String[] args) {
        int a = hash(new byte[]{1, 2, 3, 4, 5, 6, 7, 0});
        int b = hash(new byte[]{1, 2, 3, 4, 5, 6, 7, -128});
        int samples = a ^ b;
        System.out.println(Integer.toString(samples, 2));
    }

}