package com.datapyro.kinesis.example;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;

import static com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration.*;
import static com.datapyro.kinesis.common.KinesisExampleConstants.REGION;
import static com.datapyro.kinesis.common.KinesisExampleConstants.STREAM_NAME;

public class ConsumerExample {

    final String appName = "ConsumerExample";

    private void run() {
        final EnvironmentVariableCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();

        // What kind of constructor method is this???
        final KinesisClientLibConfiguration config = new KinesisClientLibConfiguration(
                appName,
                STREAM_NAME,
                "https://kinesis." + REGION + ".amazonaws.com",
                null,
                DEFAULT_INITIAL_POSITION_IN_STREAM,
                credentialsProvider,
                credentialsProvider,
                credentialsProvider,
                DEFAULT_FAILOVER_TIME_MILLIS,
                "test",
                DEFAULT_MAX_RECORDS,
                DEFAULT_IDLETIME_BETWEEN_READS_MILLIS,
                DEFAULT_DONT_CALL_PROCESS_RECORDS_FOR_EMPTY_RECORD_LIST,
                DEFAULT_PARENT_SHARD_POLL_INTERVAL_MILLIS,
                DEFAULT_SHARD_SYNC_INTERVAL_MILLIS,
                DEFAULT_CLEANUP_LEASES_UPON_SHARDS_COMPLETION,
                new ClientConfiguration(),
                new ClientConfiguration(),
                new ClientConfiguration(),
                DEFAULT_TASK_BACKOFF_TIME_MILLIS,
                DEFAULT_METRICS_BUFFER_TIME_MILLIS,
                DEFAULT_METRICS_MAX_QUEUE_SIZE,
                DEFAULT_VALIDATE_SEQUENCE_NUMBER_BEFORE_CHECKPOINTING,
                REGION, DEFAULT_SHUTDOWN_GRACE_MILLIS);

        final IRecordProcessorFactory recordProcessorFactory = new SampleRecordProcessorFactory();
        final Worker worker = new Worker.Builder()
                .recordProcessorFactory(recordProcessorFactory)
                .config(config)
                .build();
        worker.run();
    }

    public static void main(String[] args) {
        new ConsumerExample().run();
    }

}
