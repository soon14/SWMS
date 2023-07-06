package com.swms.utils.compress;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

@Slf4j
public class CompressUtils {

    public static byte[] compress(String value) {

        if (value == null) {
            return new byte[0];
        }

        try {
            byte[] input = value.getBytes(StandardCharsets.UTF_8);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream, new Deflater(Deflater.DEFAULT_COMPRESSION));

            deflaterOutputStream.write(input);
            deflaterOutputStream.finish();

            deflaterOutputStream.close();
            outputStream.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Unable to compress: {} , error:", value, e);
            return new byte[0];
        }
    }
}
