package com.datapyro.kinesis.common;

import com.datapyro.kinesis.entity.ApacheLogEntity;
import com.datapyro.kinesis.model.ApacheAccessLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents an Apache access log line.
 * See http://httpd.apache.org/docs/2.2/logs.html for more details.
 */
public class ApacheAccessLogParser {

    // Example Apache log line:
    //   127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] "GET /home.html HTTP/1.1" 200 2048
    private static final String LOG_ENTRY_PATTERN =
            // 1:IP  2:client 3:user 4:date time 5:method 6:req 7:proto 8:respcode 9:size 10: url 11: user agent
            "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+) \"(\\S+)\"";

    private static final Pattern PATTERN = Pattern.compile(LOG_ENTRY_PATTERN);

    private final Logger logger = LoggerFactory.getLogger(ApacheAccessLog.class);

    public ApacheLogEntity parse(String line) {
        ApacheAccessLog log = parseLog(line);
        return new ApacheLogEntity().setIpAddress(log.getIpAddress())
                                    .setClientId(log.getClientId())
                                    .setUserID(log.getUserID())
                                    .setContentSize(log.getContentSize())
                                    .setDateTime(log.getDateTimeString())
                                    .setEndpoint(log.getEndpoint())
                                    .setIpAddress(log.getIpAddress())
                                    .setMethod(log.getMethod())
                                    .setProtocol(log.getProtocol())
                                    .setResponseCode(log.getResponseCode())
                                    .setUrl(log.getUrl());
    }

    private ApacheAccessLog parseLog(String log) {
        Matcher m = PATTERN.matcher(log);
        if (!m.find()) {
            logger.info("Cannot parse logline" + log);
            throw new RuntimeException("Error parsing logline");
        }
        
        return new ApacheAccessLog(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6),
                                   m.group(7), Integer.parseInt(m.group(8)), Long.parseLong(m.group(9)), m.group(10));
    }


}
