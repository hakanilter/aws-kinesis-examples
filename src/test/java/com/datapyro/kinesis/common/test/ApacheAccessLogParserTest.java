package com.datapyro.kinesis.common.test;

import com.datapyro.kinesis.common.ApacheAccessLogParser;
import com.datapyro.kinesis.model.ApacheAccessLog;
import org.junit.Test;

public class ApacheAccessLogParserTest {

    @Test
    public void test() {
        final String str = "165.57.90.53 - - [03/Nov/2017:14:25:21 +0000] \"GET /list HTTP/1.0\" 301 5012 \"http://www.dennis-martin" +
                ".com/list/category/author.asp\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_8_2) AppleWebKit/5362 (KHTML, like Gecko) Chrome/14.0.859.0 Safari/5362\"";
        ApacheAccessLogParser parser = new ApacheAccessLogParser();
        ApacheAccessLog log = parser.parseFromLogLine(str);

        System.out.println(log);
    }
    
}
