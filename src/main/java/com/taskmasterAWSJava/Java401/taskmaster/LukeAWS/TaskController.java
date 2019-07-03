package com.taskmasterAWSJava.Java401.taskmaster.LukeAWS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TaskController {
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
    public void displayTask(@RequestParam String title, @RequestParam String description, @RequestParam String status, @RequestParam String assignee){
        Task newTask = new Task(title, description, status, assignee);
        taskRepository.save(newTask);
        List<Task> allTasks = (List) taskRepository.findAll();
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
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable String id){
        taskRepository.deleteById(id);
    }
}
