package com.datapyro.kinesis.example;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;

public class SampleRecordProcessorFactory implements IRecordProcessorFactory {
    
    @Override
    public IRecordProcessor createProcessor() {
        return new SampleRecordProcessor();
    }
    
}
