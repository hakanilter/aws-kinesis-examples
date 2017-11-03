package com.datapyro.kinesis.dynamodb;

import com.datapyro.kinesis.entity.ApacheLogEntity;

public class ApacheLogDynamoDBRepository extends BaseDynamoDBRepository<ApacheLogEntity> {

    private static final String TABLE_NAME = "ApacheLogs";
    private static final String HASH_KEY_FIELD = "hashId";
    private static final String ORDER_KEY_FIELD = "dateTime";

    public ApacheLogDynamoDBRepository() {
        super(TABLE_NAME, HASH_KEY_FIELD, ORDER_KEY_FIELD);
    }
    
}
