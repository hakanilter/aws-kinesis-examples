package com.datapyro.kinesis.example;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.CreateStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;

import static com.datapyro.kinesis.common.KinesisExampleConstants.REGION;
import static com.datapyro.kinesis.common.KinesisExampleConstants.SHARD_COUNT;

/**
 * Create stream example
 * 
 * https://docs.aws.amazon.com/streams/latest/dev/kinesis-using-sdk-java-create-stream.html
 */
public class CreateStreamExample {
    
    private void run() throws Exception {
        final String streamName = "test";
        AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard();
        clientBuilder.setRegion(REGION);
        clientBuilder.setCredentials(new EnvironmentVariableCredentialsProvider());
        clientBuilder.setClientConfiguration(new ClientConfiguration());

        AmazonKinesis client = clientBuilder.build();

        CreateStreamRequest createStreamRequest = new CreateStreamRequest();
        createStreamRequest.setStreamName(streamName);
        createStreamRequest.setShardCount(SHARD_COUNT);

        client.createStream(createStreamRequest);
        DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
        describeStreamRequest.setStreamName(streamName);

        // wait up to 10 minutes for stream creation
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (10 * 60 * 1000);
        while (System.currentTimeMillis() < endTime) {
            Thread.sleep(20 * 1000);

            DescribeStreamResult describeStreamResponse = client.describeStream(describeStreamRequest);
            String streamStatus = describeStreamResponse.getStreamDescription().getStreamStatus();
            if (streamStatus.equals("ACTIVE")) {
                break;
            }

            Thread.sleep(1000);
        }
        if (System.currentTimeMillis() >= endTime) {
            throw new RuntimeException("Stream " + streamName + " never went active");
        }
        System.out.println("Stream " + streamName + " has been created");
    }

    public static void main(String[] args) throws Exception {
        new CreateStreamExample().run();
    }

}
