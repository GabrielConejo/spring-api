package com.example.api.controller;

import com.example.api.enums.TaskPriority;
import com.example.api.enums.TaskStatus;
import com.example.api.model.Task;
import com.example.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("/find")
    public List<Task> findTasks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long responsibleId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction) {
        return taskService.findTasks(search, status, priority, responsibleId, startDate, endDate, sortBy, direction);
    }

    @PostMapping
    public Task save(@RequestBody Task task) {
        return taskService.save(task);
    }

    @PutMapping("/updateStatus")
    public Task updateStatus(@RequestParam TaskStatus status, @RequestParam Long id, @RequestParam Long userId) {
        return taskService.updateStatus(status, id, userId);
    }
}
