package com.taskmasterAWSJava.Java401.taskmaster.LukeAWS;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import javafx.application.Application;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")

public class TaskRepositoryTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository repository;

    private static final String title = "do work";
    private static final String description = "do the work";
    private static final String status = "Accepted";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Task.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        dynamoDBMapper.batchDelete((List<Task>)repository.findAll());
    }

    @Test
    public void readAndWriteTestCase() {
        Task tonga = new Task(title, description, status);
        repository.save(tonga);

        List<Task> result = (List<Task>) repository.findAll();

        assertTrue("Not empty", result.size() > 0);
        assertTrue("Contains title", result.get(0).getTitle().equals(title));
    }

    @Test
    public void testTrue() {
        assertTrue(true);
    }

}