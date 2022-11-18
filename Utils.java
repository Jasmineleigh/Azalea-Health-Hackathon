package ver1;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/*
 * In the original project, this was written by Chase Vaughn
 */

public final class Utils {
    public static final int UUID_BYTES = Long.BYTES * 2;

    public static byte[] sha1(byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(bytes);
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sha512(byte[] salt, byte[] bytes) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(salt);
        byte[] hash = md.digest(bytes);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < hash.length; i++) {
            sb.append(String.format("%02x", hash[i]));
        }
        return sb.toString();
    }

    public static byte[] uuidToBytes(UUID uuid) {
        byte[] most = longToBytes(uuid.getMostSignificantBits());
        byte[] least = longToBytes(uuid.getLeastSignificantBits());
        byte[] out = new byte[most.length + least.length];
        for(int i = 0; i < most.length; i++) {
            out[i] = most[i];
            out[i + most.length] = least[i];
        }
        return out;
    }

    public static UUID uuidFromBytes(byte[] bytes) {
        long most = 0;
        long least = 0;
        for(int i = 0; i < Long.BYTES; i++) {
            most <<= Byte.SIZE;
            most |= (bytes[i] & 0xFF);
            least <<= Byte.SIZE;
            least |= (bytes[i + Long.BYTES] & 0xFF);
        }
        return new UUID(most, least);
    }

    public static UUID uuidFromBytes(byte[] bytes, int start, int end) {
        return uuidFromBytes(Arrays.copyOfRange(bytes, start, end));
    }

    public static byte[] longToBytes(long l) {
        byte[] out = new byte[Long.BYTES];
        for(int i = Long.BYTES - 1; i >= 0; i--) {
            out[i] = (byte) (l & 0xFF);
            l >>= Byte.SIZE;
        }
        return out;
    }

    public static long bytesToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static long bytesToLong(byte[] bytes, int start, int end) {
        return bytesToLong(Arrays.copyOfRange(bytes, start, end));
    }

    public static byte[] intToBytes(int n) {
        byte[] out = new byte[Integer.BYTES];
        for(int i = Integer.BYTES - 1; i >= 0; i--) {
            out[i] = (byte) (n & 0xFF);
            n >>= Byte.SIZE;
        }
        return out;
    }

    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static int bytesToInt(byte[] bytes, int start, int end) {
        return bytesToInt(Arrays.copyOfRange(bytes, start, end));
    }

    public static void printByteArray(byte[] bytes) {
        for(int i = 0; i < bytes.length; i++) {
            if(i > 0 && i % 32 == 0) System.out.println();
            System.out.printf("%02X ", bytes[i]);
        }
        System.out.println();
    }
}
