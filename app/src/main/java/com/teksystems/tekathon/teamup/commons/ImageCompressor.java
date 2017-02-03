package com.teksystems.tekathon.teamup.commons;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by Mayank Tiwari on 10/06/16.
 */

public class ImageCompressor {

    private static final String TAG = "ImageCompressor";

    public static byte[] compressImage(byte[] originalBytes) {
        Deflater deflater = new Deflater();
        deflater.setInput(originalBytes);
        deflater.finish();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        while (!deflater.finished()) {
            int byteCount = deflater.deflate(buf);
            baos.write(buf, 0, byteCount);
        }
        deflater.end();

        byte[] compressedBytes = baos.toByteArray();

        Log.d(TAG, "Original: " + originalBytes.length / 1024 + " Kb");
        Log.d(TAG, "Compressed: " + compressedBytes.length / 1024 + " Kb");

        return compressedBytes;
    }

    public static byte[] decompress(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();

            byte[] output = outputStream.toByteArray();
            Log.d(TAG, "Original: " + output.length / 1024 + " Kb");
            Log.d(TAG, "Compressed: " + data.length / 1024 + " Kb");
            return output;
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
