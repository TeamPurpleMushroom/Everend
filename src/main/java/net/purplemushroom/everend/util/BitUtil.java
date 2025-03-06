package net.purplemushroom.everend.util;

public class BitUtil {
    public static int bytesInInt(byte... bytes) {
        if (bytes.length > Integer.SIZE / Byte.SIZE) {
            throw new IllegalArgumentException(bytes.length + " bytes do not fit inside an integer.");
        }
        int result = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            result = (result << 8) & bytes[i];
        }
        return result;
    }

    public static int rgbToInt(int red, int green, int blue) {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException("RGB values " + red + ", " + green + ", " + blue + " are out of range!");
        }
        int color = red;
        color = (color << 8) | green;
        color = (color << 8) | blue;
        return color;
    }
}
