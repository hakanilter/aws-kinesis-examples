package com.datapyro.kinesis.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;

import static com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration.*;
import static com.datapyro.kinesis.common.KinesisExampleConstants.REGION;

public final class KinesisUtil {

    public static KinesisClientLibConfiguration getDefaultConfig(String appName, String streamName) {
        final String workerId = "worker-" + NetworkUtil.getHostName();
        final AWSCredentialsProvider credentialsProvider = getCredentialProvider();
        return new KinesisClientLibConfiguration(
               appName, streamName, "https://kinesis." + REGION + ".amazonaws.com", null,
               DEFAULT_INITIAL_POSITION_IN_STREAM, credentialsProvider, credentialsProvider, credentialsProvider,
               DEFAULT_FAILOVER_TIME_MILLIS, workerId, DEFAULT_MAX_RECORDS, DEFAULT_IDLETIME_BETWEEN_READS_MILLIS,
               DEFAULT_DONT_CALL_PROCESS_RECORDS_FOR_EMPTY_RECORD_LIST, DEFAULT_PARENT_SHARD_POLL_INTERVAL_MILLIS,
               DEFAULT_SHARD_SYNC_INTERVAL_MILLIS, DEFAULT_CLEANUP_LEASES_UPON_SHARDS_COMPLETION, new ClientConfiguration(),
               new ClientConfiguration(), new ClientConfiguration(), DEFAULT_TASK_BACKOFF_TIME_MILLIS,
               DEFAULT_METRICS_BUFFER_TIME_MILLIS, DEFAULT_METRICS_MAX_QUEUE_SIZE,
               DEFAULT_VALIDATE_SEQUENCE_NUMBER_BEFORE_CHECKPOINTING, REGION, DEFAULT_SHUTDOWN_GRACE_MILLIS);
    }

    public static AWSCredentialsProvider getCredentialProvider() {
        return System.getenv("AWS_ACCESS_KEY") != null ?
                new EnvironmentVariableCredentialsProvider() :
                new InstanceProfileCredentialsProvider();
    }

}
