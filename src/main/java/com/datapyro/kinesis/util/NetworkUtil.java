package com.datapyro.kinesis.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetworkUtil {

    public static String getHostName() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

}
