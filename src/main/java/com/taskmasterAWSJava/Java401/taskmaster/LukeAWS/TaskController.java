package com.taskmasterAWSJava.Java401.taskmaster.LukeAWS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class TaskController {
    private S3Client s3Client;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public Iterable<Task> getTask(){
        return taskRepository.findAll();
    }

    @GetMapping("/users/{assignee}/tasks")
    public Iterable<Task> getAssigneeTasks(@PathVariable String assignee){
        return taskRepository.findByAssignee(assignee);
    }

    @PostMapping("/tasks")
    public ResponseEntity createTasks(@RequestBody Task newTask){
        if(newTask.getAssignee() != null){
            newTask.setStatus("Assigned");
        } else {
            newTask.setStatus("Available");
        }
        taskRepository.save(newTask);
        return new ResponseEntity(newTask, HttpStatus.OK);
    }

    @PostMapping("/tasks/{id}/images")
    public Task uploadFile(@PathVariable String id, @RequestPart(value = "file") MultipartFile file){
        Task task = taskRepository.findById(id).get();
        String image = this.s3Client.uploadFile(file);
        task.setImage(image);
        String[] pics = image.split("/");
        String thumbnail = pics[pics.length -1];
        task.setThumbnailImage("https://serverless-taskmaster-app-resized.s3.us-east-2.amazonaws.com/resized-" + thumbnail);
        taskRepository.save(task);
        return task;
    }

    //Change Status
    @PutMapping("/tasks/{id}/state")
    public List<Task> putState(@PathVariable String id){
        Task task = taskRepository.findById(id).get();

        if(task.getStatus().equals("Available")){
            task.setStatus("Assigned");
        } else if(task.getStatus().equals("Assigned")){
            task.setStatus("Accepted");
        } else if(task.getStatus().equals("Accepted")){
            task.setStatus("Finished");
        }
        taskRepository.save(task);
        List<Task> tasks = (List) taskRepository.findAll();
        return tasks;
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public List<Task> putAssignee(@PathVariable String id, @PathVariable String assignee){
        Task task = taskRepository.findById(id).get();
        task.setAssignee(assignee);
        task.setStatus("Assigned");
        taskRepository.save(task);
        List<Task> tasks = (List) taskRepository.findAll();
        return tasks;

        AmazonSNSClient snsClient = new AmazonSNSClient();
        String message = "My SMS message";
        String phoneNumber = "+1XXXXXX2092";
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
    }

    public static void sendSMSMessage(AmazonSNSClient snsClient, String message, String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest().withMessage(message).withPhoneNumber(phoneNumber).withMessageAttributes(smsAttributes));
        System.out.println(result);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable String id){
        taskRepository.deleteById(id);
    }
}
