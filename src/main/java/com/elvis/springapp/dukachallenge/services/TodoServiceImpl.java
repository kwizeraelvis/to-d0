package com.elvis.springapp.dukachallenge.services;

import com.elvis.springapp.dukachallenge.repository.TaskRepository;
import com.elvis.springapp.dukachallenge.domain.Task;
import com.elvis.springapp.dukachallenge.domain.TaskOwner;
import com.elvis.springapp.dukachallenge.exceptions.TodoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TodoServiceImpl implements TaskService {
    TaskRepository repository;

    public TodoServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * @param t
     * @return Task
     * */
    @Override
    public Task saveTask(Task t) {
        return repository.save(Task.builder()
                .id(UUID.randomUUID())
                .title(t.getTitle())
                .dateTime(t.getDateTime())
                .description(t.getDescription())
                .ownedBy(t.getOwnedBy())
                .build()
        );
    }

    /**
     * @param id
     * @return void
     * */
    @Override
    public void deleteTask(UUID id) {
        repository.deleteById(id);
    }

    /**
     * @param t
     * @return Task
     * */
    @Override
    public Task updateTask(Task t) throws TodoException {
        System.out.println(t);
        Task todo = repository.findById(t.getId()).get();
        if(todo.equals(null)){
            throw new TodoException("Task to update not exist");
        }
        if(t.getTitle()!=null){
            todo.setTitle(t.getTitle());
        }
        if(t.getDescription()!=null){
            todo.setDescription(t.getDescription());
        }
        if(t.getStatus()!=null){
            todo.setStatus(t.getStatus());
        }
        if(t.getDateTime()!=null){
            todo.setDateTime(t.getDateTime());
        }

        Task updatedTodo = repository.save(todo);
        return  updatedTodo;
    }


    /**
     * @param id
     * @return Task
     * */
    @Override
    public Task findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Task> getTaskByOwner(TaskOwner owner) {
        return repository.findDistinctByOwnedBy(owner);
    }


    /**
     * @return List<Task>
     * */
    @Override
    public List<Task> getAllTasks() {
        List<Task> todos =  new ArrayList<>();
        repository.findAll().forEach(t -> todos.add(t));
        return todos;
    }
}

