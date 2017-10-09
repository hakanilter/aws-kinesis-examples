package com.datapyro.kinesis;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;

public class SampleRecordProcessor implements IRecordProcessor {

    @Override
    public void initialize(InitializationInput initializationInput) {
        System.out.println(getClass().getSimpleName() + " initialize");
    }

    @Override
    public void processRecords(ProcessRecordsInput input) {
        input.getRecords().stream().forEach(rec -> System.out.println(new String(rec.getData().array())));
    }

    @Override
    public void shutdown(ShutdownInput shutdownInput) {
        System.out.println(getClass().getSimpleName() + " shutdown");
    }
    
}
