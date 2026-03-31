package com.example.api.model;

import com.example.api.enums.TaskPriority;
import com.example.api.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks", indexes = {
    @Index(name = "idx_task_title", columnList = "title"),
    @Index(name = "idx_task_description", columnList = "description")
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    private String title;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    
    private Date createdAt;
    private Date updatedAt;
    private Date deadLine;

    @OneToMany
    @JoinColumn(name = "responsible_id", referencedColumnName = "id")
    private User responsible;
}
