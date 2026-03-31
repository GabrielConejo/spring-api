package com.example.api.service;

import com.example.api.model.Project;
import com.example.api.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServer {

    private final ProjectRepository repository;

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Project save(Project project) {
        return repository.save(project);
    }

    public Project findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
