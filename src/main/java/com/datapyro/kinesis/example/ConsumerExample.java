package com.datapyro.kinesis.example;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.datapyro.kinesis.util.KinesisUtil;

public class ConsumerExample {

    private void run() {
        final String appName = "ConsumerExample";
        final String streamName = "test";
        
        final KinesisClientLibConfiguration config = KinesisUtil.getDefaultConfig(appName, streamName);

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
