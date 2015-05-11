package com.my;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static String fileName = "Doc.doc";

    public static void printInfo(Document doc) throws IOException {
        System.out.println("Original:"
                + Integer.toBinaryString(CRC.hash(doc.getBytesOriginalFile()))
                + "; Fake:"
                + Integer.toBinaryString(CRC.hash(doc.getBytesFakeFile())));
    }

    public static void print(List<Integer> list) {
        for (Integer hash : list) {
            System.out.println(Integer.toBinaryString(hash));
        }
    }

    public static void main(String[] args) throws IOException {
        Document doc = new Document("Orig.doc", "Fake.doc");

        Path originPath = Paths.get("Orig.doc");
        Path fakePath = Paths.get("Fake.doc");

        Files.delete(fakePath);
        Files.delete(originPath);

        Files.copy(Paths.get("Doc.doc"), originPath);
        Files.copy(Paths.get("Doc.doc"), fakePath);
        doc.addSymbolToFile("64523");

        int i = 0;

        while (CRC.hash(doc.getBytesOriginalFile()) != CRC.hash(doc
                .getBytesFakeFile())) {
            printInfo(doc);
            boolean successFake = false;
            boolean successOrigin = false;

            String symbol = String.valueOf(i);
            while (!successFake) {
                try {
                    successFake = true;
                    doc.addSymbolToFile(symbol);
                } catch (IOException e) {
                    System.out.println("Trying again..." + i);
                    successFake = false;
                }
            }
            while (!successOrigin) {
                try {
                    successOrigin = true;
                    doc.addSymbolToFile2(symbol);
                } catch (IOException e) {
                    System.out.println("Trying again..." + i);
                    successOrigin = false;
                }
            }
            i++;
        }
        printInfo(doc);
        System.out.println(i);
    }

}
