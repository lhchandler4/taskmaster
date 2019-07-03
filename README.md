# Task Master

## Description 
- Task Master is a task-tracking application with the same basic goal as Trello: allow users to keep 
track of tasks to be done and their status. While we’ll start today with a basic feature set,
 we will continue building out the capabilities of this application over time.
 
## API
- @GetMapping("/tasks")
    - Get all the tasks
- @GetMapping("/users/{name}/tasks")
    - Get all the tasks assigned to an assignee
- @PostMapping("/tasks")
    - Post a task 
- @PutMapping("/tasks/{id}/state")
    - Changes the status of a task
- @PutMapping("/tasks/{id}/assign/{assignee}")
    - Assigns an assignee to a task and sets the status to Assigned

 
## Deployed Site
- http://taskmaster1-dev.us-east-2.elasticbeanstalk.com

