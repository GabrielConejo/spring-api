package com.example.api.controller;

import com.example.api.model.Project;
import com.example.api.service.ProjectServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectServer service;

    @GetMapping
    public List<Project> list() {
        return service.findAll();
    }

    @GetMapping("/id")
    public Project findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return service.save(project);
    }

    @DeleteMapping("/id")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/id")
    public Project update(@PathVariable Long id, @RequestBody Project project) {
        return service.save(project);
    }
}
