package com.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 10.05.2015.
 */
public class Freqs {

    public static void main(String[] args) {
        DES des = new DES();

        String toEncode = "aaaaaaa bbbbbbb cccccc";
        String pass = "password";

        String encrypted = des.encrypt(pass, DES.utfToBin(toEncode));

        ArrayList<String> strings = new ArrayList<String>();
        HashMap<String, Integer> freq = new HashMap<String, Integer>();

        for(int i = 0; i < encrypted.length(); i+=8){
            String curChar = encrypted.substring(i, i + 8);
            if(freq.containsKey(curChar)){
                freq.put(curChar, freq.get(curChar) + 1);
            } else {
                freq.put(curChar, 1);
            }
        }

        for (Map.Entry<String, Integer> stringIntegerEntry : freq.entrySet()) {
            System.out.println("Char:" + stringIntegerEntry.getKey() + ", freq: " + stringIntegerEntry.getValue());
        }
    }

}
