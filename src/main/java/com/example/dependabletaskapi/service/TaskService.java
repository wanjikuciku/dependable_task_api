package com.example.dependabletaskapi.service;

import com.example.dependabletaskapi.entity.Task;
import com.example.dependabletaskapi.exception.TaskNotFoundException;
import com.example.dependabletaskapi.repository.TaskRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Task createTask(String title) {
        Task t = new Task(title);
        return repository.save(t);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Transactional
    public Task markTaskCompleted(Long id) {
        Task t = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        t.setCompleted(true);
        return repository.save(t);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
