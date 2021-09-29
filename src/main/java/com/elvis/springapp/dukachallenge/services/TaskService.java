package com.elvis.springapp.dukachallenge.services;


import com.elvis.springapp.dukachallenge.exceptions.TodoException;
import com.elvis.springapp.dukachallenge.domain.Task;
import com.elvis.springapp.dukachallenge.domain.TaskOwner;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    public Task saveTask(Task t);
    public void deleteTask(UUID id);
    public Task updateTask(Task todo) throws TodoException;
    public Task findById(UUID id);
    public List<Task> getAllTasks();
    public List<Task> getTaskByOwner(TaskOwner owner);
}
