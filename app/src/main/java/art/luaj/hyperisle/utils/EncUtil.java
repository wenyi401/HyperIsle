package art.luaj.hyperisle.utils;


import java.nio.charset.StandardCharsets;

public class EncUtil {


    private static final byte[] SECRET_KEY = {0x04, 0x01, 0x02, 0x06, 0x52}; // 自定义密钥，根据需要修改

    public static String encrypt(String data) {
        byte[] encryptedBytes = encryptBytes(data.getBytes(StandardCharsets.UTF_8));
        return byteArrayToHexString(encryptedBytes);
    }

    public static String encrypt(int data) {
        byte[] encryptedBytes = encryptBytes(Integer.toString(data).getBytes(StandardCharsets.UTF_8));
        return byteArrayToHexString(encryptedBytes);
    }

    public static int decryptInt(String encryptedData) {
        byte[] encryptedBytes = hexStringToByteArray(encryptedData);
        byte[] decryptedBytes = decryptBytes(encryptedBytes);
        String data = new String(decryptedBytes, StandardCharsets.UTF_8);
        return Integer.parseInt(data);
    }

    public static String decryptStr(String encryptedData) {
        byte[] encryptedBytes = hexStringToByteArray(encryptedData);
        byte[] decryptedBytes = decryptBytes(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static byte[] encryptBytes(byte[] data) {
        byte[] encryptedBytes = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            encryptedBytes[i] = (byte) (data[i] ^ SECRET_KEY[i % SECRET_KEY.length]);
        }
        return encryptedBytes;
    }

    private static byte[] decryptBytes(byte[] encryptedBytes) {
        byte[] decryptedBytes = new byte[encryptedBytes.length];
        for (int i = 0; i < encryptedBytes.length; i++) {
            decryptedBytes[i] = (byte) (encryptedBytes[i] ^ SECRET_KEY[i % SECRET_KEY.length]);
        }
        return decryptedBytes;
    }

    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) +
                Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }

}