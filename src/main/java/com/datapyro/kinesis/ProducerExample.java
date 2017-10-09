package com.datapyro.kinesis;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.datapyro.kinesis.common.KinesisExampleConstants.REGION;
import static com.datapyro.kinesis.common.KinesisExampleConstants.STREAM_NAME;

/**
 * Producer example
 *
 * https://docs.aws.amazon.com/streams/latest/dev/developing-producers-with-sdk.html
 */
public class ProducerExample {

    private void run() {
        AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard();
        clientBuilder.setRegion(REGION);
        clientBuilder.setCredentials(new EnvironmentVariableCredentialsProvider());
        clientBuilder.setClientConfiguration(new ClientConfiguration());

        AmazonKinesis client = clientBuilder.build();

        PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        putRecordsRequest.setStreamName(STREAM_NAME);
        List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(String.valueOf(i).getBytes()));
            putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", i));
            putRecordsRequestEntryList.add(putRecordsRequestEntry);
        }

        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        PutRecordsResult putRecordsResult = client.putRecords(putRecordsRequest);
        System.out.println("Put Result" + putRecordsResult);
    }

    public static void main(String[] args) {
        new ProducerExample().run();
    }

}
