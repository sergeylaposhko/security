package com.my;

/**
 * Created by Sergey on 09.05.2015.
 */
public class ResultBean {

    private String encrypted;
    private String decrypted;
    private long[] encryptKeys;
    private String[] encryptKeysStrings;
    private long[] decryptKeys;
    private String[] decryptKeysString;
    private long keyHash;

    public long getKeyHash() {
        return keyHash;
    }

    public void setKeyHash(long keyHash) {
        this.keyHash = keyHash;
    }

    public String[] getEncryptKeysStrings() {
        return encryptKeysStrings;
    }

    public void setEncryptKeysStrings(String[] encryptKeysStrings) {
        this.encryptKeysStrings = encryptKeysStrings;
    }

    public long[] getDecryptKeys() {
        return decryptKeys;
    }

    public void setDecryptKeys(long[] decryptKeys) {
        this.decryptKeys = decryptKeys;
    }

    public String[] getDecryptKeysString() {
        return decryptKeysString;
    }

    public void setDecryptKeysString(String[] decryptKeysString) {
        this.decryptKeysString = decryptKeysString;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public String getDecrypted() {
        return decrypted;
    }

    public void setDecrypted(String decrypted) {
        this.decrypted = decrypted;
    }

    public long[] getEncryptKeys() {
        return encryptKeys;
    }

    public void setEncryptKeys(long[] encryptKeys) {
        this.encryptKeys = encryptKeys;
    }
}