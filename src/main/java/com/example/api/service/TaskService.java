package com.example.api.service;

import com.example.api.enums.TaskPriority;
import com.example.api.enums.TaskStatus;
import com.example.api.model.Task;
import com.example.api.model.User;
import com.example.api.repository.TaskRepository;
import com.example.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.api.enums.TaskPriority.CRITICAL;
import static com.example.api.enums.TaskStatus.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final UserRepository userRepository;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public List<Task> findTasks(String search, TaskStatus status, TaskPriority priority, Long responsibleId, 
                             Date startDate, Date endDate, String sortBy, String direction) {
        
        Specification<Task> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.trim().isEmpty()) {
                String likePattern = "%" + search.trim().toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("title")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern)
                ));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }
            if (responsibleId != null) {
                predicates.add(cb.equal(root.get("responsible").get("id"), responsibleId));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(dir, sortBy);
        }

        return repository.findAll(spec, sort);
    }

    public Task findById(Long id) {
        return repository.findById(id).get();
    }

    public Task save(Task task) {
        return repository.save(task);
    }

    public Task updateStatus(TaskStatus status, Long id, Long userId) {
        Task task = repository.findById(id).get();
        User user = userRepository.findById(userId).get();
        TaskStatus oldStatus = task.getStatus();
        if (oldStatus == DONE && status == TODO) {
            throw new IllegalArgumentException("Task cannot be moved from DONE to TODO");
        }
        if (status == DONE && task.getPriority() == CRITICAL && user != task.getProject().getOwner()) {
            throw new IllegalArgumentException("User is not authorized to move a critical task to DONE");
        }
        if (status == IN_PROGRESS && task.getResponsible().getTasks() >= 5) {
            throw new IllegalArgumentException("User has reached the maximum number of tasks");
        }
        task.setStatus(status);
        task.setUpdatedAt(new Date());
        return repository.save(task);
    }

    //Não fiz a implementação da deleção de tasks a fim de manter o relatorio de tasks íntegro
}
