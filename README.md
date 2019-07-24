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
- @PostMapping("/tasks/{id}/images")
    - Post a image to be associated with a task
- @PutMapping("/tasks/{id}/state")
    - Changes the status of a task
- @PutMapping("/tasks/{id}/assign/{assignee}")
    - Assigns an assignee to a task and sets the status to Assigned
- @DeleteMapping("/tasks/{id}")
    - Delete a task

- When a task is deleted from Dynamo, trigger a message that will fire a lambda to remove any images associated to it from S3
- Instead of having S3 run the resizer automatically on upload, evaluate the size of the image in your Java code and then send
 a message to a Q, that will in turn trigger the lambda resizer -- only when the image > 350k
 
## Deployed Site
- http://taskmaster1-dev.us-east-2.elasticbeanstalk.com
- Front End
    -  http://serverless-taskmaster-app.s3-website.us-east-2.amazonaws.com

## How to use the Lambda function
- The lambda function will automatically resize the image which is uploaded to the 
front end website to a 50px by 50px thumbnail and the url to that image will be available
in the JSON data that is displayed after submission of the image

## Issues with Lambda deployment
- Encountered a lot of issues. The first was with IAM user permissions.
Solved that problem and then there were many issues with my actual lambda function.
This was probably due to the fact that I was modifying the lambda resizing function
from https://docs.aws.amazon.com/lambda/latest/dg/with-s3-example.html. 

## Issues with SNS 
- Still experiencing difficulties with SNS and getting it to send a text message. 
Interacting with SES is also hard to trouble shoot. I put the routes in controller for SNS.