package com.datapyro.kinesis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class IOUtil {

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static String readFromClasspath(String resourceName) throws IOException {
        InputStream inputStream = IOUtil.class
                .getClassLoader()
                .getResourceAsStream(resourceName);
        return readFromInputStream(inputStream);
    }
    
}
