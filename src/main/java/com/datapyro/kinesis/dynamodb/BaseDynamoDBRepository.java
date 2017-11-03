package com.datapyro.kinesis.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseDynamoDBRepository<T> {

    private final Class<T> persistentClass;

    private final String tableName, hashKeyField, orderKeyField;
    
    private DynamoDBMapper mapper;

    private DynamoDB dynamoDB;

    private Table table;

    private ObjectMapper jacksonMapper = new ObjectMapper();

    public BaseDynamoDBRepository(String tableName, String hashKeyField) {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.tableName = tableName;
        this.hashKeyField = hashKeyField;
        this.orderKeyField = null;
    }

    public BaseDynamoDBRepository(String tableName, String hashKeyField, String orderKeyField) {
        
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.tableName = tableName;
        this.hashKeyField = hashKeyField;
        this.orderKeyField = orderKeyField;
    }

    public void init() {
        AmazonDynamoDB client = DynamoDBClientFactory.getClient();
        mapper = new DynamoDBMapper(client);
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable(tableName);
    }

    public void save(T object) {
        mapper.save(object);
    }

    public void saveAll(List<T> objects) {
        mapper.batchSave(objects);
    }

    public List<T> findByHashKey(Object hashKey) {
        Map<String, AttributeValue> attributes = new HashMap<>();
        if (hashKey instanceof String) {
            attributes.put(":hkey", new AttributeValue().withS((String) hashKey));
        } else {
            attributes.put(":hkey", new AttributeValue().withN(hashKey.toString()));
        }

        DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
                .withKeyConditionExpression(hashKeyField + " = :hkey")
                .withExpressionAttributeValues(attributes)
                .withScanIndexForward(false)
                .withLimit(300);

        PaginatedQueryList<T> paginatedList = mapper.query(persistentClass, queryExpression);
        return new ArrayList<>(paginatedList);
    }

    public List<T> findBySecondaryKey(String indexName, String fieldName, Object value) {
        Index index = table.getIndex(indexName);

        ValueMap valueMap;
        if (value instanceof String) {
            valueMap = new ValueMap().withString(":id", (String) value);
        } else {
            valueMap = new ValueMap().withNumber(":id", (Number) value);
        }

        QuerySpec spec = new QuerySpec().withKeyConditionExpression("#id = :id")
                                        .withNameMap(new NameMap().with("#id", fieldName))
                                        .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = index.query(spec);
        Iterator<Item> it = items.iterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED), false)
                            .map(item -> convert(item)).collect(Collectors.toList());
    }

    public T singleResult(List<T> list) {
        return list != null && list.size() > 0 ?
                list.get(0) : null;
    }

    private T convert(Item item) {
        try {
            return item == null ? null : jacksonMapper.readValue(item.toJSON(), persistentClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
