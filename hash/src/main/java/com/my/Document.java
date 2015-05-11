package com.my;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Document {

    private String originalFile;
    private String fakeFile;

    public Document(String originalFile, String fakeFile) {
        this.originalFile = originalFile;
        this.fakeFile = fakeFile;
    }

    public byte[] getBytesOriginalFile() throws IOException {
        Path path = Paths.get(originalFile);
        byte[] data = Files.readAllBytes(path);
        return data;
    }

    public byte[] getBytesFakeFile() throws IOException {
        Path path = Paths.get(fakeFile);
        byte[] data = Files.readAllBytes(path);
        return data;
    }

    public void addSymbolToFile(String symbol) throws IOException {
        String temtFile = "tempFile.doc";
        copyFile(fakeFile, temtFile);
        POIFSFileSystem fs = null;
        fs = new POIFSFileSystem(new FileInputStream(fakeFile));
        HWPFDocument doc = new HWPFDocument(fs);
        Range range = doc.getRange();
        CharacterRun run = range.insertAfter(symbol);
        try (OutputStream out = new FileOutputStream(temtFile)) {
            doc.write(out);
            out.flush();
        }
        copyFile(temtFile, fakeFile);
    }

    public void addSymbolToFile2(String symbol) throws IOException {
        String temtFile = "tempFile2.doc";
        copyFile(originalFile, temtFile);
        POIFSFileSystem fs = null;
        fs = new POIFSFileSystem(new FileInputStream(originalFile));
        HWPFDocument doc = new HWPFDocument(fs);
        Range range = doc.getRange();
        CharacterRun run = range.insertAfter(symbol);
        try (OutputStream out = new FileOutputStream(temtFile)) {
            doc.write(out);
            out.flush();
        }
        copyFile(temtFile, originalFile);
    }

    public void copyFile(String src, String dest) throws IOException {
        POIFSFileSystem fs = null;
        fs = new POIFSFileSystem(new FileInputStream(src));
        HWPFDocument doc = new HWPFDocument(fs);
        try (OutputStream out = new FileOutputStream(dest)) {
            doc.write(out);
            out.flush();
            out.close();
        }
    }

    public void copyOriginalToFake() throws IOException {
        copyFile(originalFile, fakeFile);
    }
}
