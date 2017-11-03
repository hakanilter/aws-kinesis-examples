package com.datapyro.kinesis.dynamodb;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class DynamoDBClientFactory {

    private static AmazonDynamoDB client;

    public static AmazonDynamoDB getClient() {
        if (client == null) {
            client = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider().getCredentials());
            client.setRegion(Region.getRegion(Regions.EU_WEST_1));
        }
        return client;
    }

}
