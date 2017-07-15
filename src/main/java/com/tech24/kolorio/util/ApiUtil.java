package com.tech24.kolorio.util;

import org.apache.commons.codec.Charsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by oscarmakala on 15/07/2017.
 */
public class ApiUtil {

    /**
     * Convert a string to a enum type.
     *
     * @param value  string value
     * @param values enum values
     * @param <T>    enum type
     * @return converted enum if able to convert; null otherwise
     */
    public static <T extends Enum<?>> T enumFromString(final String value, final T[] values) {
        if (value == null) {
            return null;
        }

        for (T v : values) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }

        return null;
    }


    /**
     * Reads an {@link InputStream} into a String using the UTF-8 encoding.
     * Note that this method closes the InputStream passed to it.
     *
     * @param is The InputStream to be read.
     * @return The contents of the InputStream as a String.
     * @throws IOException
     */
    public static String readAsString(InputStream is) throws IOException {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return sb.toString();
    }
}
