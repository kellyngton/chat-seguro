import java.util.Random;
public class CriptografiaEngine {

    public static String encrypt(String message, int key, long seed) {
        Random random = new Random(seed);
        char[] encryptedChars = new char[message.length()];

        for (int i = 0; i < message.length(); i++) {
            char encryptedChar = (char) (message.charAt(i) ^ key ^ random.nextInt(256));
            encryptedChars[i] = encryptedChar;
        }

        return new String(encryptedChars);
    }

    public static String decrypt(String encryptedMessage, int key, long seed) {
        return encrypt(encryptedMessage, key, seed);
    }

    public CriptografiaEngine() {
    }
}
