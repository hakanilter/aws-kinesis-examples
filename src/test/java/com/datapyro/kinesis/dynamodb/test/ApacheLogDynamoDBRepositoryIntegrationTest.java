package com.datapyro.kinesis.dynamodb.test;

import com.datapyro.kinesis.common.ApacheAccessLogParser;
import com.datapyro.kinesis.dynamodb.ApacheLogDynamoDBRepository;
import com.datapyro.kinesis.dynamodb.base.DynamoDBClientFactory;
import com.datapyro.kinesis.entity.ApacheLogEntity;
import com.datapyro.kinesis.util.IOUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApacheLogDynamoDBRepositoryIntegrationTest {

    private ApacheLogDynamoDBRepository apacheLogRepository;

    private ApacheAccessLogParser parser = new ApacheAccessLogParser();

    @Before
    public void setUp() {
        apacheLogRepository = new ApacheLogDynamoDBRepository();
        apacheLogRepository.init();
    }

    @Test
    public void test() throws Exception {
        String input = IOUtil.readFromClasspath("apache.log");
        List<ApacheLogEntity> entities = Arrays.stream(input.split("\n"))
              .map(line -> parser.parse(line))
              .collect(Collectors.toList());
        apacheLogRepository.saveAll(entities);
    }

    @After
    public void tearDown() {
        DynamoDBClientFactory.getClient().shutdown();
    }

}
