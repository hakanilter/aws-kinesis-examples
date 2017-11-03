package com.datapyro.kinesis.logparser;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.datapyro.kinesis.util.KinesisUtil;

public class LogParserKinesisConsumer {

    public void run() {
        final String appName = "LogParserKinesisConsumerApp";
        final String streamName = "apache-logs-kinesis-stream";
        final KinesisClientLibConfiguration config = KinesisUtil.getDefaultConfig(appName, streamName);
        final IRecordProcessorFactory recordProcessorFactory = new LogParserRecordProcessorFactory();
        final Worker worker = new Worker.Builder().recordProcessorFactory(recordProcessorFactory).config(config).build();
        worker.run();
    }

    public static void main(String[] args) {
        new LogParserKinesisConsumer().run();
    }

}
