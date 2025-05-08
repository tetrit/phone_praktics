package ru.mirea.aleev.mireaproject;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class CryptoUtils {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_FACTORY = "PBKDF2WithHmacSHA256";
    private static final int IV_SIZE = 16;
    private static final int KEY_SIZE = 256;
    private static final int ITERATIONS = 65536;
    private static final String PASSWORD = "YourSuperSecretPassword123!"; // Замените на свой пароль

    public static byte[] encrypt(byte[] data) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        SecretKey secretKey = generateKey(salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = new byte[IV_SIZE];
        random.nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        byte[] encryptedData = cipher.doFinal(data);
        byte[] result = new byte[salt.length + iv.length + encryptedData.length];
        System.arraycopy(salt, 0, result, 0, salt.length);
        System.arraycopy(iv, 0, result, salt.length, iv.length);
        System.arraycopy(encryptedData, 0, result, salt.length + iv.length, encryptedData.length);
        return result;
    }

    public static byte[] decrypt(byte[] encryptedData) throws Exception {
        byte[] salt = new byte[16];
        byte[] iv = new byte[IV_SIZE];
        byte[] data = new byte[encryptedData.length - salt.length - iv.length];

        System.arraycopy(encryptedData, 0, salt, 0, salt.length);
        System.arraycopy(encryptedData, salt.length, iv, 0, iv.length);
        System.arraycopy(encryptedData, salt.length + iv.length, data, 0, data.length);

        SecretKey secretKey = generateKey(salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    private static SecretKey generateKey(byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(), salt, ITERATIONS, KEY_SIZE);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY);
        byte[] key = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }
}