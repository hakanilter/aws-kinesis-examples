package com.datapyro.kinesis.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetworkUtil {

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

}
