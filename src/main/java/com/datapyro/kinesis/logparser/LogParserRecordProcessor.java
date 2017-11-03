package com.datapyro.kinesis.logparser;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.datapyro.kinesis.common.ApacheAccessLogParser;
import com.datapyro.kinesis.dynamodb.ApacheLogDynamoDBRepository;
import com.datapyro.kinesis.dynamodb.DynamoDBClientFactory;
import com.datapyro.kinesis.entity.ApacheLogEntity;

import java.util.List;
import java.util.stream.Collectors;

public class LogParserRecordProcessor implements IRecordProcessor {

    private ApacheLogDynamoDBRepository apacheLogRepository;

    private ApacheAccessLogParser parser;

    @Override
    public void initialize(InitializationInput initializationInput) {
        apacheLogRepository = new ApacheLogDynamoDBRepository();
        apacheLogRepository.init();
        
        parser = new ApacheAccessLogParser();
    }

    @Override
    public void processRecords(ProcessRecordsInput input) {
        // parse logs and convert to DynamoDB entity
        List<ApacheLogEntity> entities = input.getRecords().stream()
                                          .map(rec -> parser.parse(new String(rec.getData().array())))
                                          .collect(Collectors.toList());
        apacheLogRepository.saveAll(entities);
    }

    @Override
    public void shutdown(ShutdownInput shutdownInput) {
        DynamoDBClientFactory.getClient().shutdown();
    }

}
