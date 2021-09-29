package com.elvis.springapp.dukachallenge.controllers;

import com.elvis.springapp.dukachallenge.domain.Task;
import com.elvis.springapp.dukachallenge.domain.TaskOwner;
import com.elvis.springapp.dukachallenge.exceptions.TodoException;
import com.elvis.springapp.dukachallenge.repository.TaskOwnerRepository;
import com.elvis.springapp.dukachallenge.security.ApplicationSecurityUser;
import com.elvis.springapp.dukachallenge.services.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/todo")
@Slf4j
@Api(value = "Todo Rest API",description = "Todo Controller ")
public class TaskController {
    TaskService todoService;
    final
    TaskOwnerRepository ownerRepository;

    public TaskController(TaskService todoService, TaskOwnerRepository ownerRepository) {
        this.todoService = todoService;
        this.ownerRepository = ownerRepository;
    }

    /**
     * @param todo <h4>Todo which has information of to do to be saves</h4>
     * @return Todo
     * */
    @ApiOperation(value = "Creating a new ToDO")
    @PostMapping("/save")
    public ResponseEntity createNewTask(@RequestBody @Valid Task todo,@AuthenticationPrincipal ApplicationSecurityUser user){

        try {

            TaskOwner owner = ownerRepository.findDistinctByUsername(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("No user found with specified username"));
            todo.setOwnedBy(owner);
            Task savedTodo = todoService.saveTask(todo);
            log.info("Saving a Todo " + todo);
            return new ResponseEntity(savedTodo, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage()+" Error Happened while saving new Todo");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return List<Todo>
     * */
    @ApiOperation(value = "Retrieving all Todo")
    @GetMapping("/findAll")
    public ResponseEntity<List<Task>> getAllTask(@AuthenticationPrincipal ApplicationSecurityUser user){
        log.info("Retrieving all Todos");
        TaskOwner owner = ownerRepository.findDistinctByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No user found with specified username"));

        return new ResponseEntity(todoService.getTaskByOwner(owner),HttpStatus.OK);
    }

    /**
     * @param id <h4>Task to do ID</h4>
     * @return Todo
     * */
    @ApiOperation(value = "Retrieving single Todo by It's ID")
    @GetMapping("/find/{id}")
    public ResponseEntity<Task> getOneTask(@PathVariable("id") UUID id){
        log.info("Retrieve single todo by id"+id);
        return new ResponseEntity(todoService.findById(id),HttpStatus.OK);
    }

    /**
     * @param id  <h4>THis is ID of the task to be Deleted </h4>
     * @return String
     * */
    @ApiOperation(value = "Deleting a Todo")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") UUID id){
        try{
            todoService.deleteTask(id);
            log.info("Deleting a Todo with ID "+id);
            return new ResponseEntity<>("Todo Deleted",HttpStatus.OK);
        }catch (Exception e) {
            log.error("Error occurred during deleting of a Todo");
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return Todo
     * */
    @ApiOperation(value = "Updating a Todo")
    @PatchMapping("/update")
    public ResponseEntity<Task>  updateToDo(@RequestBody Task todo) throws TodoException {
        log.info("Updating a Todo "+todo);
        return new ResponseEntity<>(todoService.updateTask(todo), HttpStatus.OK);
    }

    /**
     * @param response  <h4>This is the response that we have to send to user which have content type of text/cvs </h4> */
    @ApiOperation(value = "Exporting List of all Todo in Csv")
    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) {
        log.info("Exporting  list of all todo into csv");
        try {
            response.setContentType("text/csv");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
            response.setHeader(headerKey, headerValue);

            List<Task> listUsers = todoService.getAllTasks();

            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Todo Name", "Description", "Status"};
            String[] nameMapping = {"name", "description", "priority"};

            csvWriter.writeHeader(csvHeader);

            for (Task todo : listUsers) {
                csvWriter.write(todo, nameMapping);
            }

            csvWriter.close();
        }catch (Exception e){
            log.error("Error "+e.getMessage()+"  Occurred in Exporting a Todo list");
        }

    }

}
