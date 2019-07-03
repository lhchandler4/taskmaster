package com.taskmasterAWSJava.Java401.taskmaster.LukeAWS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public Iterable<Task> getTask(){
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public void displayTask(@RequestParam String title, @RequestParam String description, @RequestParam String status){
        Task task = new Task(title, description, status);
        taskRepository.save(task);
        List<Task> allTasks = (List) taskRepository.findAll();
    }

    @PutMapping("/tasks/{id}/state")
    public List<Task> putState(@PathVariable UUID id){
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

}
